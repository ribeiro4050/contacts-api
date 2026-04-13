package br.ifsp.contacts_api.dto;

import br.ifsp.contacts_api.model.Contact;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

public record ContactDTO(
        Long id,

        @NotBlank(message = "O nome não pode estar vazio")
        String nome,

        @NotBlank(message = "O email não pode estar vazio")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "O telefone não pode estar vazio")
        @Size(min = 8, max = 15, message = "O telefone deve ter entre 8 e 15 caracteres")
        @Pattern(regexp = "\\d+", message = "O telefone deve conter apenas números")
        String telefone,

        @NotEmpty(message = "O contato deve ter pelo menos um endereço")
        List<AddressDTO> addresses) {

    public ContactDTO(Contact contact){
        this(
                contact.getId(),
                contact.getNome(),
                contact.getEmail(),
                contact.getTelefone(),
                contact.getAddresses().stream().map(AddressDTO::new).toList()
                );
    }
}
