package com.mister_robot.mister_robot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstoqueController {    
    public EstoqueController (){
    
    }  

    @GetMapping("/jogo")
    public String getJogo(){
        return "Jogo";
    }
}
