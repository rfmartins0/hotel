package ca.com.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.com.hotel.dto.RoomDto;
import ca.com.hotel.service.BookService;
import ca.com.hotel.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;


@RestController
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private BookService bookService;
	
    @Operation(summary = "Show Rooms")
	@GetMapping(path = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomDto>> getAll() {
		return ResponseEntity.ok(roomService.getAll());
	}
    
    @Operation(summary = "Show Free Rooms")
   	@GetMapping(path = "/rooms/free", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<List<RoomDto>> show(@RequestParam final String startDate, final String endDate) {
   		LocalDate dateStartDate = LocalDate.parse(startDate);
   		LocalDate dateEndDate = LocalDate.parse(endDate);
   		List<RoomDto> roomDtoList = bookService.showAvailableDto(dateStartDate, dateEndDate);
   		return ResponseEntity.ok(roomDtoList);
   	}


}
