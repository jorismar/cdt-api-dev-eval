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

import com.jorismar.cdtapideveval.api.dtos.RegisterLancamentoDto;
import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.entities.Lancamento;
import com.jorismar.cdtapideveval.api.response.Response;
import com.jorismar.cdtapideveval.api.services.CartaoService;
import com.jorismar.cdtapideveval.api.services.LancamentoService;
import com.jorismar.cdtapideveval.api.utils.LancamentoUtilities;

@RestController
@RequestMapping("/cdt/api/reg-lanc")
@CrossOrigin(origins = "*")
public class RegisterLancamentoController {
    private Logger logger = Logger.getLogger(RegisterLancamentoController.class.getName());

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private CartaoService cartaoService;

    public RegisterLancamentoController() {

    }

    @PostMapping
    public ResponseEntity<Response<RegisterLancamentoDto>> register(@Valid @RequestBody RegisterLancamentoDto dto,
                BindingResult result) throws NoSuchAlgorithmException {
        logger.log(Level.FINE, "Registering new payment:\n{}", dto.toString());
        
        Response<RegisterLancamentoDto> response = new Response<>();

        this.validate(dto, result);

        if (result.hasErrors()) {
            logger.log(Level.FINE, "Invalid data found: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Cartao cartao = this.cartaoService.findByNumero(dto.getCartaoNumero()).get();
        Lancamento lancamento = getLancamento(dto, cartao);

        // Store new lancamento into de DB
        this.lancamentoService.persist(lancamento);

        response.setData(this.getRegisterLancamentoDto(lancamento, cartao));

        return ResponseEntity.ok(response);
    }

    public void validate(RegisterLancamentoDto dto, BindingResult result) {
        final Optional<Cartao> opt = this.cartaoService.findByNumero(dto.getCartaoNumero());

        // Valid Payee to refund
        if (dto.getCodigo() != null) {
            Optional<Lancamento> optLanc = this.lancamentoService.findByCodigo(dto.getCodigo());
            if(optLanc.isPresent() && !optLanc.get().getBeneficiario().equals(dto.getBeneficiario())) {
                result.addError(new ObjectError("Lancamento", "Payee is invalid"));
                return;
            }
        }

        // Valid credit card
        if(opt.isPresent()) {
            Cartao cartao = opt.get();

            boolean nomeIsValid = cartao.getNomeDoPortador().equals(dto.getPortadorNome());
            boolean cvcIsValid = cartao.getCvc().equals(dto.getCvc());
            boolean vencIsValid = cartao.getValidade().compareTo(dto.getVencimento()) == 0;
            boolean cpfIsValid = cartao.getPortador().getCpf().equals(dto.getPortadorCpf());

            if (nomeIsValid && cvcIsValid && vencIsValid && cpfIsValid) {
                return;
            }
        }

        result.addError(new ObjectError("Lancamento", "Credit card is invalid"));
    }

    public Lancamento getLancamento(RegisterLancamentoDto dto, Cartao cartao) {
        Lancamento lancamento = null;
        Double valor = dto.getValor();
        
        Long codigo = dto.getCodigo();
        
        // Refund a Lancamento if receive a valid codigo
        if (codigo != null) {
            final Optional<Lancamento> optLancamento = this.lancamentoService.findByCodigo(codigo);
            
            if (optLancamento.isPresent()) {
                lancamento = optLancamento.get();
                lancamento.setValorCobranca(0.0);
            }
        } else {
            do {
                codigo = LancamentoUtilities.generateCode();
            } while(this.lancamentoService.findByCodigo(codigo).isPresent());
            lancamento = new Lancamento();
    
            lancamento.setDataLancamento(LocalDate.now());
            lancamento.setCartao(cartao);
            lancamento.setBeneficiario(dto.getBeneficiario());
            lancamento.setValor(valor);
            lancamento.setValorCobranca(valor);
            lancamento.setCodigo(codigo);
        }

        return lancamento;
    }

    public RegisterLancamentoDto getRegisterLancamentoDto(Lancamento lancamento, Cartao cartao) {
        RegisterLancamentoDto dto = new RegisterLancamentoDto();

        dto.setBeneficiario(lancamento.getBeneficiario());
        dto.setCartaoNumero(lancamento.getCartao().getNumero());
        dto.setCodigo(lancamento.getCodigo());
        dto.setDataLancamento(lancamento.getDataLancamento());
        dto.setPortadorCpf(cartao.getPortador().getCpf());
        dto.setValor(lancamento.getValorCobranca() == 0 ? lancamento.getValor() * -1 : lancamento.getValor());

        return dto;
    }
}