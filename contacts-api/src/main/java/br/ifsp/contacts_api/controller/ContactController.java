package br.ifsp.contacts_api.controller;

import br.ifsp.contacts_api.dto.ContactDTO;
import br.ifsp.contacts_api.exception.ResourceNotFoundException;
import br.ifsp.contacts_api.model.Contact;
import br.ifsp.contacts_api.repository.ContactRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifsp.contacts_api.mapper.ContactMapper;

import java.util.List;

@RestController
@RequestMapping("api/contacts")
public class ContactController {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactController(ContactRepository contactRepository, ContactMapper contactMapper){
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    /**
     * Retorna uma lista paginada de todos os contatos.
     *
     * @param pageable informações de paginação
     * @return página de contatos
     */
    @Operation(summary = "Listar todos os contatos", description = "Retorna uma lista paginada de todos os contatos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contatos encontrados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })

    @GetMapping
    public ResponseEntity<Page<ContactDTO>> getAllContacts(Pageable pageable){
        Page<Contact> contactsPage = contactRepository.findAll(pageable);
        Page<ContactDTO> responseDTO = contactsPage.map(contactMapper::toDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Busca um contato pelo ID.
     *
     * @param id identificador do contato
     * @return contato encontrado
     * @throws ResourceNotFoundException se o contato não for encontrado
     */
    @Operation(summary = "Buscar contato por ID", description = "Retorna um contato específico com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })

    @GetMapping("/{id}/addresses")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id){

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));
        ContactDTO responseDTO = contactMapper.toDTO(contact);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Busca contatos pelo nome.
     *
     * @param name     nome ou parte do nome a ser pesquisado
     * @param pageable informações de paginação
     * @return lista paginada de contatos que correspondem ao critério de busca
     */
    @Operation(summary = "Buscar contatos por nome", description = "Retorna uma lista paginada de contatos cujo nome contém o termo pesquisado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })

    // Ex-01
    // responde aos endpoints com /search,
    // tudo que vier depois do "?" na url, ele entende como parametro automaticamente,
    // no caso o name=valor ele joga no @RequestParam String name
    @GetMapping("/search")
    public ResponseEntity<Page<ContactDTO>> getContactsByName(@RequestParam String name, Pageable pageable){
        Page<Contact> contactsPage = contactRepository.findByNomeContainingIgnoreCase(name, pageable);
        Page<ContactDTO> responseDTO = contactsPage.map(contactMapper::toDTO);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Cria um novo contato.
     *
     * @paramContactDTOdados do contato a ser criado
     * @return contato criado
     */
    @Operation(summary = "Criar novo contato", description = "Cria um novo contato com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContactDTO> createContact(@RequestBody @Valid ContactDTO contactDTO){
        Contact contact = contactMapper.toEntity(contactDTO);
        if (contact.getAddresses() != null){
            contact.getAddresses().forEach(address -> address.setContact(contact));
        }
        Contact savedContact = contactRepository.save(contact);
        ContactDTO contactResponseDTO = contactMapper.toDTO(savedContact);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactResponseDTO);
    }

    /**
     * Atualiza um contato existente.
     *
     * @param id  identificador do contato
     * @param updatedContactDTO novos dados do contato
     * @return contato atualizado
     * @throws ResourceNotFoundException se o contato não for encontrado
     */
    @Operation(summary = "Atualizar contato", description = "Atualiza todos os dados de um contato existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody @Valid ContactDTO updatedContactDTO){
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));

        existingContact.setNome(updatedContactDTO.nome());
        existingContact.setTelefone(updatedContactDTO.telefone());
        existingContact.setEmail(updatedContactDTO.email());

        Contact updatedContact = contactRepository.save(existingContact);
        ContactDTO responseDTO = contactMapper.toDTO(updatedContact);

        return ResponseEntity.ok(responseDTO);
    }


    /**
     * Atualiza parcialmente um contato existente.
     *
     * @param id  identificador do contato
     * @param contactDTO dados a serem atualizados
     * @return contato atualizado
     * @throws ResourceNotFoundException se o contato não for encontrado
     */
    @Operation(summary = "Atualizar contato parcialmente", description = "Atualiza apenas os campos especificados de um contato existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })

    @PatchMapping("/{id}")
    public ResponseEntity<ContactDTO> updateField(@PathVariable Long id, @RequestBody @Valid ContactDTO contactDTO){
        Contact existingContact = contactRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));
        if(contactDTO.nome() != null) {
            existingContact.setNome(contactDTO.nome());
        }if(contactDTO.telefone() != null){
            existingContact.setTelefone(contactDTO.telefone());
        }if(contactDTO.email() != null)
            existingContact.setEmail(contactDTO.email());
        Contact updatedContact = contactRepository.save(existingContact);
        ContactDTO responseDTO = contactMapper.toDTO(updatedContact);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Exclui um contato.
     *
     * @param id identificador do contato
     * @return resposta sem conteúdo
     * @throws ResourceNotFoundException se o contato não for encontrado
     */
    @Operation(summary = "Excluir contato", description = "Remove permanentemente um contato do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contato excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){
        if(!contactRepository.existsById(id)){
            throw new ResourceNotFoundException("Contato com ID " + id + "Não encontrado");
        }
        contactRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
