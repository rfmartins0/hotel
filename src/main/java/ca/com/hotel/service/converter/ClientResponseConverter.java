package ca.com.hotel.service.converter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import ca.com.hotel.domain.ClientEntity;
import ca.com.hotel.dto.ClientDto;

@Component
public class ClientResponseConverter implements Function<ClientEntity, ClientDto> {
	

	public ClientDto apply(ClientEntity clientEntity) {
		ClientDto clientDto = new ClientDto();
		clientDto.setName(clientEntity.getName());
		clientDto.setEmail(clientEntity.getEmail());
		return clientDto;
	}
	
	public List<ClientDto> applyList(List<ClientEntity> clientEntitys) {
		List<ClientDto> clientDtos = new LinkedList<>();
		for (ClientEntity clientEntity : clientEntitys) {
			clientDtos.add(apply(clientEntity));
		}
		return clientDtos;
	}


}

