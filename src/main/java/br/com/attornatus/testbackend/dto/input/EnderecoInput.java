package br.com.attornatus.testbackend.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AddressInput {

    @NotBlank
    private String logradouro;

    @NotBlank
    private String cep;

    @NotBlank
    private String numero;

    @NotBlank
    private String cidade;
}
