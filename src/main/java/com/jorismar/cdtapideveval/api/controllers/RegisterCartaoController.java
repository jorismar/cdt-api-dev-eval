package com.jorismar.cdtapideveval.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import java.util.Optional;
import java.util.logging.Level;

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

import com.jorismar.cdtapideveval.api.dtos.RegisterCartaoDto;
import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.entities.Portador;
import com.jorismar.cdtapideveval.api.response.Response;
import com.jorismar.cdtapideveval.api.services.CartaoService;
import com.jorismar.cdtapideveval.api.services.PortadorService;
import com.jorismar.cdtapideveval.api.utils.CartaoUtilities;

@RestController
@RequestMapping("/cdt/api/request/card")
@CrossOrigin(origins = "*")
public class RegisterCartaoController {
    private final Double MINIMUM_INCOME = 800.0;

    private Logger logger = Logger.getLogger(RegisterPortadorController.class.getName());

    @Autowired
    private PortadorService portadorService;

    @Autowired
    private CartaoService cartaoService;

    public RegisterCartaoController() {

    }

    @PostMapping
    public ResponseEntity<Response<RegisterCartaoDto>> register(@Valid @RequestBody RegisterCartaoDto dto,
                BindingResult result) throws NoSuchAlgorithmException {
        logger.log(Level.FINE, "Registering new Credit Card:\n{}", dto.toString());
        
        Response<RegisterCartaoDto> response = new Response<>();

        Optional<Portador> optPortador = this.portadorService.findByCpf(dto.getPortadorCpf());

        // Validating CPF
        if(!optPortador.isPresent()) {
            result.addError(new ObjectError("Cartao", "CPF not found."));
        }
        // Validating minimal monthly income
        else if (optPortador.get().getRenda() < MINIMUM_INCOME) {
            result.addError(new ObjectError("Cartao", "Request refused. Your income is not enough."));
        }

        // Validating card password
        if (!CartaoUtilities.validPassword(dto.getSenha())) {
            result.addError(new ObjectError("Cartao", "Invalid card password."));
        }

        if (result.hasErrors()) {
            logger.log(Level.FINE, "Invalid data found: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        // Generate a new credit card
        Cartao cartao = null;

        do {
            cartao = CartaoUtilities.generate(optPortador.get(), dto.getSenha());
        } while (this.cartaoService.findByNumero(cartao.getNumero()).isPresent());
        
        // Store new card into the DB
        this.cartaoService.persist(cartao);

        response.setData(this.getRegisterCartaoDto(cartao));

        return ResponseEntity.ok(response);
    }

    public RegisterCartaoDto getRegisterCartaoDto(Cartao cartao) {
        RegisterCartaoDto dto = new RegisterCartaoDto();

        dto.setPortadorCpf(cartao.getPortador().getCpf());
        dto.setSenha(cartao.getSenha());
        dto.setNumero(cartao.getNumero());
        dto.setNomePortador(cartao.getNomePortador());
        dto.setValidade(cartao.getValidade());
        dto.setCvc(cartao.getCvc());
        dto.setLimite(cartao.getLimite());

        return dto;
    }
}