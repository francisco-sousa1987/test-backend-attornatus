package br.com.attornatus.testbackend.factory;

import br.com.attornatus.testbackend.dto.input.EnderecoInput;
import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;
import br.com.attornatus.testbackend.entity.Endereco;
import br.com.attornatus.testbackend.entity.Pessoa;

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

    public static Pessoa createPessoa() {

        Pessoa pessoa = new Pessoa();
        Endereco endereco1 = new Endereco();
        Endereco endereco2 = new Endereco();

        pessoa.setId(1L);
        pessoa.setNome("Lara Rebeca Oliveira");
        pessoa.setDataNascimento(new Date());

        endereco1.setId(1L);
        endereco1.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        endereco1.setCep("69077-749");
        endereco1.setNumero("532");
        endereco1.setCidade("Manaus");
        endereco1.setIsPrincipal(true);

        endereco2.setId(2L);
        endereco2.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        endereco2.setCep("69077-749");
        endereco2.setNumero("532");
        endereco2.setCidade("Manaus");
        endereco2.setIsPrincipal(true);

        pessoa.setEnderecos(List.of(endereco1, endereco2));

        return pessoa;
    }

    public static Endereco createEndereco() {

        Long id = 1L;

        Endereco endereco = new Endereco();

        endereco.setId(id);
        endereco.setLogradouro("Rua Professora Joana D'arc Ribeiro");
        endereco.setCep("69077-749");
        endereco.setNumero("532");
        endereco.setCidade("Manaus");
        endereco.setIsPrincipal(false);

        return endereco;
    }
}
