package br.com.attornatus.testbackend.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    DADOS_INVALIDOS("Dados inválidos"),
    RECURSO_NAO_ENCONTRADO("Recurso não encontrado");

    private final String title;

    ProblemType(String title) {
        this.title = title;
    }
}
