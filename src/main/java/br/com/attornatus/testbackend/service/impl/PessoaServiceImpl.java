package br.com.attornatus.testbackend.service.impl;

import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;
import br.com.attornatus.testbackend.entity.Endereco;
import br.com.attornatus.testbackend.entity.Pessoa;
import br.com.attornatus.testbackend.exception.PessoaNaoEncontradaException;
import br.com.attornatus.testbackend.repository.EnderecoRepository;
import br.com.attornatus.testbackend.repository.PessoaRepository;
import br.com.attornatus.testbackend.service.PessoaService;
import br.com.attornatus.testbackend.service.util.converter.ConverterDtoParaEntity;
import br.com.attornatus.testbackend.service.util.converter.ConverterEntityParaDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final ConverterDtoParaEntity converterDtoParaEntity;
    private final ConverterEntityParaDto converterEntityParaDto;

    public PessoaServiceImpl(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository,
                             ConverterDtoParaEntity convertAddressToEntity,
                             ConverterEntityParaDto converterEntityParaDto) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
        this.converterDtoParaEntity = convertAddressToEntity;
        this.converterEntityParaDto = converterEntityParaDto;
    }

    @Override
    public List<PessoaOutput> findAll() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return converterEntityParaDto.toCollectionPessoaDto(pessoas);
    }

    @Override
    public PessoaOutput findById(Long pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));

        return mountPersonResponse(pessoa, null);
    }

    @Override
    @Transactional
    public PessoaOutput save(PessoaInput pessoaInput) {
        Pessoa pessoa = converterDtoParaEntity.toEntityPessoa(pessoaInput);
        pessoa = pessoaRepository.save(pessoa);

        List<Endereco> enderecos = persistirEndereco(pessoaInput, pessoa);

        return mountPersonResponse(pessoa, enderecos);
    }

    @Override
    @Transactional
    public void delete(Long pessoaId) {
        findById(pessoaId);
        pessoaRepository.deleteById(pessoaId);
    }

    @Override
    @Transactional
    public PessoaOutput update(Long pessoaId, PessoaInput pessoaInput) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
        converterDtoParaEntity.copyToPessoaDomain(pessoaInput, pessoa);
        pessoa = pessoaRepository.save(pessoa);
        return converterEntityParaDto.toPessoaDto(pessoa);
    }

    private PessoaOutput mountPersonResponse(Pessoa pessoa, List<Endereco> enderecos) {
        List<EnderecoOutput> enderecoOutputs;

        PessoaOutput pessoaOutput = converterEntityParaDto.toPessoaDto(pessoa);
        if (enderecos == null) {
            enderecoOutputs = converterEntityParaDto.toCollectionEnderecoDto(pessoa.getEnderecos());
        } else {
            enderecoOutputs = converterEntityParaDto.toCollectionEnderecoDto(enderecos);
        }

        pessoaOutput.setEnderecos(enderecoOutputs);
        return pessoaOutput;
    }

    private List<Endereco> persistirEndereco(PessoaInput pessoaInput, Pessoa pessoa) {

        List<Endereco> enderecos;
        enderecos = converterDtoParaEntity.toCollectionEnderecoEntity(pessoaInput.getEnderecos());

        enderecos.forEach(address -> address.setPessoa(pessoa));
        enderecoRepository.saveAll(enderecos);
        return enderecos;
    }
}
