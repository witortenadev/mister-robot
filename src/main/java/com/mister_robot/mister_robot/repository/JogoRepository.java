package com.mister_robot.mister_robot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mister_robot.mister_robot.entity.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
    List<Jogo> findByTituloContainingIgnoreCase(String titulo);
    List<Jogo> findByGeneroIgnoreCase(String genero);
    List<Jogo> findByDesenvolvedoraIgnoreCase(String desenvolvedora);
    List<Jogo> findByQuantidadeEstoqueGreaterThan(int quantidade);
}