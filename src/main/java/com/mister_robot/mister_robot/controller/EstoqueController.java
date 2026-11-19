package com.mister_robot.mister_robot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.dto.request.JogoRequestDTO;
import com.mister_robot.mister_robot.JogoService;
import com.mister_robot.mister_robot.entity.Jogo;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final JogoService jogoService;

    public EstoqueController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @GetMapping
    public List<Jogo> listar() {
        return jogoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        var jogo = jogoService.findById(id);

        if (jogo.isPresent()) {
            return ResponseEntity.ok(jogo.get());
        }

        return ResponseEntity.status(404)
                .body("Jogo não encontrado com o id: " + id);
    }

    @GetMapping("/buscar")
    public List<Jogo> buscarPorTitulo(@RequestParam String titulo) {
        return jogoService.findByTitulo(titulo);
    }

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody JogoRequestDTO dto) {
        try {
            Jogo jogoCriado = jogoService.save(dto);
            return ResponseEntity.status(201).body(jogoCriado);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Não foi possível criar o jogo");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody JogoRequestDTO dto) {
        if (jogoService.findById(id).isEmpty()) {
            return ResponseEntity.status(404)
                    .body("Jogo não encontrado com o id: " + id);
        }

        try {
            Jogo jogoAtualizado = jogoService.update(id, dto);
            return ResponseEntity.ok(jogoAtualizado);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Não foi possível atualizar o jogo");
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> tratarJsonInvalido(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest()
                .body("JSON inválido: verifique os tipos dos campos enviados");
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        jogoService.deleteById(id);
    }

    @PostMapping("/{id}/entrada")
    public Jogo entradaEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        return jogoService.aumentarEstoque(id, quantidade);
    }

    @PostMapping("/{id}/saida")
    public Jogo saidaEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        return jogoService.diminuirEstoque(id, quantidade);
    }
}
