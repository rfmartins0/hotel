package ca.com.hotel.service.converter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import ca.com.hotel.domain.RoomEntity;
import ca.com.hotel.dto.RoomDto;

@Component
public class RoomResponseConverter implements Function<RoomEntity, RoomDto> {

	@Override
	public RoomDto apply(RoomEntity roomEntity) {
		RoomDto roomDto = new RoomDto();
		roomDto.setRoom(roomEntity.getRoom());
		return roomDto;
	}

	public List<RoomDto> applyList(List<RoomEntity> roomEntitys) {
		List<RoomDto> roomDtos = new LinkedList<>();
		for (RoomEntity roomEntity : roomEntitys) {
			roomDtos.add(apply(roomEntity));
		}
		return roomDtos;
	}

}
