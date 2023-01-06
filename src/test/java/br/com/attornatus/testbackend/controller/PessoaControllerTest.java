package br.com.attornatus.testbackend.controller;

import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;
import br.com.attornatus.testbackend.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static br.com.attornatus.testbackend.factory.CreationObjects.createPessoaInput;
import static br.com.attornatus.testbackend.factory.CreationObjects.createPessoaOutput;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(PessoaController.class)
@AutoConfigureMockMvc
public class PessoaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PessoaService service;

    private final PessoaOutput pessoaOutput = createPessoaOutput();
    private final String pessoaApi = "/pessoas";

    @Test
    @DisplayName("Deve recuperar pessoa por id")
    public void recuperarPessoaPorId() throws Exception {

        when(service.findById(anyLong())).thenReturn(pessoaOutput);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(pessoaApi.concat("/" + pessoaOutput.getId()))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(pessoaOutput.getId()))
                .andExpect(jsonPath("nome").value(pessoaOutput.getNome()))
                .andExpect(jsonPath("$['enderecos'][0]['id']").value(pessoaOutput.getEnderecos().get(0).getId()));
    }

    @Test
    @DisplayName("Deve recuperar todas as pessoas")
    public void recuperarTodasPessoas() throws Exception {

        when(service.findAll()).thenReturn(List.of(pessoaOutput));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(pessoaApi)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['id']").value(pessoaOutput.getId()))
                .andExpect(jsonPath("$[0]['nome']").value(pessoaOutput.getNome()))
                .andExpect(jsonPath("$[0]['enderecos'][0]['id']").value(pessoaOutput.getEnderecos().get(0).getId()));
    }

    @Test
    @DisplayName("Deve criar uma pessoa com sucesso")
    public void criarPessoa() throws Exception {

        PessoaInput pessoaInput = createPessoaInput();

        when(service.save(any(PessoaInput.class))).thenReturn(pessoaOutput);

        String json = new ObjectMapper().writeValueAsString(pessoaInput);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(pessoaApi)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(pessoaOutput.getId()))
                .andExpect(jsonPath("nome").value(pessoaOutput.getNome()));
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando a data de nascimento for inválida")
    public void dataNascimentoInvalida() throws Exception {

        PessoaInput pessoaInput = createPessoaInput();
        pessoaInput.setDataNascimento("1987-33-58");

        String json = new ObjectMapper().writeValueAsString(pessoaInput);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(pessoaApi)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando um campo no json está inválido")
    public void jsonCampoInvalido() throws Exception {

        PessoaInput pessoaInput = createPessoaInput();
        pessoaInput.setNome("");

        String json = new ObjectMapper().writeValueAsString(pessoaInput);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(pessoaApi)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve deletar uma pessoa")
    public void deletePessoa() throws Exception {

        when(service.findById(anyLong())).thenReturn(pessoaOutput);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(pessoaApi.concat("/" + pessoaOutput.getId()));

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa")
    public void updateBookTest() throws Exception {

        when(service.update(anyLong(), any(PessoaInput.class))).thenReturn(pessoaOutput);

        String json = new ObjectMapper().writeValueAsString(pessoaOutput);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(pessoaApi.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("id").value(pessoaOutput.getId()))
                .andExpect(jsonPath("nome").value(pessoaOutput.getNome()));
    }
}
