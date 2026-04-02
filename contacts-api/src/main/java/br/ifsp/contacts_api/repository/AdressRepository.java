package br.ifsp.contacts_api.repository;

import br.ifsp.contacts_api.model.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Adress, Long> {
}
