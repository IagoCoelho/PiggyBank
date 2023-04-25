package br.com.fiap.piggybank.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.piggybank.exception.RestNotFoundException;
import br.com.fiap.piggybank.models.Despesa;
import br.com.fiap.piggybank.repository.ContaRepository;
import br.com.fiap.piggybank.repository.DespesaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/despesas")
public class DespesaController {

    List<Despesa> despesas = new ArrayList<>();

    @Autowired // IoD IoC
    DespesaRepository despesaRepository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        Page<Despesa> despesas = (busca == null)?
            despesaRepository.findAll(pageable):
            despesaRepository.findByDescricaoContaining(busca, pageable);

        return assembler.toModel(despesas.map(Despesa::toModel));
    }

    //assembler

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Despesa despesa){
        log.info("cadastrando despesa: " + despesa);
        despesaRepository.save(despesa);
        despesa.setConta(contaRepository.findById(despesa.getConta().getId()).get());
        return ResponseEntity
            .created(despesa.toModel().getRequiredLink("self").toUri())
            .body(despesa.toModel());
    }
    
    @GetMapping("{id}")
    public EntityModel<Despesa> show(@PathVariable Long id){
        log.info("buscando despesa com id " + id);
        var despesa = despesaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Despesa não encontrada"));
        
        return despesa.toModel();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Despesa> destroy(@PathVariable Long id){
        log.info("apagando despesa com id " + id);
        var despesa = despesaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("despesa não encontrada"));

        despesaRepository.delete(despesa);

        return ResponseEntity.noContent().build();

    } 

    @PutMapping("{id}")
    public EntityModel<Despesa> update(@PathVariable Long id, @RequestBody @Valid Despesa despesa){
        log.info("alterando despesa com id " + id);
        despesaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("despesa não encontrada"));

        despesa.setId(id);
        despesaRepository.save(despesa);

        return despesa.toModel();

    }


    
    
}