package br.com.attornatus.testbackend.service.impl;

import br.com.attornatus.testbackend.dto.input.EnderecoInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.entity.Endereco;
import br.com.attornatus.testbackend.entity.Pessoa;
import br.com.attornatus.testbackend.exception.PessoaNaoEncontradaException;
import br.com.attornatus.testbackend.repository.EnderecoRepository;
import br.com.attornatus.testbackend.repository.PessoaRepository;
import br.com.attornatus.testbackend.service.EnderecoService;
import br.com.attornatus.testbackend.service.util.converter.ConverterDtoParaEntity;
import br.com.attornatus.testbackend.service.util.converter.ConverterEntityParaDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final PessoaRepository pessoaRepository;
    private final ConverterEntityParaDto converterEntityParaDto;
    private final ConverterDtoParaEntity converterDtoParaEntity;
    private final EnderecoRepository enderecoRepository;

    public EnderecoServiceImpl(PessoaRepository pessoaRepository, ConverterEntityParaDto converterEntityParaDto,
                               ConverterDtoParaEntity converterDtoParaEntity,
                               EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.converterEntityParaDto = converterEntityParaDto;
        this.converterDtoParaEntity = converterDtoParaEntity;
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public List<EnderecoOutput> findById(Long pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
        List<Endereco> enderecos = pessoa.getEnderecos();

        return converterEntityParaDto.toCollectionEnderecoDto(enderecos);
    }

    @Override
    public EnderecoOutput save(EnderecoInput enderecoInput, Long pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
        List<Endereco> enderecos = pessoa.getEnderecos();

        enderecos.forEach(e -> {
            if (e.getIsPrincipal()) {
                e.setIsPrincipal(false);
            }
        });

        Endereco endereco = converterDtoParaEntity.toEntityEndereco(enderecoInput);
        endereco.setPessoa(pessoa);
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        return converterEntityParaDto.toEnderecoDto(enderecoSalvo);
    }

    @Override
    public List<EnderecoOutput> alterarEnderecoPrincipal(Long pessoaId, Long enderecoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));

        List<Endereco> enderecos = pessoa.getEnderecos();

        enderecos.forEach(e -> {
            if (e.getIsPrincipal() && !Objects.equals(e.getId(), enderecoId)) {
                e.setIsPrincipal(false);
            }
            if (Objects.equals(e.getId(), enderecoId)) {
                e.setIsPrincipal(true);
            }
        });

        enderecos = enderecoRepository.saveAll(enderecos);
        return converterEntityParaDto.toCollectionEnderecoDto(enderecos);
    }
}
