package br.com.attornatus.testbackend.controller;

import br.com.attornatus.testbackend.dto.input.EnderecoInput;
import br.com.attornatus.testbackend.dto.output.EnderecoOutput;
import br.com.attornatus.testbackend.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@EqualsAndHashCode
@RequestMapping(path = "/enderecos/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{pessoaId}")
    public ResponseEntity<List<EnderecoOutput>> pesquisarEnderecosPessoa(@PathVariable Long pessoaId) {
        return ResponseEntity.ok(enderecoService.findById(pessoaId));
    }

    @PostMapping("/{pessoaId}")
    public ResponseEntity<EnderecoOutput> salvarNovoEnderecoPessoa(
            @PathVariable Long pessoaId,
            @RequestBody @Valid EnderecoInput enderecoInput
    ) {

        EnderecoOutput enderecoOutput = enderecoService.save(enderecoInput, pessoaId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(enderecoOutput.getId()).toUri();
        return ResponseEntity.created(uri).body(enderecoOutput);
    }

    @PostMapping("/{pessoaId}/principal/{enderecoId}")
    public ResponseEntity<List<EnderecoOutput>> alterarEnderecoPrincipal(@PathVariable Long pessoaId, @PathVariable Long enderecoId) {

        List<EnderecoOutput> enderecoOutputs = enderecoService.alterarEnderecoPrincipal(pessoaId, enderecoId);
        return ResponseEntity.ok(enderecoOutputs);
    }
}
