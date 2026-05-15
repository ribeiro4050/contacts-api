package br.ifsp.contacts_api.dto;

import br.ifsp.contacts_api.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDTO(

        Long id,
        @NotBlank(message = "A rua não pode estar vazia")
        String rua,

        @NotBlank(message = "A cidade não pode estar vazia")
        String cidade,

        @NotBlank(message = "O estado não pode estar vazio")
        @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 caracteres (sigla)")
        @Pattern(regexp = "[A-Z]{2}", message = "O estado deve ser representado por duas letras maiúsculas")
        String estado,

        @NotBlank(message = "O CEP não pode estar vazio")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 99999-999")
        String cep) {

    public AddressDTO(Address address){
        this(
                address.getId(),
                address.getRua(),
                address.getCidade(),
                address.getEstado(),
                address.getCep()
        );
    }
}
