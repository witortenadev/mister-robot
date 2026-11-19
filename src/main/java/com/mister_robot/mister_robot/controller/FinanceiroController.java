package com.mister_robot.mister_robot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Optional<Venda> buscarPorId(@PathVariable Long id) {
        return vendaService.findById(id);
    }

    @PostMapping
    public Venda criar(@RequestBody Venda venda) {
        return vendaService.save(venda);
    }

    @PutMapping("/{id}")
    public Venda atualizar(@PathVariable Long id, @RequestBody Venda venda) {
        return vendaService.update(id, venda);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        vendaService.deleteById(id);
    }
}
