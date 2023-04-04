package br.com.fiap.piggybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.piggybank.models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    
}
