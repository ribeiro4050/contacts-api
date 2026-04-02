package br.ifsp.contacts_api.repository;

import br.ifsp.contacts_api.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    /*
       metodo personalizado para achar contatos por nome, por conta das convenções de nomeclaturas
       o spring permite criar metodos personalizados desde que sigam as regras da sua biblioteca
       no caso o findBy, ele da um SELECT * FROM contacts que é a tabela que ele tem acesso,
       o nome, que é o campo do banco de dados, ele da um WHERE nome
       e o Containing que é o operador de comparação, ele da um LIKE %valor%

       juntando tudo o comando que ele faz é
       SELECT * FROM contacts WHERE nome LIKE %valor%
    */
     public List<Contact> findByNomeContaining(String nome);
}
