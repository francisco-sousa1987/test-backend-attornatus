package br.com.attornatus.testbackend.exception;

public class DataNascimentoInvalidaException extends RuntimeException {

    public DataNascimentoInvalidaException(String mensagem) {
        super(String.format("Data de nascimento inválida %s", mensagem));
    }
}
