package com.williambertoldo.consultaendereco.service;

public class EnderecoNotFoundException extends RuntimeException {
    public EnderecoNotFoundException(String cep) {
        super(String.format("Endereço não encontrado para o CEP %s", cep));
    }
}