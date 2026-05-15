package br.ifsp.contacts_api.ContactController;

import br.ifsp.contacts_api.controller.ContactController;
import br.ifsp.contacts_api.dto.ContactDTO;
import br.ifsp.contacts_api.exception.ResourceNotFoundException;
import br.ifsp.contacts_api.mapper.ContactMapper;
import br.ifsp.contacts_api.model.Address;
import br.ifsp.contacts_api.model.Contact;
import br.ifsp.contacts_api.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactControllerUnitTest {

    // criando atores para testes simplificados
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    // injetando atores no controller
    @InjectMocks
    private ContactController contactController;

    // testando o método getContactById
    @Test
    void testGetContactById_ReturnsContact(){
        // given:
        Long id = 1L;
        Contact contact = new Contact ("Barolo Salgueiro", "barolo@email.com", "127677777");
        Address address = new Address();
        address.setRua("Rua Um");
        address.setCidade("São Paulo");
        address.setEstado("SP");
        address.setCep("88888-999");
        List<Address> addresses = List.of(address);
        contact.setAddresses(addresses);
        ReflectionTestUtils.setField(contact, "id", id);

        ContactDTO dto = new ContactDTO(contact);

        //when:
        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));
        when(contactMapper.toDTO(contact)).thenReturn(dto);

        ResponseEntity<ContactDTO> response = contactController.getContactById(id);

        //then:
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().id());

    }
    // Testa a excessão caso o método falhar
    @Test
    void testGetContactById_NotFound(){
        //given
        Long id = 999L;

        //when
        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThrows(ResourceNotFoundException.class, () -> contactController.getContactById(id));
    }


}
