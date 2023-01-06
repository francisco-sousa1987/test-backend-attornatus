package br.com.attornatus.testbackend.controller;

import br.com.attornatus.testbackend.dto.input.EnderecoInput;
import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;
import br.com.attornatus.testbackend.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(PessoaController.class)
@AutoConfigureMockMvc
public class PessoaOutputControllerTest {

    static final String PESSOA_API = "/pessoas";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PessoaService service;

    @Test
    @DisplayName("Recuperar pessoa por id")
    public void recuperarPessoaPorId() throws Exception {

        Long id = 1L;

        br.com.attornatus.testbackend.dto.output.PessoaOutput pessoaOutput = new br.com.attornatus.testbackend.dto.output.PessoaOutput();
        pessoaOutput.setId(id);
        pessoaOutput.setNome("Lara Rebeca Oliveira");

        EnderecoOutput enderecoOutput = new EnderecoOutput();
        enderecoOutput.setId(id);
        enderecoOutput.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        enderecoOutput.setCep("69077-749");
        enderecoOutput.setNumero("532");
        enderecoOutput.setCidade("Manaus");
        enderecoOutput.setIsPrincipal(false);

        pessoaOutput.setEnderecos(List.of(enderecoOutput));

        BDDMockito.given(service.findById(id)).willReturn(pessoaOutput);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PESSOA_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("nome").value(pessoaOutput.getNome()))
                .andExpect(jsonPath("$['enderecos'][0]['id']").value(pessoaOutput.getEnderecos().get(0).getId()))
                .andExpect(jsonPath("$['enderecos'][0]['logradouro']").value(pessoaOutput.getEnderecos().get(0).getLogradouro()))
                .andExpect(jsonPath("$['enderecos'][0]['cep']").value(pessoaOutput.getEnderecos().get(0).getCep()))
                .andExpect(jsonPath("$['enderecos'][0]['numero']").value(pessoaOutput.getEnderecos().get(0).getNumero()))
                .andExpect(jsonPath("$['enderecos'][0]['cidade']").value(pessoaOutput.getEnderecos().get(0).getCidade()))
                .andExpect(jsonPath("$['enderecos'][0]['isPrincipal']").value(pessoaOutput.getEnderecos().get(0).getIsPrincipal()));
    }

    @Test
    @DisplayName("Recuperar todas as pessoas")
    public void recuperarTodasPessoas() throws Exception {

        Long id = 1L;

        br.com.attornatus.testbackend.dto.output.PessoaOutput pessoaOutput = new br.com.attornatus.testbackend.dto.output.PessoaOutput();
        pessoaOutput.setId(id);
        pessoaOutput.setNome("Lara Rebeca Oliveira");

        EnderecoOutput enderecoOutput = new EnderecoOutput();
        enderecoOutput.setId(id);
        enderecoOutput.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        enderecoOutput.setCep("69077-749");
        enderecoOutput.setNumero("532");
        enderecoOutput.setCidade("Manaus");
        enderecoOutput.setIsPrincipal(false);

        pessoaOutput.setEnderecos(List.of(enderecoOutput));

        BDDMockito.given(service.findAll()).willReturn(List.of(pessoaOutput));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PESSOA_API)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['id']").value(id))
                .andExpect(jsonPath("$[0]['nome']").value(pessoaOutput.getNome()))
                .andExpect(jsonPath("$[0]['enderecos'][0]['id']").value(pessoaOutput.getEnderecos().get(0).getId()))
                .andExpect(jsonPath("$[0]['enderecos'][0]['logradouro']").value(pessoaOutput.getEnderecos().get(0).getLogradouro()))
                .andExpect(jsonPath("$[0]['enderecos'][0]['cep']").value(pessoaOutput.getEnderecos().get(0).getCep()))
                .andExpect(jsonPath("$[0]['enderecos'][0]['numero']").value(pessoaOutput.getEnderecos().get(0).getNumero()))
                .andExpect(jsonPath("$[0]['enderecos'][0]['cidade']").value(pessoaOutput.getEnderecos().get(0).getCidade()))
                .andExpect(jsonPath("$[0]['enderecos'][0]['isPrincipal']").value(pessoaOutput.getEnderecos().get(0).getIsPrincipal()));
    }

    @Test
    @DisplayName("Deve criar uma pessoa com sucesso")
    public void criarPessoa() throws Exception {

        PessoaInput pessoaInput = new PessoaInput();
        pessoaInput.setNome("Lara Rebeca Oliveira");
        pessoaInput.setDataNascimento("1987-09-15");

        EnderecoInput enderecoInput = new EnderecoInput();
        enderecoInput.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        enderecoInput.setCep("69077-749");
        enderecoInput.setNumero("532");
        enderecoInput.setCidade("Manaus");
        enderecoInput.setIsPrincipal(false);

        pessoaInput.setEnderecos(List.of(enderecoInput));

        PessoaOutput pessoaOutput = new PessoaOutput();
        pessoaOutput.setId(1L);
        pessoaOutput.setNome(pessoaInput.getNome());
        pessoaOutput.setDataNascimento(new Date());

        EnderecoOutput endereco = new EnderecoOutput();
        endereco.setLogradouro(enderecoInput.getLogradouro());
        endereco.setCep(enderecoInput.getCep());
        endereco.setNumero(enderecoInput.getNumero());
        endereco.setCidade(enderecoInput.getCidade());
        endereco.setIsPrincipal(enderecoInput.getIsPrincipal());

        pessoaOutput.setEnderecos(List.of(endereco));

        BDDMockito.given(service.save(Mockito.any(PessoaInput.class))).willReturn(pessoaOutput);
        String json = new ObjectMapper().writeValueAsString(pessoaOutput);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PESSOA_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("nome").value(pessoaOutput.getNome()));
    }
}
