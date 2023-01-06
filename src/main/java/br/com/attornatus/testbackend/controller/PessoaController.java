package br.com.attornatus.testbackend.controller;

import br.com.attornatus.testbackend.dto.input.PersonInput;
import br.com.attornatus.testbackend.dto.output.PersonOutput;
import br.com.attornatus.testbackend.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static br.com.attornatus.testbackend.util.validator.DateValidator.validaDataNascimento;

@RestController
@RequestMapping(path = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PersonOutput>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "/{pessoaId}")
    public ResponseEntity<PersonOutput> findOne(@PathVariable Long pessoaId) {
        PersonOutput person = service.findById(pessoaId);
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<PersonOutput> save(@RequestBody @Valid PersonInput dto) {
        validaDataNascimento(dto.getDataNascimento());
        PersonOutput savedPerson = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPerson.getId()).toUri();
        return ResponseEntity.created(uri).body(savedPerson);
    }
}
