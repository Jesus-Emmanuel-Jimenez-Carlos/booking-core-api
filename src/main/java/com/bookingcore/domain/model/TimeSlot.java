package com.bookingcore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO representing an available or reserved calculated time window.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean available;
}
