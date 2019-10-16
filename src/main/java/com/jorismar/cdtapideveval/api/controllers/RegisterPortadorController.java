package com.jorismar.cdtapideveval.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import com.jorismar.cdtapideveval.api.components.RabbitMQSender;
import com.jorismar.cdtapideveval.api.dtos.RegisterPortadorDto;
import com.jorismar.cdtapideveval.api.entities.Portador;
import com.jorismar.cdtapideveval.api.form.Proposta;
import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.response.Response;
import com.jorismar.cdtapideveval.api.services.CartaoService;
import com.jorismar.cdtapideveval.api.services.PortadorService;
import com.jorismar.cdtapideveval.api.utils.CartaoUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cdt/api/register")
@CrossOrigin(origins = "*")
public class RegisterPortadorController {
    private Logger logger = Logger.getLogger(RegisterPortadorController.class.getName());

    @Autowired
    private PortadorService portadorService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private RabbitMQSender amqpSender;

    public RegisterPortadorController() {

    }

    @PostMapping
    public ResponseEntity<Response<RegisterPortadorDto>> register(@Valid @RequestBody RegisterPortadorDto dto,
            BindingResult result) throws NoSuchAlgorithmException {
        logger.log(Level.FINE, "Registering:\n{}", dto.toString());

        Response<RegisterPortadorDto> response = new Response<>();

        this.validate(dto, result);

        if (result.hasErrors()) {
            logger.log(Level.FINE, "Invalid data found: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        // Create entity by DTO
        Portador portador = getPortador(dto);

        Cartao cartao = null;
        
        do {
            // Generate a new credit card
            cartao = CartaoUtilities.generate(portador, dto.getSenha());
        } while (this.cartaoService.findByNumero(cartao.getNumero()).isPresent());

        // Generate the Proposta form to publish
        Proposta proposta = new Proposta();
        proposta.fill(portador, cartao);

        // Publish the Proposta form in AMQP the Queue
        this.amqpSender.publishMessage(proposta);

        // Set response information
        response.setData(this.getRegisterPortadorDto(portador, cartao));

        return ResponseEntity.ok(response);
    }

    public void validate(RegisterPortadorDto dto, BindingResult result) {
        this.portadorService.findByCpf(dto.getCpf()).ifPresent(
                portador -> result.addError(new ObjectError("portador", "A client with this CPF already exists.")));

        this.portadorService.findByEmail(dto.getEmail())
                .ifPresent(portador -> result.addError(new ObjectError("portador", "This email is already in use.")));

        if (!CartaoUtilities.validPassword(dto.getSenha())) {
            result.addError(new ObjectError("portador", "Invalid card password."));
        }
    }

    public Portador getPortador(RegisterPortadorDto dto) {
        Portador portador = new Portador();

        portador.setCpf(dto.getCpf());
        portador.setEmail(dto.getEmail());
        portador.setNome(dto.getNome());
        portador.setDataNascimento(dto.getDataNascimento());
        portador.setRenda(dto.getRenda());

        return portador;
    }

    public RegisterPortadorDto getRegisterPortadorDto(Portador portador, Cartao cartao) {
        RegisterPortadorDto dto = new RegisterPortadorDto();

        dto.setCpf(portador.getCpf());
        dto.setEmail(portador.getEmail());
        dto.setNome(portador.getNome());
        dto.setDataNascimento(portador.getDataNascimento());
        dto.setRenda(portador.getRenda());
        dto.setNumeroCartao(cartao.getNumero());
        dto.setNomeCartao(cartao.getNomePortador());
        dto.setValidadeCartao(cartao.getValidade());
        dto.setCvcCartao(cartao.getCvc());
        dto.setSenha(cartao.getSenha());

        return dto;
    }
}