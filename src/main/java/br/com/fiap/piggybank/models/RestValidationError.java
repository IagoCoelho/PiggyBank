package br.com.fiap.piggybank.models;

public record RestValidationError (
    Integer code,
    String field,
    String mesage
){}
     

