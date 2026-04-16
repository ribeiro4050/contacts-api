package br.ifsp.contacts_api.controller;

import br.ifsp.contacts_api.dto.ContactDTO;
import br.ifsp.contacts_api.exception.ResourceNotFoundException;
import br.ifsp.contacts_api.model.Contact;
import br.ifsp.contacts_api.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import br.ifsp.contacts_api.mapper.ContactMapper;

import java.util.List;

@RestController
@RequestMapping("api/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @GetMapping
    public Page<ContactDTO> getAllContacts(Pageable pageable){
        Page<Contact> contactsPage = contactRepository.findAll(pageable);
        return contactsPage.map(contactMapper::toDTO);
    }

    @GetMapping("/{id}")
    public ContactDTO getContactById(@PathVariable Long id){

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));
        return contactMapper.toDTO(contact);
    }

    // Ex-01
    // responde aos endpoints com /search,
    // tudo que vier depois do "?" na url, ele entende como parametro automaticamente,
    // no caso o name=valor ele joga no @RequestParam String name
    @GetMapping("/search")
    public Page<ContactDTO> getContactsByName(@RequestParam String name, Pageable pageable){
        Page<Contact> contactsPage = contactRepository.findByNomeContainingIgnoreCase(name, pageable);
        return contactsPage.map(contactMapper::toDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO createContact(@RequestBody @Valid ContactDTO contactDTO){
        Contact contact = contactMapper.toEntity(contactDTO);
        if (contact.getAddresses() != null){
            contact.getAddresses().forEach(address -> address.setContact(contact));
        }
        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toDTO(savedContact);
    }

    @PutMapping("/{id}")
    public ContactDTO updateContact(@PathVariable Long id, @RequestBody @Valid ContactDTO updatedContactDTO){
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));

        existingContact.setNome(updatedContactDTO.nome());
        existingContact.setTelefone(updatedContactDTO.telefone());
        existingContact.setEmail(updatedContactDTO.email());

        Contact updatedContact = contactRepository.save(existingContact);
        return contactMapper.toDTO(updatedContact);
    }

    @PatchMapping("/{id}")
    public ContactDTO updateField(@PathVariable Long id, @RequestBody @Valid ContactDTO contactDTO){
        Contact existingContact = contactRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));
        if(contactDTO.nome() != null) {
            existingContact.setNome(contactDTO.nome());
        }if(contactDTO.telefone() != null){
            existingContact.setTelefone(contactDTO.telefone());
        }if(contactDTO.email() != null)
            existingContact.setEmail(contactDTO.email());
        Contact updatedContact = contactRepository.save(existingContact);
        return contactMapper.toDTO(updatedContact);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable Long id){
        contactRepository.deleteById(id);
    }


}
