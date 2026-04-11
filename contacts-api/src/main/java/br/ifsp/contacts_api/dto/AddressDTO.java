package br.ifsp.contacts_api.dto;

import br.ifsp.contacts_api.model.Address;

public record AddressDTO(Long id, String rua, String cidade, String estado, String cep) {
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
