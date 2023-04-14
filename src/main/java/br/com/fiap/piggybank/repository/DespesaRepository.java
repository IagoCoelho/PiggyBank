package br.com.fiap.piggybank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.piggybank.models.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    Page<Despesa> findByDescricaoContaining(String descricao, Pageable pageable);
    
}