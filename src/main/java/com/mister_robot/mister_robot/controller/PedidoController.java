package com.mister_robot.mister_robot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.request.VendaRequestDTO;
import com.mister_robot.mister_robot.VendaService;
import com.mister_robot.mister_robot.entity.Venda;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final VendaService vendaService;

    public PedidoController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping
    public List<Venda> listar() {
        return vendaService.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> realizarVenda(@RequestBody VendaRequestDTO dto) {
        try {
            return ResponseEntity.status(201).body(vendaService.save(dto));
        } catch (IllegalArgumentException | IllegalStateException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Não foi possível realizar o pedido");
        }
    }
}
