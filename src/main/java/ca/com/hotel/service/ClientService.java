package ca.com.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.com.hotel.domain.ClientEntity;
import ca.com.hotel.dto.ClientDto;
import ca.com.hotel.repository.ClientRepository;
import ca.com.hotel.service.converter.ClientRequestConverter;
import ca.com.hotel.service.converter.ClientResponseConverter;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private ClientResponseConverter clientResponseConverter;
	
	@Autowired
	private ClientRequestConverter clientRequestConverter;

	public ClientDto save(ClientDto clientDto) {
		ClientEntity clientEntity = this.clientRepository.save(clientRequestConverter.apply(clientDto));
		return this.clientResponseConverter.apply(clientEntity);		
	}
	
	public ClientEntity saveEntity(ClientDto clientDto) {
		return this.clientRepository.save(clientRequestConverter.apply(clientDto));		
	}

	public List<ClientDto> getAll() {
		final List<ClientEntity> clientEntitys = (List<ClientEntity>) this.clientRepository.findAll();
		return clientResponseConverter.applyList(clientEntitys);
	}
	
	public ClientDto getClientById(Long id) {
		ClientEntity clientEntity = clientRepository.findById(id).get();
		return clientResponseConverter.apply(clientEntity);
	}
	
	public ClientEntity getClientEntityById(Long id) {
		ClientEntity clientEntity = clientRepository.findById(id).get();
		return clientEntity;
	}
	

}
