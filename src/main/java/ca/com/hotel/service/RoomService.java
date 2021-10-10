package ca.com.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.com.hotel.domain.RoomEntity;
import ca.com.hotel.dto.RoomDto;
import ca.com.hotel.repository.RoomRepository;
import ca.com.hotel.service.converter.RoomResponseConverter;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private RoomResponseConverter roomResponseConverter;

	public List<RoomDto> getAll() {
		final List<RoomEntity> roomEntitys = (List<RoomEntity>) this.roomRepository.findAll();
		return roomResponseConverter.applyList(roomEntitys);
	}
	
	public List<RoomEntity> getEntityAll() {
		return (List<RoomEntity>) this.roomRepository.findAll();
	}
	
	public RoomDto getRoomById(Long id) {
		RoomEntity roomEntity = roomRepository.findById(id).get();
		return roomResponseConverter.apply(roomEntity);
	}
	
	public RoomEntity getRoomEntityById(Long id) {
		RoomEntity roomEntity = roomRepository.findById(id).get();
		return roomEntity;
	}
	
	public long getNumberOfRooms() {
		return roomRepository.count();
	}
	

}
