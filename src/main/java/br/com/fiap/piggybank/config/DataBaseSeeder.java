package br.com.fiap.piggybank.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.piggybank.models.Conta;
import br.com.fiap.piggybank.models.Despesa;
import br.com.fiap.piggybank.repository.ContaRepository;
import br.com.fiap.piggybank.repository.DespesaRepository;

@Configuration
public class DataBaseSeeder implements CommandLineRunner {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    DespesaRepository despesaRepository;

    @Override
    public void run(String... args) throws Exception {
        Conta c1 = new Conta(1L, "itau", new BigDecimal(100), "money");
        Conta c2 = new Conta(2L, "bradesco", new BigDecimal(20), "money");
        Conta c3 = new Conta(3L, "carteira", new BigDecimal(2), "money");
        contaRepository.saveAll(List.of(c1,c2,c3));

            despesaRepository.saveAll(List.of(
                Despesa.builder().valor(new BigDecimal(50)).descricao("cinema").data(LocalDate.now()).conta(c1).build(),
                Despesa.builder().valor(new BigDecimal(98)).descricao("aluguel").data(LocalDate.now()).conta(c3).build(),
                Despesa.builder().valor(new BigDecimal(65)).descricao("netflix").data(LocalDate.now()).conta(c2).build(),
                Despesa.builder().valor(new BigDecimal(87)).descricao("restaurante").data(LocalDate.now()).conta(c2).build(),
                Despesa.builder().valor(new BigDecimal(12)).descricao("ifood").data(LocalDate.now()).conta(c3).build(),
                Despesa.builder().valor(new BigDecimal(50)).descricao("cinema").data(LocalDate.now()).conta(c1).build(),
                Despesa.builder().valor(new BigDecimal(56)).descricao("estacionamento").data(LocalDate.now()).conta(c3).build(),
                Despesa.builder().valor(new BigDecimal(76)).descricao("cinema").data(LocalDate.now()).conta(c1).build(),
                Despesa.builder().valor(new BigDecimal(78)).descricao("transporte").data(LocalDate.now()).conta(c2).build(),
                Despesa.builder().valor(new BigDecimal(100)).descricao("pedagiob").data(LocalDate.now()).conta(c1).build()
            ));

    }
    
}
