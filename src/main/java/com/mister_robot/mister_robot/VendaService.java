package com.mister_robot.mister_robot;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mister_robot.mister_robot.entity.FormaPagamento;
import com.mister_robot.mister_robot.entity.Venda;
import com.mister_robot.mister_robot.repository.VendaRepository;

@Service
@Transactional
public class VendaService {

    private final VendaRepository repository;

    public VendaService(VendaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Venda> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Venda> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Venda> findByStatus(char status) {
        return repository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Venda> findByPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Datas de início e fim são obrigatórias");
        }
        if (fim.isBefore(inicio)) {
            throw new IllegalArgumentException("Data final não pode ser anterior à data inicial");
        }
        return repository.findByDataBetween(inicio, fim);
    }

    @Transactional(readOnly = true)
    public List<Venda> findByFormaPagamento(FormaPagamento formaPagamento) {
        if (formaPagamento == null) {
            throw new IllegalArgumentException("Forma de pagamento é obrigatória");
        }
        return repository.findByFormaPagamento(formaPagamento);
    }

    public Venda save(Venda venda) {
        validate(venda);
        venda.setId(null);
        return repository.save(venda);
    }

    public Venda update(Long id, Venda venda) {
        Venda atual = findByIdOrThrow(id);
        validate(venda);
        atual.setData(venda.getData());
        atual.setValorTotal(venda.getValorTotal());
        atual.setStatus(venda.getStatus());
        atual.setFormaPagamento(venda.getFormaPagamento());
        return repository.save(atual);
    }

    public Venda alterarStatus(Long id, char status) {
        Venda venda = findByIdOrThrow(id);
        venda.setStatus(status);
        return repository.save(venda);
    }

    public void deleteById(Long id) {
        findByIdOrThrow(id);
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }

    private Venda findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada: " + id));
    }

    private void validate(Venda venda) {
        if (venda == null) {
            throw new IllegalArgumentException("Venda é obrigatória");
        }
        if (venda.getData() == null) {
            throw new IllegalArgumentException("Data da venda é obrigatória");
        }
        if (venda.getValorTotal() < 0) {
            throw new IllegalArgumentException("Valor total não pode ser negativo");
        }
        if (venda.getFormaPagamento() == null) {
            throw new IllegalArgumentException("Forma de pagamento é obrigatória");
        }
    }
}
