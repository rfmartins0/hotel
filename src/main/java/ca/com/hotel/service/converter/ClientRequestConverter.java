package ca.com.hotel.service.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import ca.com.hotel.domain.ClientEntity;
import ca.com.hotel.dto.ClientDto;

@Component
public class ClientRequestConverter implements Function<ClientDto, ClientEntity> {
	
	@Override
	public ClientEntity apply(ClientDto clientDto) {
		ClientEntity clientEntity = new ClientEntity();
		clientEntity.setName(clientDto.getName());
		clientEntity.setEmail(clientDto.getEmail());
		return clientEntity;
	}

}

