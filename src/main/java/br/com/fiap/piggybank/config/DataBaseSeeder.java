package br.com.fiap.piggybank.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.piggybank.models.Conta;
import br.com.fiap.piggybank.repository.ContaRepository;

@Configuration
public class DataBaseSeeder implements CommandLineRunner {

    @Autowired
    ContaRepository contaRepository;

    @Override
    public void run(String... args) throws Exception {
        contaRepository.saveAll(List.of(
            new Conta(1L, "itau", new BigDecimal(100), "money"),
            new Conta(2L, "bradesco", new BigDecimal(20), "money"),
            new Conta(3L, "carteira", new BigDecimal(2), "money")
            ));

    }
    
}
