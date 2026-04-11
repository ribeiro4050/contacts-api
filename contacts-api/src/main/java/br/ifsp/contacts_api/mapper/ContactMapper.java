package br.ifsp.contacts_api.mapper;

import br.ifsp.contacts_api.dto.ContactDTO;
import br.ifsp.contacts_api.dto.AddressDTO;
import br.ifsp.contacts_api.model.Contact;
import br.ifsp.contacts_api.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring") // Faz com que o Spring gerencie o Mapper como um Bean
public interface ContactMapper {

    // Converte a Entidade Contact para o seu DTO
    ContactDTO toDTO(Contact contact);

    // Converte o seu DTO para a Entidade Contact (Útil no POST/PUT)
    Contact toEntity(ContactDTO contactDTO);

    // Mapeamento de listas (O MapStruct faz o loop automaticamente)
    List<ContactDTO> toDTOList(List<Contact> contacts);

    // Mapeamento individual de endereço (usado internamente pelo MapStruct)
    AddressDTO toAddressDTO(Address address);

    Address toAddressEntity(AddressDTO addressDTO);
}