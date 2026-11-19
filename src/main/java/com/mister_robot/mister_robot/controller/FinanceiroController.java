package com.mister_robot.mister_robot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.request.VendaRequestDTO;
import com.mister_robot.mister_robot.VendaService;
import com.mister_robot.mister_robot.entity.Venda;

@RestController
@RequestMapping("/vendas")
public class FinanceiroController {

    private final VendaService vendaService;

    public FinanceiroController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping
    public List<Venda> listar() {
        return vendaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        Optional<Venda> venda = vendaService.findById(id);
        if (venda.isPresent()) {
            return ResponseEntity.ok(venda.get());
        }
        return ResponseEntity.status(404).body("Venda não encontrada com o id: " + id);
    }

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody VendaRequestDTO dto) {
        try {
            return ResponseEntity.status(201).body(vendaService.save(dto));
        } catch (IllegalArgumentException | IllegalStateException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Não foi possível concluir a venda");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody VendaRequestDTO dto) {
        try {
            return ResponseEntity.ok(vendaService.update(id, dto));
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().contains("não encontrada")) {
                return ResponseEntity.status(404).body(exception.getMessage());
            }
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Não foi possível atualizar a venda");
        }
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        vendaService.deleteById(id);
    }
}
