package br.com.attornatus.testbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.io.Serial;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final BindingResult bindingResult;
}
