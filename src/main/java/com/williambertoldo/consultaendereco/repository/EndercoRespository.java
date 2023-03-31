package com.williambertoldo.consultaendereco.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.williambertoldo.consultaendereco.model.Endereco;

@Repository
public class EndercoRespository {

    private final Map<String, Endereco> enderecos = new HashMap<>();

    public Endereco save(Endereco endereco) {
        enderecos.put(endereco.getCep(), endereco);
        return endereco;
    }

    public Endereco findByCep(String cep) {
        return enderecos.get(cep);
    }
}