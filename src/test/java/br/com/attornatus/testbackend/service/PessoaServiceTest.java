package br.com.attornatus.testbackend.service;

import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;
import br.com.attornatus.testbackend.entity.Pessoa;
import br.com.attornatus.testbackend.exception.PessoaNaoEncontradaException;
import br.com.attornatus.testbackend.repository.EnderecoRepository;
import br.com.attornatus.testbackend.repository.PessoaRepository;
import br.com.attornatus.testbackend.service.impl.PessoaServiceImpl;
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

import static br.com.attornatus.testbackend.factory.CreationObjects.createPessoa;
import static br.com.attornatus.testbackend.factory.CreationObjects.createPessoaInput;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PessoaServiceTest {

    private PessoaService service;

    @MockBean
    private PessoaRepository pessoaRepository;

    @MockBean
    private EnderecoRepository enderecoRepository;

    private final ConverterDtoParaEntity converterDtoParaEntity =
            new ConverterDtoParaEntity(new ModelMapper());

    private final ConverterEntityParaDto converterEntityParaDto =
            new ConverterEntityParaDto(new ModelMapper());

    private final int index = 0;
    private final Pessoa pessoa = createPessoa();
    private final long pessoaId = 1L;

    @BeforeEach
    public void setUp() {
        this.service = new PessoaServiceImpl(pessoaRepository, enderecoRepository,
                converterDtoParaEntity, converterEntityParaDto);
    }

    @Test
    @DisplayName("Deve recuperar todas as pessoas")
    public void findAll() {

        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

        List<PessoaOutput> pessoaOutputList = service.findAll();

        assertThat(pessoaOutputList.get(index).getId()).isNotNull();
        assertThat(pessoaOutputList.get(index).getNome()).isNotNull();
        assertThat(pessoaOutputList.get(index).getEnderecos()).isNotEmpty();
        assertThat(pessoaOutputList.get(index).getEnderecos().get(index).getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve recuperar pessoa por id")
    public void findById() {

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        PessoaOutput pessoaOutput = service.findById(pessoaId);

        assertThat(pessoaOutput.getId()).isNotNull();
        assertThat(pessoaOutput.getNome()).isNotNull();
        assertThat(pessoaOutput.getEnderecos()).isNotEmpty();
        assertThat(pessoaOutput.getEnderecos().get(index).getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve salvar uma pessoa")
    public void save() {

        PessoaInput pessoaInput = createPessoaInput();

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaOutput pessoaOutput = service.save(pessoaInput);

        assertThat(pessoaOutput.getId()).isNotNull();
        assertThat(pessoaOutput.getNome()).isNotNull();
        assertThat(pessoaOutput.getEnderecos()).isNotEmpty();
    }

    @Test
    @DisplayName("Deve excluir uma pessoa")
    public void delete() {

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        assertDoesNotThrow(() -> service.delete(anyLong()));
        verify(pessoaRepository, atLeast(1)).deleteById(anyLong());
        verify(pessoaRepository, atLeast(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa")
    public void update() {

        PessoaInput pessoaInput = createPessoaInput();

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaOutput pessoaOutput = service.update(pessoaId, pessoaInput);

        assertThat(pessoaOutput.getId()).isNotNull();
        assertThat(pessoaOutput.getNome()).isNotNull();
        assertThat(pessoaOutput.getEnderecos()).isNotEmpty();
        assertThat(pessoaOutput.getEnderecos().get(index).getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar erro ao não encontrar uma pessoa")
    public void pessoaNaoEncontrada() {

        Throwable exception = catchThrowable(() -> service.findById(pessoaId));

        assertThat(exception)
                .isInstanceOf(PessoaNaoEncontradaException.class)
                .hasMessage("Não existe um cadastro de pessoa com código %d", pessoaId);
    }
}
