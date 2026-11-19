package com.mister_robot.mister_robot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mister_robot.mister_robot.JogoService;
import com.mister_robot.mister_robot.VendaService;

@Controller
@RequestMapping("/pages/vendas")
public class VendaMvcController {

    private final VendaService vendaService;
    private final JogoService jogoService;

    public VendaMvcController(VendaService vendaService, JogoService jogoService) {
        this.vendaService = vendaService;
        this.jogoService = jogoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vendas", vendaService.findAll());
        return "vendas";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("jogos", jogoService.findEmEstoque());
        return "venda-form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("venda", vendaService.findById(id).orElse(null));
        model.addAttribute("vendaId", id);
        return "venda-form";
    }
}
