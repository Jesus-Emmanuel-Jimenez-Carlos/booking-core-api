package com.bookingcore.service.impl;

import com.bookingcore.domain.exception.BookingConflictException;
import com.bookingcore.domain.model.Appointment;
import com.bookingcore.domain.model.TimeSlot;
import com.bookingcore.infrastructure.persistence.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl {

    private final AppointmentRepository appointmentRepository;

    private static final int DEFAULT_SERVICE_DURATION_MINUTES = 30;
    private static final LocalTime BUSINESS_OPENING_TIME = LocalTime.of(9, 0);
    private static final LocalTime BUSINESS_CLOSING_TIME = LocalTime.of(17, 0);

    /**
     * Schedules a new appointment after verifying boundary conflicts.
     */
    @Transactional
    public Appointment scheduleAppointment(Long customerId, Long providerId, Long serviceId, LocalDateTime startTime, String notes) {
        LocalDateTime endTime = startTime.plusMinutes(DEFAULT_SERVICE_DURATION_MINUTES);

        boolean hasConflict = appointmentRepository.existsOverlappingAppointment(providerId, startTime, endTime);
        if (hasConflict) {
            throw new BookingConflictException("The selected provider is not available during the requested time slot.");
        }

        Appointment appointment = Appointment.builder()
                .customerId(customerId)
                .providerId(providerId)
                .serviceId(serviceId)
                .startTime(startTime)
                .endTime(endTime)
                .notes(notes)
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .build();

        return appointmentRepository.save(appointment);
    }

    /**
     * Dynamically calculates available time slots for a provider on a specific date.
     */
    @Transactional(readOnly = true)
    public List calculateAvailableSlots(Long providerId, Long serviceId, LocalDate date) {
        LocalDateTime dayStart = date.atTime(BUSINESS_OPENING_TIME);
        LocalDateTime dayEnd = date.atTime(BUSINESS_CLOSING_TIME);

        List existingAppointments = appointmentRepository.findProviderAppointmentsForDay(providerId, dayStart, dayEnd);
        List slots = new ArrayList<>();

        LocalDateTime currentSlotStart = dayStart;
        while (currentSlotStart.plusMinutes(DEFAULT_SERVICE_DURATION_MINUTES).isBefore(dayEnd) || 
               currentSlotStart.plusMinutes(DEFAULT_SERVICE_DURATION_MINUTES).isEqual(dayEnd)) {
            
            LocalDateTime currentSlotEnd = currentSlotStart.plusMinutes(DEFAULT_SERVICE_DURATION_MINUTES);
            
            final LocalDateTime slotStart = currentSlotStart;
            boolean isBooked = existingAppointments.stream().anyMatch(appt -> 
                slotStart.isBefore(appt.getEndTime()) && currentSlotEnd.isAfter(appt.getStartTime())
            );

            slots.add(TimeSlot.builder()
                    .startTime(currentSlotStart)
                    .endTime(currentSlotEnd)
                    .available(!isBooked)
                    .build());

            currentSlotStart = currentSlotEnd;
        }

        return slots;
    }
}
