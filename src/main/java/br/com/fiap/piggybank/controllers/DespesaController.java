package br.com.fiap.piggybank.controllers;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.piggybank.exception.RestNotFoundException;
import br.com.fiap.piggybank.models.Despesa;
import br.com.fiap.piggybank.models.RestError;
import br.com.fiap.piggybank.repository.DespesaRepository;
import jakarta.validation.Valid;

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
    public ResponseEntity<Object> create(@RequestBody @Valid Despesa despesa){
        //if(result.hasErrors()) return ResponseEntity.badRequest().body(new RestError("Erros na requisição. Total = " + result.getErrorCount()));
        log.info("cadastrando despesa: " + despesa);
        repository.save(despesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(despesa);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Despesa> show(@PathVariable Long id){
        log.info("buscando despesa com id " + id);
        var despesa = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Despesa não encontrada"));
        return ResponseEntity.ok(despesa);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Despesa> destroy(@PathVariable Long id){
        log.info("apagando despesa com id " + id);
        var despesa = repository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("despesa não encontrada"));

        repository.delete(despesa);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Despesa> update(@PathVariable Long id, @RequestBody Despesa despesa){
        log.info("alterando despesa com id " + id);
        repository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("despesa não encontrada"));

        despesa.setId(id);
        repository.save(despesa);

        return ResponseEntity.ok(despesa);

    }


    
    
}