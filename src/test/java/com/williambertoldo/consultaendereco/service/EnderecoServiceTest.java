package com.williambertoldo.consultaendereco.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.williambertoldo.consultaendereco.model.Endereco;
import com.williambertoldo.consultaendereco.model.EnderecoRequest;
import com.williambertoldo.consultaendereco.repository.EndercoRespository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {

    @Mock
    private EndercoRespository repository;

    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    public void buscarEnderecoExistenteTest() {
        // Arrange
        String cep = "12213-500";
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setRua("Rua Butantã");
        endereco.setBairro("Vila Paiva");
        endereco.setCidade("São José dos Campos");
        endereco.setEstado("SP");
        when(repository.findByCep(cep)).thenReturn(endereco);

        // Act
        Endereco resultado = enderecoService.buscarEndereco(cep);

        // Assert
        assertNotNull(resultado);
        assertEquals(cep, resultado.getCep());
        assertEquals("Rua Butantã", resultado.getRua());
        assertEquals("Vila Paiva", resultado.getBairro());
        assertEquals("São José dos Campos", resultado.getCidade());
        assertEquals("SP", resultado.getEstado());
    }

    @Test
    public void calcularFreteSudesteTest() {
        // Arrange
        String estado = "SP";

        // Act
        double frete = enderecoService.calcularFrete(estado);

        // Assert
        assertEquals(7.85, frete);
    }

    @Test
    public void calcularFreteCentroOesteTest() {
        // Arrange
        String estado = "GO";

        // Act
        double frete = enderecoService.calcularFrete(estado);

        // Assert
        assertEquals(12.5, frete);
    }

    @Test
    public void calcularFreteNordesteTest() {
        // Arrange
        String estado = "BA";

        // Act
        double frete = enderecoService.calcularFrete(estado);

        // Assert
        assertEquals(15.98, frete);
    }

    @Test
    @DisplayName("Deve consultar um endereço na API do ViaCep e salvar no banco de dados")
    public void consultarEnderecoViaCepTest() {
        String cep = "12213-500";
        String rua = "Rua Butantã";
        String bairro = "Vila Paiva";
        String cidade = "São José dos Campos";
        String estado = "SP";

        EnderecoRequest enderecoResponse = new EnderecoRequest();
        enderecoResponse.setCep(cep);
        enderecoResponse.setLogradouro(rua);
        enderecoResponse.setBairro(bairro);
        enderecoResponse.setLocalidade(cidade);
        enderecoResponse.setUf(estado);

        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        Mockito.when(repository.findByCep(Mockito.anyString())).thenReturn(null);

        Mockito.when(repository.save(Mockito.any(Endereco.class))).thenReturn(null);

        Endereco enderecoBuscado = enderecoService.buscarEndereco(cep);

        Assertions.assertNotNull(enderecoBuscado);
        Assertions.assertEquals(cep, enderecoBuscado.getCep());
        Assertions.assertEquals(rua, enderecoBuscado.getRua());
        Assertions.assertEquals(bairro, enderecoBuscado.getBairro());
        Assertions.assertEquals(cidade, enderecoBuscado.getCidade());
        Assertions.assertEquals(estado, enderecoBuscado.getEstado());
    }
}