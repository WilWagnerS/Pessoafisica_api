package br.com.senac.Pessoafisica_api.Controllers;

import br.com.senac.Pessoafisica_api.dtos.PessoaJuridicaDto;
import br.com.senac.Pessoafisica_api.entidades.PessoaJuridica;
import br.com.senac.Pessoafisica_api.services.PessoaJuridicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class PessoaJuridicaController {
    private PessoaJuridicaService pessoaJuridicaService;

    public PessoaJuridicaController(PessoaJuridicaService pessoaJuridicaService) {
        this.pessoaJuridicaService = pessoaJuridicaService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PessoaJuridica>> listar() {
        return ResponseEntity.ok(pessoaJuridicaService.listar());
    }

    @PostMapping("/criar")
    public ResponseEntity<PessoaJuridica> criar(@RequestBody PessoaJuridicaDto pessoa) {
        try {
            return ResponseEntity.ok(pessoaJuridicaService.criar(pessoa));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(null);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PessoaJuridica> atualizar(@PathVariable Long id, @RequestBody PessoaJuridicaDto pessoa) {
        try {
            return ResponseEntity.ok(pessoaJuridicaService.atualizar(id, pessoa));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(null);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            pessoaJuridicaService.deletar(id);
            return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(null);
        }
    }
}