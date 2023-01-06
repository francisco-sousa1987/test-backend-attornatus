package br.com.attornatus.testbackend.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressOutput {

    private Long id;

    private String logradouro;

    private String cep;

    private String numero;

    private String cidade;
}
