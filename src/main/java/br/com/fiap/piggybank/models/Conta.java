package br.com.fiap.piggybank.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Conta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
   
    @JsonProperty(value = "saldo_inicial", access = Access.WRITE_ONLY)
    private BigDecimal saldoInicial;
    
    private String icone;


}
