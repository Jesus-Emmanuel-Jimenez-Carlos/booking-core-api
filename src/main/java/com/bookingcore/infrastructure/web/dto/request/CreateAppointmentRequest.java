package com.bookingcore.infrastructure.web.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Provider ID is required")
    private Long providerId;

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "Start time is required")
    @Future(message = "Appointment start time must be in the future")
    private LocalDateTime startTime;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
}
