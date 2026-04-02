package br.ifsp.contacts_api.controller;

import br.ifsp.contacts_api.model.Adress;
import br.ifsp.contacts_api.model.Contact;
import br.ifsp.contacts_api.repository.AdressRepository;
import br.ifsp.contacts_api.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class AdressController {

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/{id}/adresses")
    public Adress getAdressById(@PathVariable Long id){
        return adressRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Contato não encontrado"));
    }

    @PostMapping("/{id}/adresses")
    public Adress createAdress(@PathVariable Long id, @RequestBody Adress adress){
        if(!contactRepository.existsById(adress.getContactId())){
             throw new RuntimeException("Contato não encontrado");
        }

        return adressRepository.save(adress);
    }
}
