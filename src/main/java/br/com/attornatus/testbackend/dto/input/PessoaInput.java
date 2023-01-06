package br.com.attornatus.testbackend.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PessoaInput {

    @NotBlank
    private String nome;

    @NotNull
    private String dataNascimento;

    @Valid
    private List<EnderecoInput> enderecos = new ArrayList<>();
}
