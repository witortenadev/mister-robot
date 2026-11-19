package com.mister_robot.mister_robot;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mister_robot.mister_robot.entity.Jogo;
import com.mister_robot.mister_robot.repository.JogoRepository;

@Service
@Transactional
public class JogoService {

    private final JogoRepository repository;

    public JogoService(JogoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Jogo> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Jogo> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Jogo> findByTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    @Transactional(readOnly = true)
    public List<Jogo> findByGenero(String genero) {
        return repository.findByGeneroIgnoreCase(genero);
    }

    @Transactional(readOnly = true)
    public List<Jogo> findByDesenvolvedora(String desenvolvedora) {
        return repository.findByDesenvolvedoraIgnoreCase(desenvolvedora);
    }

    @Transactional(readOnly = true)
    public List<Jogo> findEmEstoque() {
        return repository.findByQuantidadeEstoqueGreaterThan(0);
    }

    public Jogo save(Jogo jogo) {
        validate(jogo);
        return repository.save(jogo);
    }

    public Jogo update(Long id, Jogo jogo) {
        Jogo atual = findByIdOrThrow(id);
        validate(jogo);
        atual.setTitulo(jogo.getTitulo());
        atual.setDescricao(jogo.getDescricao());
        atual.setGenero(jogo.getGenero());
        atual.setDesenvolvedora(jogo.getDesenvolvedora());
        atual.setIdadeMinima(jogo.getIdadeMinima());
        atual.setQuantidadeEstoque(jogo.getQuantidadeEstoque());
        atual.setPreco(jogo.getPreco());
        return repository.save(atual);
    }

    public void deleteById(Long id) {
        findByIdOrThrow(id);
        repository.deleteById(id);
    }

    public Jogo aumentarEstoque(Long id, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        Jogo jogo = findByIdOrThrow(id);
        jogo.setQuantidadeEstoque(jogo.getQuantidadeEstoque() + quantidade);
        return repository.save(jogo);
    }

    public Jogo diminuirEstoque(Long id, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        Jogo jogo = findByIdOrThrow(id);
        if (jogo.getQuantidadeEstoque() < quantidade) {
            throw new IllegalStateException("Estoque insuficiente");
        }
        jogo.setQuantidadeEstoque(jogo.getQuantidadeEstoque() - quantidade);
        return repository.save(jogo);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }

    private Jogo findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado: " + id));
    }

    private void validate(Jogo jogo) {
        if (jogo == null) {
            throw new IllegalArgumentException("Jogo é obrigatório");
        }
        if (jogo.getTitulo() == null || jogo.getTitulo().isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }
        if (jogo.getPreco() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        if (jogo.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");
        }
        if (jogo.getIdadeMinima() < 0) {
            throw new IllegalArgumentException("Idade mínima não pode ser negativa");
        }
    }
}
