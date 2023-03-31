package com.williambertoldo.consultaendereco.controller;

import com.williambertoldo.consultaendereco.model.Endereco;
import com.williambertoldo.consultaendereco.model.EnderecoRequest;
import com.williambertoldo.consultaendereco.service.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping("/consulta-endereco")
    public ResponseEntity<Endereco> consultaEndereco(@RequestBody EnderecoRequest enderecoRequest) {
        Endereco endereco = enderecoService.buscarEndereco(enderecoRequest.getCep());
        if (endereco != null) {
            return ResponseEntity.ok(endereco);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
