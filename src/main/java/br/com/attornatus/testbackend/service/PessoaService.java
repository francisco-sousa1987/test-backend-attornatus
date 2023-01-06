package br.com.attornatus.testbackend.service;

import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;

import java.util.List;

public interface PessoaService {

    List<PessoaOutput> findAll();
    PessoaOutput findById(Long pessoaId);
    PessoaOutput save(PessoaInput pessoaInput);
    void delete(Long pessoaId);
    PessoaOutput update(Long pessoaId, PessoaInput pessoaInput);
}
