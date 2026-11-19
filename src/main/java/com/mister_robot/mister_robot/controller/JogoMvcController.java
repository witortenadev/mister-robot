package com.mister_robot.mister_robot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mister_robot.mister_robot.JogoService;

@Controller
@RequestMapping("/pages/jogos")
public class JogoMvcController {

    private final JogoService jogoService;

    public JogoMvcController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("jogos", jogoService.findAll());
        return "estoque";
    }

    @GetMapping("/novo")
    public String novo() {
        return "jogo-form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("jogo", jogoService.findById(id).orElse(null));
        model.addAttribute("jogoId", id);
        return "jogo-form";
    }
}
