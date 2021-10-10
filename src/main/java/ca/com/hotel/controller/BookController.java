package ca.com.hotel.controller;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.com.hotel.dto.BookSerialDto;
import ca.com.hotel.dto.ClientBookDto;
import ca.com.hotel.exception.ValidateException;
import ca.com.hotel.service.BookService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

    @Operation(summary = "Add New Client/Booking")
	@PostMapping(path = "/books", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookSerialDto> saveClientBook(@RequestBody final ClientBookDto clientBookDto) {
		this.validate(clientBookDto.getStartDate(), clientBookDto.getEndDate());
		BookSerialDto bookSerialDto = new BookSerialDto(bookService.save(clientBookDto));
		return new ResponseEntity<BookSerialDto>(bookSerialDto, HttpStatus.ACCEPTED);
	}

   
    @Operation(summary = "Delete Booking")
	@DeleteMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> cancelReservation(final String reservationNumber) {
		return new ResponseEntity<Boolean>(bookService.cancelReservation(reservationNumber), HttpStatus.OK);
	}

    @Operation(summary = "Update Reservation")
	@PatchMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateReservation(final String dateStart, final String dateEnd,
			final String numberReservation) {
		LocalDate dateStartDate = LocalDate.parse(dateStart);
		LocalDate dateEndDate = LocalDate.parse(dateEnd);
		this.validate(dateStartDate, dateEndDate);
		Boolean update = bookService.updateReservation(dateStartDate, dateEndDate, numberReservation);
		return new ResponseEntity<String>(update.toString(), HttpStatus.OK);
	}

	private void validate(LocalDate dateStart, LocalDate dateEnd) {
		boolean resultDate = this.checkDate(dateStart, dateEnd);
		if (!resultDate) {
			throw new ValidateException("Invalid date");
		}
		boolean resultMax3 = this.checkMax3Days(dateStart, dateEnd);
		if (!resultMax3) {
			throw new ValidateException("Only 3 days are allowed");
		}
		boolean resultMax30 = this.checkMax30DaysBefore(dateStart);
		if (!resultMax30) {
			throw new ValidateException("More than 30 days");
		}
		boolean resultValidate = this.checkValidDate(dateStart);
		if (!resultValidate) {
			throw new ValidateException("The date must be greater than the current date");
		}
	}

	private boolean checkValidDate(LocalDate dateStart) {
		if (!dateStart.isAfter(LocalDate.now())) {
			return false;
		}
		return true;
	}


	private boolean checkDate(LocalDate dateStart, LocalDate dateEnd) {
		if (dateStart.isAfter(dateEnd)) {
			return false;
		}
		return true;
	}

	private boolean checkMax3Days(LocalDate dateStart, LocalDate dateEnd) {
		Period period = Period.between(dateStart, dateEnd);
		if (period.getDays() > 3 || period.getMonths() > 0 || period.getYears() > 0) {
			return false;
		}
		return true;
	}

	private boolean checkMax30DaysBefore(LocalDate dateStart) {
		Period period = Period.between(LocalDate.now(), dateStart);
		if (period.getDays() > 30 || period.getMonths() != 0 || period.getYears() != 0) {
			return false;
		}
		return true;
	}

}
