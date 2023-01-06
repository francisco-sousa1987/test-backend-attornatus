package br.com.attornatus.testbackend.exceptionhandler;

import br.com.attornatus.testbackend.exception.DataNascimentoInvalidaException;
import br.com.attornatus.testbackend.exception.PessoaNaoEncontradaException;
import br.com.attornatus.testbackend.exception.ValidacaoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
        log.info("Inside method: handleValidacaoException()");
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<?> handlePessoaNaoEncontrada(PessoaNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataNascimentoInvalidaException.class)
    public ResponseEntity<?> handleDataNascimentoInvalida(DataNascimentoInvalidaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage("Verifique se a data está escrita de forma correta ou seguindo o padrão yyyy-MM-dd").build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("Inside method: handleMethodArgumentNotValid()");
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(
            Exception ex,
            BindingResult bindingResult,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {

        log.info("Inside method: handleValidationInternal()");

        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }
                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                }).collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, problemType, detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, ProblemType problemType, String detail) {
        log.info("inside method: createProblemBuilder()");
        return Problem.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .title(problemType.getTitle())
                .detail(detail);
    }
}
