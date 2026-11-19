package com.mister_robot.mister_robot.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Entity @Table(name = "jogos")
public class Jogo {

@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

@Column(nullable = false, length = 150)
    private String titulo;

@Column(length = 500)
    private String descricao;

@Column(length = 150)
    private String genero;

@Column(length = 150)
    private String desenvolvedora;

    private int idadeMinima; 

@Column(nullable = false)
    private int quantidadeEstoque;

@Column(nullable = false)
    private double preco;
}
