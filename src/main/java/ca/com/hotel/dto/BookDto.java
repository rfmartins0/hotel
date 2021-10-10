package ca.com.hotel.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookDto {

	private Long roomId;

	private Long clientId;

	@Schema(description = "Start Date", 
            example = "2021-09-20", required = true)
	private LocalDate startDate;

	@Schema(description = "End Date", 
            example = "2021-09-22", required = true)
	private LocalDate endDate;

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}
