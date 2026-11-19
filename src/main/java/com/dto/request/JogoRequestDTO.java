package com.dto.request;

public record JogoRequestDTO (
    String titulo,
    String descricao,
    String genero,
    String desenvolvedora,
    int idadeMinima,
    int quantidadeEstoque,
    double preco
){}
