package com.mister_robot.mister_robot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mister_robot.mister_robot.entity.FormaPagamento;
import com.mister_robot.mister_robot.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByStatus(char status);
    List<Venda> findByDataBetween(LocalDate inicio, LocalDate fim);
    List<Venda> findByFormaPagamento(FormaPagamento formaPagamento);
}
