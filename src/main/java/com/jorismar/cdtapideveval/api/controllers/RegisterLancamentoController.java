package com.jorismar.cdtapideveval.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jorismar.cdtapideveval.api.components.RabbitMQSender;
import com.jorismar.cdtapideveval.api.dtos.RegisterLancamentoDto;
import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.entities.Lancamento;
import com.jorismar.cdtapideveval.api.enums.CondicaoCartaoEnum;
import com.jorismar.cdtapideveval.api.enums.CondicaoLancamentoEnum;
import com.jorismar.cdtapideveval.api.response.Response;
import com.jorismar.cdtapideveval.api.services.CartaoService;
import com.jorismar.cdtapideveval.api.services.LancamentoService;
import com.jorismar.cdtapideveval.api.utils.CartaoUtilities;
import com.jorismar.cdtapideveval.api.utils.LancamentoUtilities;

@RestController
@RequestMapping("/cdt/api/payment")
@CrossOrigin(origins = "*")
public class RegisterLancamentoController {
    private Logger logger = Logger.getLogger(RegisterLancamentoController.class.getName());

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private RabbitMQSender amqpSender;

    public RegisterLancamentoController() {

    }

    @PostMapping
    public ResponseEntity<Response<RegisterLancamentoDto>> register(@Valid @RequestBody RegisterLancamentoDto dto,
                BindingResult result) throws NoSuchAlgorithmException {
        logger.log(Level.FINE, "Registering new payment:\n{}", dto.toString());
        
        Response<RegisterLancamentoDto> response = new Response<>();

        Cartao cartao = null;
        Lancamento lancamento = null;

        Optional<Cartao> optCartao = this.getCartao(dto, result);
        
        if (optCartao.isPresent()) {
            cartao = optCartao.get();
            lancamento = getLancamento(dto, cartao, result);
        }

        if (result.hasErrors()) {
            logger.log(Level.FINE, "Refused: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.amqpSender.publishMessage(lancamento);

        response.setData(this.getRegisterLancamentoDto(lancamento));

        return ResponseEntity.ok(response);
    }

    public Optional<Cartao> getCartao(RegisterLancamentoDto dto, BindingResult result) {
        Optional<Cartao> opt = this.cartaoService.findByNumero(dto.getCartaoNumero());

        // Valid credit card
        if(!opt.isPresent()) {
            result.addError(new ObjectError("Lancamento", "Invalid card number"));
            return opt;
        }

        Cartao cartao = opt.get();

        boolean nomeIsValid = cartao.getNomePortador().equals(dto.getPortadorNome());
        boolean cvcIsValid = cartao.getCvc().equals(dto.getCvc());
        boolean vencIsValid = cartao.getValidade().compareTo(dto.getValidade()) == 0;
        boolean cpfIsValid = cartao.getPortador().getCpf().equals(dto.getPortadorCpf());
        boolean isActive = cartao.getCondicao() == CondicaoCartaoEnum.ATIVO;
        boolean hasLimite = CartaoUtilities.getLimitAvailable(cartao) >= dto.getValor();
        boolean isExpired = vencIsValid && LocalDate.now().isAfter(cartao.getValidade());

        if (nomeIsValid && cvcIsValid && vencIsValid && cpfIsValid) {
            if (isExpired) {
                result.addError(new ObjectError("Lancamento", "Credit card is expired"));
            }
            if (!isActive) {
                result.addError(new ObjectError("Lancamento", "Credit card is not active"));
            }
            if (!hasLimite) {
                result.addError(new ObjectError("Lancamento", "Insufficient funds"));
            }
        } else {
            result.addError(new ObjectError("Lancamento", "Invalid credit card information"));
        }

        return opt;
    }

    public Lancamento getLancamento(RegisterLancamentoDto dto, Cartao cartao, BindingResult result) {
        Lancamento lancamento = null;
        
        String identificador = dto.getIdentificador();
        
        // Refund a Lancamento if receive a valid codigo
        if (identificador != null && !identificador.isEmpty()) {
            final Optional<Lancamento> optLancamento = this.lancamentoService.findByIdentificador(identificador);
            
            if (optLancamento.isPresent()) {
                lancamento = optLancamento.get();

                // Valid payee
                if (lancamento.getBeneficiario().equals(dto.getBeneficiario())) {
                    // Valid condition
                    if (lancamento.getCondicao() == CondicaoLancamentoEnum.PENDENTE) {
                        // Valid value
                        if (lancamento.getValor().equals(dto.getValor())) {
                            lancamento.setCondicao(CondicaoLancamentoEnum.REEMBOLSADO);
                            return lancamento;
                        }
                    }
                }
                
                result.addError(new ObjectError("Lancamento", "Invalid information to refund operation"));
                return lancamento;
            }
        }
        
        lancamento = new Lancamento();

        // Generates a new ID while it exists
        do {
            try {
                identificador = LancamentoUtilities.generateID(dto);
            } catch (NoSuchAlgorithmException e) {}
        } while(identificador == null || identificador.isEmpty() || this.lancamentoService.findByIdentificador(identificador).isPresent());
        
        lancamento.setIdentificador(identificador);
        lancamento.setDataLancamento(LocalDate.now());
        lancamento.setCartao(cartao);
        lancamento.setBeneficiario(dto.getBeneficiario());
        lancamento.setValor(dto.getValor());
        lancamento.setCondicao(CondicaoLancamentoEnum.PENDENTE);

        return lancamento;
    }

    public RegisterLancamentoDto getRegisterLancamentoDto(Lancamento lancamento) {
        RegisterLancamentoDto dto = new RegisterLancamentoDto();

        dto.setBeneficiario(lancamento.getBeneficiario());
        dto.setCartaoNumero(lancamento.getCartao().getNumero());
        dto.setIdentificador(lancamento.getIdentificador());
        dto.setDataLancamento(lancamento.getDataLancamento());
        dto.setPortadorCpf(lancamento.getCartao().getPortador().getCpf());
        dto.setValor(lancamento.getValor());
        dto.setPortadorNome(lancamento.getCartao().getNomePortador());
        dto.setValidade(lancamento.getCartao().getValidade());
        dto.setCvc(lancamento.getCartao().getCvc());

        return dto;
    }
}