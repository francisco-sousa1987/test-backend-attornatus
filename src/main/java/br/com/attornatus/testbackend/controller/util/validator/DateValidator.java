package br.com.attornatus.testbackend.controller.util.validator;

import br.com.attornatus.testbackend.exception.DataNascimentoInvalidaException;

import static org.apache.commons.validator.GenericValidator.isDate;

public class DateValidator {

    public static void validaDataNascimento(String dataNascimento) {
        boolean isValidDate = isDate(dataNascimento, "yyyy-MM-dd", true);

        if (!isValidDate) {
            throw new DataNascimentoInvalidaException(dataNascimento);
        }
    }
}
