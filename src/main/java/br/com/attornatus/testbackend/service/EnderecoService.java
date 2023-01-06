package br.com.attornatus.testbackend.service;

import br.com.attornatus.testbackend.dto.input.EnderecoInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;

import java.util.List;

public interface EnderecoService {

    List<EnderecoOutput> findById(Long pessoaId);

    EnderecoOutput save(EnderecoInput enderecoInput, Long pessoaId);

    List<EnderecoOutput> alterarEnderecoPrincipal(Long pessoaId, Long enderecoId);
}
