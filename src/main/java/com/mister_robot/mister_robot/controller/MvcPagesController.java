package com.mister_robot.mister_robot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mister_robot.mister_robot.JogoService;
import com.mister_robot.mister_robot.VendaService;

@Controller
public class MvcPagesController {

    private final JogoService jogoService;
    private final VendaService vendaService;

    public MvcPagesController(JogoService jogoService, VendaService vendaService) {
        this.jogoService = jogoService;
        this.vendaService = vendaService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("jogos", jogoService.count());
        model.addAttribute("vendas", vendaService.count());
        model.addAttribute("estoque", jogoService.findAll().stream()
                .mapToInt(jogo -> jogo.getQuantidadeEstoque()).sum());
        return "index";
    }
}
