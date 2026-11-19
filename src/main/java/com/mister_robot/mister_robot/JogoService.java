package com.mister_robot.mister_robot;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.request.JogoRequestDTO;
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

    public Jogo save(JogoRequestDTO dto) {
        validate(dto);

        Jogo jogo = new Jogo();
        jogo.setTitulo(dto.titulo());
        jogo.setDescricao(dto.descricao());
        jogo.setGenero(dto.genero());
        jogo.setDesenvolvedora(dto.desenvolvedora());
        jogo.setIdadeMinima(dto.idadeMinima());
        jogo.setQuantidadeEstoque(dto.quantidadeEstoque());
        jogo.setPreco(dto.preco());

        return repository.save(jogo);
    }

    public Jogo update(Long id, JogoRequestDTO dto) {
        Jogo atual = findByIdOrThrow(id);
        validate(dto);
        atual.setTitulo(dto.titulo());
        atual.setDescricao(dto.descricao());
        atual.setGenero(dto.genero());
        atual.setDesenvolvedora(dto.desenvolvedora());
        atual.setIdadeMinima(dto.idadeMinima());
        atual.setQuantidadeEstoque(dto.quantidadeEstoque());
        atual.setPreco(dto.preco());
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

    private void validate(JogoRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dados do jogo são obrigatórios");
        }
        if (dto.titulo() == null || dto.titulo().isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }
        if (dto.preco() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        if (dto.quantidadeEstoque() < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");
        }
        if (dto.idadeMinima() < 0) {
            throw new IllegalArgumentException("Idade mínima não pode ser negativa");
        }
    }
}
