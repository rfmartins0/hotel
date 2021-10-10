package ca.com.hotel.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.com.hotel.domain.BookEntity;
import ca.com.hotel.domain.ClientEntity;
import ca.com.hotel.domain.RoomEntity;
import ca.com.hotel.dto.ClientBookDto;
import ca.com.hotel.dto.ClientDto;
import ca.com.hotel.dto.RoomDto;
import ca.com.hotel.exception.ValidateException;
import ca.com.hotel.repository.BookRepository;
import ca.com.hotel.service.converter.RoomResponseConverter;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ClientService clientService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private RoomResponseConverter roomResponseConverter;

	@Transactional
	public String save(ClientBookDto clientBookDto) {
		List<RoomEntity> availables = showAvailable(clientBookDto.getStartDate(), clientBookDto.getEndDate());
		if (!availables.isEmpty()) {
			ClientDto clientDto = new ClientDto();
			clientDto.setEmail(clientBookDto.getEmail());
			clientDto.setName(clientBookDto.getName());
			ClientEntity clientyEntity = clientService.saveEntity(clientDto);
			BookEntity bookEntity = new BookEntity();
			bookEntity.setStartDate(clientBookDto.getStartDate());
			bookEntity.setEndDate(clientBookDto.getEndDate());
			bookEntity.setClient(clientyEntity);
			bookEntity.setRoom(availables.iterator().next());
			String uuid = UUID.randomUUID().toString();
			bookEntity.setReservationNumber(uuid);
			bookRepository.save(bookEntity);
			return uuid;
		}
		throw new ValidateException("Room occupied");
	}

	@Transactional
	public boolean cancelReservation(final String numberOfReservation) {
		long number = bookRepository.deleteByReservationNumber(numberOfReservation);
		return number > 0;
	}

	@Transactional
	public boolean updateReservation(final LocalDate dateStart, final LocalDate dateEnd,
			final String numberOfReservation) {
		int number = 0;
		Optional<BookEntity> bookEntityOptional = bookRepository.findByReservationNumber(numberOfReservation);
		if (!bookEntityOptional.isPresent()) {
			throw new ValidateException("The reservation number is wrong");
		}
		List<RoomEntity> roomEntitys = showAvailableWithOut(dateStart, dateEnd, numberOfReservation);
		if (!roomEntitys.isEmpty()) {
			RoomEntity roomEntity = roomEntitys.iterator().next();
			number = bookRepository.setDatesById(dateStart, dateEnd, numberOfReservation, roomEntity);
		}
		return number > 0;
	}

	public List<RoomEntity> showAvailable(LocalDate startDate, LocalDate endDate) {
		List<BookEntity> bookEntitys = bookRepository.findFullRooms(startDate, endDate);
		List<RoomEntity> roomEntitys = new LinkedList<RoomEntity>(roomService.getEntityAll());
		List<RoomEntity> roomAvailablesEntitys = new LinkedList<RoomEntity>(roomService.getEntityAll());
		for (RoomEntity roomEntity : roomEntitys) {
			for (BookEntity bookEntity : bookEntitys) {
				if (roomEntity.getId() == bookEntity.getRoom().getId()) {
					roomAvailablesEntitys.remove(roomEntity);
				}
			}
		}
		return roomAvailablesEntitys;
	}

	public List<RoomEntity> showAvailableWithOut(LocalDate startDate, LocalDate endDate, String reserved) {
		List<BookEntity> bookFullEntitys = bookRepository.findFullRooms(startDate, endDate);
		List<BookEntity> bookEntitys = new ArrayList<>();
		for (BookEntity bookFullEntity : bookFullEntitys) {
			if (!bookFullEntity.getReservationNumber().equals(reserved)) {
				bookEntitys.add(bookFullEntity);
			}
		}
		List<RoomEntity> roomEntitys = new LinkedList<RoomEntity>(roomService.getEntityAll());
		List<RoomEntity> roomAvailablesEntitys = new LinkedList<RoomEntity>(roomService.getEntityAll());
		for (RoomEntity roomEntity : roomEntitys) {
			for (BookEntity bookEntity : bookEntitys) {
				if (roomEntity.getId() == bookEntity.getRoom().getId()) {
					roomAvailablesEntitys.remove(roomEntity);
				}
			}
		}
		return roomAvailablesEntitys;
	}

	public List<RoomDto> showAvailableDto(LocalDate startDate, LocalDate endDate) {
		return this.roomResponseConverter.applyList(this.showAvailable(startDate, endDate));
	}


}
