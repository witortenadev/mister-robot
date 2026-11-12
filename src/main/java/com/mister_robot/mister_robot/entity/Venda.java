package com.mister_robot.mister_robot.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor 
public class Venda {

    long id;
    LocalDate data;
    double valorTotal;
    char status;
    FormaPagamento formaPagamento;

}