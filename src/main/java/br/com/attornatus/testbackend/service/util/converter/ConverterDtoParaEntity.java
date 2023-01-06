package br.com.attornatus.testbackend.util.converter;

import br.com.attornatus.testbackend.dto.input.EnderecoInput;
import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;
import br.com.attornatus.testbackend.entity.Endereco;
import br.com.attornatus.testbackend.entity.Pessoa;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverterDtoParaEntity {

    private final ModelMapper modelMapper;

    public ConverterDtoParaEntity(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<Endereco> toCollectionEnderecoEntity(List<EnderecoInput> enderecos) {

        return enderecos.stream()
                .map(this::toEntityEndereco)
                .collect(Collectors.toList());
    }

    public Endereco toEntityEndereco(EnderecoInput enderecoInput) {
        return modelMapper.map(enderecoInput, Endereco.class);
    }

    public Pessoa toEntityPessoa(PessoaInput pessoaInput) {
        return modelMapper.map(pessoaInput, Pessoa.class);
    }

    public void copyToPessoaDomain(PessoaInput pessoaInput, Pessoa pessoa) {
        modelMapper.map(pessoaInput, pessoa);
    }
}
