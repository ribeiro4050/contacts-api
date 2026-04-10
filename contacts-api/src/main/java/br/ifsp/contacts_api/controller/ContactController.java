package br.ifsp.contacts_api.controller;

import br.ifsp.contacts_api.exception.ResourceNotFoundException;
import br.ifsp.contacts_api.model.Contact;
import br.ifsp.contacts_api.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id){
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));
    }

    // Ex-01
    // responde aos endpoints com /search,
    // tudo que vier depois do "?" na url, ele entende como parametro automaticamente,
    // no caso o name=valor ele joga no @RequestParam String name
    @GetMapping("/search")
    public List<Contact> getContactsByName(@RequestParam String name){
        return contactRepository.findByNomeContaining(name);
    }

    @PostMapping
    public Contact createContact(@RequestBody @Valid Contact contact){
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @RequestBody @Valid Contact updatedContact){
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));

        existingContact.setNome(updatedContact.getNome());
        existingContact.setTelefone(updatedContact.getTelefone());
        existingContact.setEmail(updatedContact.getEmail());

        return contactRepository.save(existingContact);
    }

    @PatchMapping("/{id}")
    public Contact updateField(@PathVariable Long id, @RequestBody @Valid Contact updatedField){
        Contact existingContact = contactRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));
        if(updatedField.getNome() != null) {
            existingContact.setNome(updatedField.getNome());
        }if(updatedField.getTelefone() != null){
            existingContact.setTelefone(updatedField.getTelefone());
        }if(updatedField.getEmail() != null)
            existingContact.setEmail(updatedField.getEmail());

        return contactRepository.save(existingContact);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id){
       contactRepository.deleteById(id);
    }


}
