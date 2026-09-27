package com.bookingcore.infrastructure.web.controller;

import com.bookingcore.domain.model.Appointment;
import com.bookingcore.domain.model.TimeSlot;
import com.bookingcore.infrastructure.web.dto.request.CreateAppointmentRequest;
import com.bookingcore.infrastructure.web.dto.response.AppointmentResponse;
import com.bookingcore.service.impl.AppointmentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Appointment & Availability API", description = "Endpoints for scheduling and provider slot calculation")
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    @GetMapping("/availability")
    @Operation(summary = "Get available time slots for a provider on a specific date")
    public ResponseEntity> getAvailability(
            @RequestParam Long providerId,
            @RequestParam Long serviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        List slots = appointmentService.calculateAvailableSlots(providerId, serviceId, date);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/appointments")
    @Operation(summary = "Schedule a new appointment")
    public ResponseEntity createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        Appointment appointment = appointmentService.scheduleAppointment(
                request.getCustomerId(),
                request.getProviderId(),
                request.getServiceId(),
                request.getStartTime(),
                request.getNotes()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentResponse.fromEntity(appointment));
    }
}
