package com.mister_robot.mister_robot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor 
public class Jogo {

    long id;
    String titulo;
    String descricao;
    String genero;
    String desenvolvedora;
    int idadeMinima; 
    int quantidadeEstoque;
    double preco;
   
}
