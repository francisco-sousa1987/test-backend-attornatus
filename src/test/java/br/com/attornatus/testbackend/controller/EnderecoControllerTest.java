package br.com.attornatus.testbackend.controller;

import br.com.attornatus.testbackend.dto.input.EnderecoInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.service.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static br.com.attornatus.testbackend.factory.CreationObjects.createEnderecoInput;
import static br.com.attornatus.testbackend.factory.CreationObjects.createEnderecoOutput;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(EnderecoController.class)
@AutoConfigureMockMvc
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EnderecoService service;

    private final EnderecoOutput enderecoOutput = createEnderecoOutput();
    private final long pessoaId = 1L;
    private final String enderecoApi = "/enderecos/pessoas";

    @Test
    @DisplayName("Deve recuperar os endereços de uma pessoa")
    public void recuperarEnderecosPessoa() throws Exception {

        when(service.findById(anyLong())).thenReturn(List.of(enderecoOutput));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(enderecoApi.concat("/" + enderecoOutput.getId()))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]['id']").value(enderecoOutput.getId()))
                .andExpect(jsonPath("[0]['logradouro']").value(enderecoOutput.getLogradouro()))
                .andExpect(jsonPath("[0]['cep']").value(enderecoOutput.getCep()));
    }

    @Test
    @DisplayName("Deve salvar um novo endereço para a pessoa")
    public void salvarNovoEnderecoPessoa() throws Exception {

        EnderecoInput enderecoInput = createEnderecoInput();

        when(service.save(Mockito.any(EnderecoInput.class), anyLong())).thenReturn(enderecoOutput);

        String json = new ObjectMapper().writeValueAsString(enderecoInput);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(enderecoApi.concat("/" + pessoaId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(enderecoOutput.getId()))
                .andExpect(jsonPath("logradouro").value(enderecoOutput.getLogradouro()))
                .andExpect(jsonPath("cep").value(enderecoOutput.getCep()));
    }

    @Test
    @DisplayName("Deve alterar o endereço principal de uma pessoa")
    public void alterarEnderecoPrincipal() throws Exception {

        long enderecoId = 1L;

        when(service.alterarEnderecoPrincipal(anyLong(), anyLong())).thenReturn(List.of(enderecoOutput));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(enderecoApi.concat("/" + pessoaId + "/" + "principal/" + enderecoId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]['id']").value(enderecoOutput.getId()))
                .andExpect(jsonPath("[0]['logradouro']").value(enderecoOutput.getLogradouro()))
                .andExpect(jsonPath("[0]['cep']").value(enderecoOutput.getCep()));
    }
}
