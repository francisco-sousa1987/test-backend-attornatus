package br.com.attornatus.testbackend.service.impl;

import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;
import br.com.attornatus.testbackend.entity.Endereco;
import br.com.attornatus.testbackend.entity.Pessoa;
import br.com.attornatus.testbackend.exception.PessoaNaoEncontradaException;
import br.com.attornatus.testbackend.repository.EnderecoRepository;
import br.com.attornatus.testbackend.repository.PersonRepository;
import br.com.attornatus.testbackend.service.PersonService;
import br.com.attornatus.testbackend.util.conversion.MapperConversionAddressToDto;
import br.com.attornatus.testbackend.util.conversion.MapperConversionAddressToEntity;
import br.com.attornatus.testbackend.util.conversion.MapperConversionPersonToDto;
import br.com.attornatus.testbackend.util.conversion.MapperConversionPersonToEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final EnderecoRepository enderecoRepository;
    private final MapperConversionPersonToDto convertPersonToDto;
    private final MapperConversionPersonToEntity convertPersonToEntity;
    private final MapperConversionAddressToEntity convertAddressToEntity;
    private final MapperConversionAddressToDto convertAddressToDto;

    public PersonServiceImpl(PersonRepository repository, EnderecoRepository enderecoRepository,
                             MapperConversionPersonToDto convertPersonToDto,
                             MapperConversionPersonToEntity convertPersonToEntity,
                             MapperConversionAddressToEntity convertAddressToEntity,
                             MapperConversionAddressToDto convertAddressToDto) {
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
        this.convertPersonToDto = convertPersonToDto;
        this.convertPersonToEntity = convertPersonToEntity;
        this.convertAddressToEntity = convertAddressToEntity;
        this.convertAddressToDto = convertAddressToDto;
    }

    @Override
    public List<PessoaOutput> findAll() {
        List<Pessoa> people = repository.findAll();
        return convertPersonToDto.toCollectionDto(people);
    }

    @Override
    public PessoaOutput findById(Long pessoaId) {
        Pessoa pessoa = repository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));

        return mountPersonResponse(pessoa, null);
    }

    @Override
    public PessoaOutput save(PessoaInput pessoaInput) {
        Pessoa pessoa = convertPersonToEntity.toDomainObject(pessoaInput);
        pessoa = repository.save(pessoa);

        List<Endereco> enderecos = persistirEndereco(pessoaInput, pessoa);

        return mountPersonResponse(pessoa, enderecos);
    }

    private PessoaOutput mountPersonResponse(Pessoa pessoa, List<Endereco> enderecos) {
        List<EnderecoOutput> enderecoOutputs;

        PessoaOutput pessoaOutput = convertPersonToDto.toDto(pessoa);
        if (enderecos == null) {
            enderecoOutputs = convertAddressToDto.toCollectionDto(pessoa.getEnderecos());
        } else {
            enderecoOutputs = convertAddressToDto.toCollectionDto(enderecos);
        }

        pessoaOutput.setEnderecos(enderecoOutputs);
        return pessoaOutput;
    }

    private List<Endereco> persistirEndereco(PessoaInput pessoaInput, Pessoa pessoa) {

        List<Endereco> enderecos;
        enderecos = convertAddressToEntity.toCollectionEntity(pessoaInput.getEnderecos());

        enderecos.forEach(address -> address.setPessoa(pessoa));
        enderecoRepository.saveAll(enderecos);
        return enderecos;
    }
}
