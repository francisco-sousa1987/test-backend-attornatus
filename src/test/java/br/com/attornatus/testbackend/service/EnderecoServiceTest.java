package br.com.attornatus.testbackend.service;

import br.com.attornatus.testbackend.dto.input.EnderecoInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.entity.Endereco;
import br.com.attornatus.testbackend.entity.Pessoa;
import br.com.attornatus.testbackend.repository.EnderecoRepository;
import br.com.attornatus.testbackend.repository.PessoaRepository;
import br.com.attornatus.testbackend.service.impl.EnderecoServiceImpl;
import br.com.attornatus.testbackend.service.util.converter.ConverterDtoParaEntity;
import br.com.attornatus.testbackend.service.util.converter.ConverterEntityParaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static br.com.attornatus.testbackend.factory.CreationObjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EnderecoServiceTest {

    private EnderecoService service;

    @MockBean
    private PessoaRepository pessoaRepository;

    @MockBean
    private EnderecoRepository enderecoRepository;

    private final ConverterDtoParaEntity converterDtoParaEntity =
            new ConverterDtoParaEntity(new ModelMapper());

    private final ConverterEntityParaDto converterEntityParaDto =
            new ConverterEntityParaDto(new ModelMapper());

    private final int index = 0;

    @BeforeEach
    public void setUp() {
        this.service = new EnderecoServiceImpl(pessoaRepository, converterEntityParaDto,
                converterDtoParaEntity, enderecoRepository);
    }

    @Test
    @DisplayName("Deve recuperar os endereços de uma pessoa")
    public void findById() {

        Pessoa pessoa = createPessoa();

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        List<EnderecoOutput> enderecoOutputList = service.findById(1L);

        assertThat(enderecoOutputList).isNotEmpty();
        assertThat(enderecoOutputList.get(index).getId()).isNotNull();
        assertThat(enderecoOutputList.get(index).getLogradouro()).isNotBlank();
        assertThat(enderecoOutputList.get(index).getCep()).isNotBlank();
        assertThat(enderecoOutputList.get(index).getCidade()).isNotBlank();
    }

    @Test
    @DisplayName("Deve salvar um novo endereço para uma pessoa")
    public void save() {

        EnderecoInput enderecoInput = createEnderecoInput();
        Endereco endereco = createEndereco();
        Pessoa pessoa = createPessoa();

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        EnderecoOutput enderecoOutput = service.save(enderecoInput, 1L);

        assertThat(enderecoOutput.getId()).isNotNull();
        assertThat(enderecoOutput.getLogradouro()).isNotBlank();
        assertThat(enderecoOutput.getCep()).isNotBlank();
        assertThat(enderecoOutput.getCidade()).isNotBlank();
    }

    @Test
    @DisplayName("Deve ao cadastrar novo endereço principal verificar se existe outro")
    public void salvarEnderecoComoPrincipal() {

        EnderecoInput enderecoInput = createEnderecoInput();
        Endereco endereco = createEndereco();
        Pessoa pessoa = createPessoa();

        pessoa.getEnderecos().get(0).setIsPrincipal(true);
        endereco.setIsPrincipal(true);
        enderecoInput.setIsPrincipal(true);

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        EnderecoOutput enderecoOutput = service.save(enderecoInput, 1L);

        assertThat(enderecoOutput.getId()).isNotNull();
        assertThat(enderecoOutput.getLogradouro()).isNotBlank();
        assertThat(enderecoOutput.getCep()).isNotBlank();
        assertThat(enderecoOutput.getCidade()).isNotBlank();
    }

    @Test
    @DisplayName("Deve alterar o endereço principal de uma pessoa")
    public void alterarEnderecoPrincipal() {

        Pessoa pessoa = createPessoa();
        Endereco endereco = createEndereco();

        pessoa.getEnderecos().get(0).setIsPrincipal(true);

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.saveAll(anyCollection())).thenReturn(List.of(endereco));

        List<EnderecoOutput> enderecoOutputList = service.alterarEnderecoPrincipal(1L, 1L);

        assertThat(enderecoOutputList).isNotEmpty();
        assertThat(enderecoOutputList.get(index).getId()).isNotNull();
        assertThat(enderecoOutputList.get(index).getLogradouro()).isNotBlank();
        assertThat(enderecoOutputList.get(index).getCep()).isNotBlank();
        assertThat(enderecoOutputList.get(index).getCidade()).isNotBlank();
    }
}
