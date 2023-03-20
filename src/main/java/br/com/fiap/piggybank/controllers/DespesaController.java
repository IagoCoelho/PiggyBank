package br.com.fiap.piggybank.controllers;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.piggybank.models.Despesa;
import br.com.fiap.piggybank.repository.DespesaRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    Logger log = LoggerFactory.getLogger(DespesaController.class);

    List<Despesa> despesas = new ArrayList<>();

    @Autowired // Inject dependency IoD
    DespesaRepository repository;

    @GetMapping
    public List<Despesa> index(){
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Despesa> create(@RequestBody Despesa despesa){
        log.info("cadastrando despesa: " + despesa);

        repository.save(despesa);

        return ResponseEntity.status(HttpStatus.CREATED).body(despesa);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Despesa> show(@PathVariable Long id){
        log.info("buscando despesa com id " + id);
        var despesaEncontrada = repository.findById(id);

        if (despesaEncontrada.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(despesaEncontrada.get());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Despesa> destroy(@PathVariable Long id){
        log.info("apagando despesa com id " + id);
        var despesaEncontrada = repository.findById(id);

        if (despesaEncontrada.isEmpty())
            return ResponseEntity.notFound().build();

        repository.delete(despesaEncontrada.get());

        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Despesa> update(@PathVariable Long id, @RequestBody Despesa despesa){
        log.info("alterando despesa com id " + id);
        var despesaEncontrada = repository.findById(id);

        if (despesaEncontrada.isEmpty())
            return ResponseEntity.notFound().build();

        despesa.setId(id);

        repository.save(despesa);

        return ResponseEntity.ok(despesa);

    }


    
    
}