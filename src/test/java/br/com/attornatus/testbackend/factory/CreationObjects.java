package br.com.attornatus.testbackend.controller.factory;

import br.com.attornatus.testbackend.dto.input.EnderecoInput;
import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;

import java.util.Date;
import java.util.List;

public class CreationObjects {

    public static PessoaOutput createPessoaOutput() {

        Long id = 1L;

        PessoaOutput pessoaOutput = new PessoaOutput();
        pessoaOutput.setId(id);
        pessoaOutput.setNome("Lara Rebeca Oliveira");
        pessoaOutput.setDataNascimento(new Date());

        EnderecoOutput enderecoOutput = new EnderecoOutput();
        enderecoOutput.setId(id);
        enderecoOutput.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        enderecoOutput.setCep("69077-749");
        enderecoOutput.setNumero("532");
        enderecoOutput.setCidade("Manaus");
        enderecoOutput.setIsPrincipal(false);

        pessoaOutput.setEnderecos(List.of(enderecoOutput));

        return pessoaOutput;
    }

    public static PessoaInput createPessoaInput() {

        PessoaInput pessoaInput = new PessoaInput();
        pessoaInput.setNome("Lara Rebeca Oliveira");
        pessoaInput.setDataNascimento("1987-09-15");

        EnderecoInput enderecoInput = new EnderecoInput();
        enderecoInput.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        enderecoInput.setCep("69077-749");
        enderecoInput.setNumero("532");
        enderecoInput.setCidade("Manaus");
        enderecoInput.setIsPrincipal(false);

        pessoaInput.setEnderecos(List.of(enderecoInput));

        return pessoaInput;
    }

    public static EnderecoOutput createEnderecoOutput() {

        Long id = 1L;

        EnderecoOutput enderecoOutput = new EnderecoOutput();
        enderecoOutput.setId(id);
        enderecoOutput.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        enderecoOutput.setCep("69077-749");
        enderecoOutput.setNumero("532");
        enderecoOutput.setCidade("Manaus");
        enderecoOutput.setIsPrincipal(false);

        return enderecoOutput;
    }

    public static EnderecoInput createEnderecoInput() {

        EnderecoInput enderecoInput = new EnderecoInput();

        enderecoInput.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        enderecoInput.setCep("69077-749");
        enderecoInput.setNumero("532");
        enderecoInput.setCidade("Manaus");
        enderecoInput.setIsPrincipal(false);

        return enderecoInput;
    }
}
