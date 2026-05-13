package br.ifsp.contacts_api.mapper;

import br.ifsp.contacts_api.dto.AddressDTO;
import br.ifsp.contacts_api.dto.ContactDTO;
import br.ifsp.contacts_api.model.Address;
import br.ifsp.contacts_api.model.Contact;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-16T01:17:25-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 26 (Oracle Corporation)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public ContactDTO toDTO(Contact contact) {
        if ( contact == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String email = null;
        String telefone = null;
        List<AddressDTO> addresses = null;

        id = contact.getId();
        nome = contact.getNome();
        email = contact.getEmail();
        telefone = contact.getTelefone();
        addresses = toAddressDTOList( contact.getAddresses() );

        ContactDTO contactDTO = new ContactDTO( id, nome, email, telefone, addresses );

        return contactDTO;
    }

    @Override
    public Contact toEntity(ContactDTO contactDTO) {
        if ( contactDTO == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setNome( contactDTO.nome() );
        contact.setEmail( contactDTO.email() );
        contact.setTelefone( contactDTO.telefone() );
        contact.setAddresses( addressDTOListToAddressList( contactDTO.addresses() ) );

        return contact;
    }

    @Override
    public List<ContactDTO> toDTOList(List<Contact> contacts) {
        if ( contacts == null ) {
            return null;
        }

        List<ContactDTO> list = new ArrayList<ContactDTO>( contacts.size() );
        for ( Contact contact : contacts ) {
            list.add( toDTO( contact ) );
        }

        return list;
    }

    @Override
    public AddressDTO toAddressDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        Long id = null;
        String rua = null;
        String cidade = null;
        String estado = null;
        String cep = null;

        id = address.getId();
        rua = address.getRua();
        cidade = address.getCidade();
        estado = address.getEstado();
        cep = address.getCep();

        AddressDTO addressDTO = new AddressDTO( id, rua, cidade, estado, cep );

        return addressDTO;
    }

    @Override
    public Address toAddressEntity(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address address = new Address();

        address.setRua( addressDTO.rua() );
        address.setCidade( addressDTO.cidade() );
        address.setEstado( addressDTO.estado() );
        address.setCep( addressDTO.cep() );

        return address;
    }

    @Override
    public List<AddressDTO> toAddressDTOList(List<Address> addresses) {
        if ( addresses == null ) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>( addresses.size() );
        for ( Address address : addresses ) {
            list.add( toAddressDTO( address ) );
        }

        return list;
    }

    protected List<Address> addressDTOListToAddressList(List<AddressDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Address> list1 = new ArrayList<Address>( list.size() );
        for ( AddressDTO addressDTO : list ) {
            list1.add( toAddressEntity( addressDTO ) );
        }

        return list1;
    }
}
