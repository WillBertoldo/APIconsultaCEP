package com.williambertoldo.consultaendereco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.williambertoldo.consultaendereco.model.Endereco;
import com.williambertoldo.consultaendereco.model.EnderecoRequest;
import com.williambertoldo.consultaendereco.repository.EndercoRespository;

@Service
public class EnderecoService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";
    private static final double FRETE_SUDESTE = 7.85;
    private static final double FRETE_CENTRO_OESTE = 12.50;
    private static final double FRETE_NORDESTE = 15.98;
    private static final double FRETE_SUL = 17.30;
    private static final double FRETE_NORTE = 20.83;

    @Autowired
    private EndercoRespository repository;

    public Endereco buscarEndereco(String cep) {
        Endereco endereco = repository.findByCep(cep);
        endereco = consultarViaCep(cep);
        if (endereco != null) {
            repository.save(endereco);
        } else
            throw new EnderecoNotFoundException("Endereço não encontrado para o CEP: " + cep);
        return endereco;
    }

    public double calcularFrete(String estado) {
        switch (estado) {
            case "SP":
            case "RJ":
            case "MG":
            case "ES":
                return FRETE_SUDESTE;
            case "MT":
            case "MS":
            case "GO":
            case "DF":
                return FRETE_CENTRO_OESTE;
            case "MA":
            case "PI":
            case "CE":
            case "RN":
            case "PB":
            case "PE":
            case "AL":
            case "SE":
            case "BA":
                return FRETE_NORDESTE;
            case "PR":
            case "SC":
            case "RS":
                return FRETE_SUL;
            case "AC":
            case "AP":
            case "AM":
            case "PA":
            case "RO":
            case "RR":
            case "TO":
                return FRETE_NORTE;
            default:
                return 0;
        }
    }

    public Endereco consultarViaCep(String cep) {
        String url = String.format(VIA_CEP_URL, cep);
        RestTemplate restTemplate = new RestTemplate();
        EnderecoRequest enderecoResponse = restTemplate.getForObject(url, EnderecoRequest.class);

        if (enderecoResponse.getCep() == null) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setCep(enderecoResponse.getCep());
        endereco.setRua(enderecoResponse.getLogradouro());
        endereco.setComplemento(enderecoResponse.getComplemento());
        endereco.setBairro(enderecoResponse.getBairro());
        endereco.setCidade(enderecoResponse.getLocalidade());
        endereco.setEstado(enderecoResponse.getUf());
        endereco.setFrete(this.calcularFrete(enderecoResponse.getUf()));

        return endereco;
    }
}
