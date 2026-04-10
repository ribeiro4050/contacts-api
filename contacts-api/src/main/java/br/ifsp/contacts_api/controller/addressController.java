package br.ifsp.contacts_api.controller;

import br.ifsp.contacts_api.exception.ResourceNotFoundException;
import br.ifsp.contacts_api.model.Address;
import br.ifsp.contacts_api.model.Contact;
import br.ifsp.contacts_api.repository.addressRepository;
import br.ifsp.contacts_api.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class addressController {

    @Autowired
    private addressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/contacts/{contactId}")
    public List<Address> getAddressesByContact(@PathVariable Long contactId){
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(()-> new ResourceNotFoundException("Contato com ID " + contactId + " não encontrado"));;
        return contact.getAddresses();
    }

    @PostMapping("/contacts/{contactId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Address createAddress(@PathVariable Long contactId, @RequestBody @Valid Address address){
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado"));

        address.setContact(contact);
        return addressRepository.save(address);
    }
}
