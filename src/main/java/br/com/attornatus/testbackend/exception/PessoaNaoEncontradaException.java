package br.com.attornatus.testbackend.exception;

public class PessoaNaoEncontradaException extends RuntimeException {

    public PessoaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PessoaNaoEncontradaException(Long pessoaId) {
        this(String.format("Não existe um cadastro de pessoa com código %d", pessoaId));
    }
}
