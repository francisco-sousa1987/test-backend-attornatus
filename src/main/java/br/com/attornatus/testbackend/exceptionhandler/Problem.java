package br.com.attornatus.testbackend.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    private final Integer status;

    private final OffsetDateTime timestamp;

    private final String title;

    private final String detail;

    private final String userMessage;

    private final List<Object> objects;

    @Getter
    @Builder
    public static class Object {

        private final String name;

        private final String userMessage;
    }
}


