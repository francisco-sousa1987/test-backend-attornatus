package br.com.attornatus.testbackend.controller;

import br.com.attornatus.testbackend.dto.input.PessoaInput;
import br.com.attornatus.testbackend.dto.output.PessoaOutput;
import br.com.attornatus.testbackend.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static br.com.attornatus.testbackend.controller.util.validator.DateValidator.validaDataNascimento;

@RestController
@RequestMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PessoaOutput>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "/{pessoaId}")
    public ResponseEntity<PessoaOutput> findOne(@PathVariable Long pessoaId) {
        return ResponseEntity.ok(service.findById(pessoaId));
    }

    @PostMapping
    public ResponseEntity<PessoaOutput> save(@RequestBody @Valid PessoaInput pessoaInput) {
        validaDataNascimento(pessoaInput.getDataNascimento());

        PessoaOutput pessoaSalva = service.save(pessoaInput);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pessoaSalva.getId()).toUri();
        return ResponseEntity.created(uri).body(pessoaSalva);
    }

    @DeleteMapping(path = "/{pessoaId}")
    public ResponseEntity<PessoaOutput> delete(@PathVariable Long pessoaId) {
        service.delete(pessoaId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{pessoaId}")
    public ResponseEntity<PessoaOutput> update(@PathVariable Long pessoaId, @RequestBody @Valid PessoaInput pessoaInput) {
        validaDataNascimento(pessoaInput.getDataNascimento());
        PessoaOutput pessoaOutput = service.update(pessoaId, pessoaInput);
        return ResponseEntity.ok().body(pessoaOutput);
    }
}
