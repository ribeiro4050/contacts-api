package br.ifsp.contacts_api.dto;

import br.ifsp.contacts_api.model.Contact;

import java.util.List;

public record ContactDTO(Long id, String nome, String email, String telefone, List<AddressDTO> addresses) {
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
