package br.com.attornatus.testbackend.service;

import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;

import java.util.List;

public interface PersonService {

    List<PessoaOutput> findAll();
    PessoaOutput findById(Long pessoaId);
    PessoaOutput save(PessoaInput pessoaInput);
}
