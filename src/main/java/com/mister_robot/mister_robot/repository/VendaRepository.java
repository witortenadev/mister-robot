package com.mister_robot.mister_robot.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mister_robot.mister_robot.entity.FormaPagamento;
import com.mister_robot.mister_robot.entity.StatusVenda;
import com.mister_robot.mister_robot.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByStatus(StatusVenda status);
    List<Venda> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Venda> findByFormaPagamento(FormaPagamento formaPagamento);
}
