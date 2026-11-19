package com.mister_robot.mister_robot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.request.VendaItemRequestDTO;
import com.dto.request.VendaRequestDTO;
import com.mister_robot.mister_robot.entity.FormaPagamento;
import com.mister_robot.mister_robot.entity.ItemVenda;
import com.mister_robot.mister_robot.entity.Jogo;
import com.mister_robot.mister_robot.entity.StatusVenda;
import com.mister_robot.mister_robot.entity.Venda;
import com.mister_robot.mister_robot.repository.JogoRepository;
import com.mister_robot.mister_robot.repository.VendaRepository;

@Service
@Transactional
public class VendaService {

    private final VendaRepository repository;
    private final JogoRepository jogoRepository;

    public VendaService(VendaRepository repository, JogoRepository jogoRepository) {
        this.repository = repository;
        this.jogoRepository = jogoRepository;
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
    public List<Venda> findByStatus(StatusVenda status) {
        return repository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Venda> findByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
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

    public Venda save(VendaRequestDTO dto) {
        validate(dto);

        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());
        venda.setClienteNome(dto.clienteNome().trim());
        venda.setFormaPagamento(dto.formaPagamento());
        venda.setStatus(StatusVenda.FINALIZADA);

        double valorTotal = 0;
        for (VendaItemRequestDTO itemDto : dto.itens()) {
            Jogo jogo = jogoRepository.findById(itemDto.jogoId())
                    .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado: " + itemDto.jogoId()));

            if (itemDto.quantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero");
            }
            if (jogo.getQuantidadeEstoque() < itemDto.quantidade()) {
                throw new IllegalStateException("Estoque insuficiente para o jogo: " + jogo.getTitulo());
            }

            jogo.setQuantidadeEstoque(jogo.getQuantidadeEstoque() - itemDto.quantidade());
            jogoRepository.save(jogo);

            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setJogo(jogo);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(jogo.getPreco());
            venda.getItens().add(item);
            valorTotal += jogo.getPreco() * itemDto.quantidade();
        }

        venda.setValorTotal(valorTotal);
        return repository.save(venda);
    }

    public Venda update(Long id, VendaRequestDTO dto) {
        Venda atual = findByIdOrThrow(id);
        if (dto == null) {
            throw new IllegalArgumentException("Dados da venda são obrigatórios");
        }
        if (dto.clienteNome() == null || dto.clienteNome().isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        if (dto.formaPagamento() == null) {
            throw new IllegalArgumentException("Forma de pagamento é obrigatória");
        }
        atual.setClienteNome(dto.clienteNome().trim());
        atual.setFormaPagamento(dto.formaPagamento());
        return repository.save(atual);
    }

    public Venda alterarStatus(Long id, StatusVenda status) {
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

    private void validate(VendaRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dados da venda são obrigatórios");
        }
        if (dto.clienteNome() == null || dto.clienteNome().isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        if (dto.formaPagamento() == null) {
            throw new IllegalArgumentException("Forma de pagamento é obrigatória");
        }
        if (dto.itens() == null || dto.itens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um jogo");
        }
    }
}
