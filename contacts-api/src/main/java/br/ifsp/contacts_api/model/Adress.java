package br.ifsp.contacts_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
    // chave estrangeira referenciando um contato
    private Long contactId;

    public Adress(){}

    public Adress(String rua, String cidade, String estado, String cep, Long contactId){
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.contactId = contactId;
    }

    public Long getId(){
        return id;
    }

    public String getRua(){
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade(){
        return cidade;
    }

    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String Estado){
        this.estado = estado;
    }

    public String getCep(){
        return cep;
    }

    public void setCep(String cep){
        this.cep = cep;
    }

    public Long getContactId(){
        return contactId;
    }

    public void setContactId(Long contactId){
        this.contactId = contactId;
    }
}
