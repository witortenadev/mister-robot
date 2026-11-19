package com.dto.request;

import java.util.List;

import com.mister_robot.mister_robot.entity.FormaPagamento;

public record VendaRequestDTO(
        String clienteNome,
        FormaPagamento formaPagamento,
        List<VendaItemRequestDTO> itens
) {
}
