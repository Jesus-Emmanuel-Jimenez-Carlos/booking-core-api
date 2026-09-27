package com.bookingcore.domain.exception;

/**
 * Thrown when a requested appointment overlaps with an existing reservation.
 */
public class BookingConflictException extends RuntimeException {

    public BookingConflictException(String message) {
        super(message);
    }
}
