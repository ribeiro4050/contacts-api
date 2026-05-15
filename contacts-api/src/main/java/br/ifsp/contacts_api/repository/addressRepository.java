package br.ifsp.contacts_api.repository;

import br.ifsp.contacts_api.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface addressRepository extends JpaRepository<Address, Long> {
    Page<Address> findByContactId(Long contactId, Pageable pageable);
}
