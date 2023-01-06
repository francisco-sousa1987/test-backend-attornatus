package br.com.attornatus.testbackend.util.converter;

import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;
import br.com.attornatus.testbackend.entity.Endereco;
import br.com.attornatus.testbackend.entity.Pessoa;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverterEntityParaDto {

    private final ModelMapper modelMapper;

    public ConverterEntityParaDto(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<EnderecoOutput> toCollectionEnderecoDto(List<Endereco> enderecos) {

        return enderecos.stream()
                .map(this::toEnderecoDto)
                .collect(Collectors.toList());
    }

    public EnderecoOutput toEnderecoDto(Endereco endereco) {
        return modelMapper.map(endereco, EnderecoOutput.class);
    }

    public PessoaOutput toPessoaDto(Pessoa pessoa) {
        return modelMapper.map(pessoa, PessoaOutput.class);
    }

    public List<PessoaOutput> toCollectionPessoaDto(List<Pessoa> pessoas) {

        return pessoas.stream()
                .map(this::toPessoaDto)
                .collect(Collectors.toList());
    }
}
