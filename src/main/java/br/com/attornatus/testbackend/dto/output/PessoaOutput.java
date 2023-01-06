package br.com.attornatus.testbackend.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonOutput {

    private Long id;

    private String nome;

    private String dataNascimento;

    private List<AddressOutput> enderecos = new ArrayList<>();
}
