package br.com.attornatus.testbackend.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

    @NotBlank
    private String logradouro;

    @NotBlank
    @Pattern(
            regexp = "(^[0-9]{5})-([0-9]{3}$)",
            message = "CEP inválido, deve seguir a nomenclatura xxxxx-xxx"
    )
    private String cep;

    @NotBlank
    private String numero;

    @NotBlank
    private String cidade;

    @NotNull
    private Boolean isPrincipal;
}
