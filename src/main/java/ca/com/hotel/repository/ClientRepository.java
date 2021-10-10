package ca.com.hotel.repository;

import org.springframework.data.repository.CrudRepository;

import ca.com.hotel.domain.ClientEntity;

public interface ClientRepository extends CrudRepository<ClientEntity, Long>{

}


