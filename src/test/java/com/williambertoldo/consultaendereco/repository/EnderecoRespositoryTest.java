package com.williambertoldo.consultaendereco.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.williambertoldo.consultaendereco.model.Endereco;

public class EnderecoRespositoryTest {

    private EndercoRespository repository;

    @BeforeEach
    public void setUp() {
        repository = new EndercoRespository();
    }

    @Test
    public void testSave() {
        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setRua("Rua Teste");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("Estado Teste");

        Endereco savedEndereco = repository.save(endereco);
        assertEquals(endereco, savedEndereco);
    }

    @Test
    public void testFindByCep() {
        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setRua("Rua Teste");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("Estado Teste");

        repository.save(endereco);

        Endereco foundEndereco = repository.findByCep("12345678");
        assertEquals(endereco, foundEndereco);

        assertNull(repository.findByCep("11111111"));
    }
}
