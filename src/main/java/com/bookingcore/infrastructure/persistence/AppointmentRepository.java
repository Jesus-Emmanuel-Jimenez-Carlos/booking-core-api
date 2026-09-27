package com.bookingcore.infrastructure.persistence;

import com.bookingcore.domain.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository {

    /**
     * Checks for intersecting appointments for a provider within a specific time window.
     * Overlap condition: (StartA < EndB) AND (EndA > StartB)
     */
    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
           "WHERE a.providerId = :providerId " +
           "AND a.status = 'SCHEDULED' " +
           "AND a.startTime < :endTime " +
           "AND a.endTime > :startTime")
    boolean existsOverlappingAppointment(
            @Param("providerId") Long providerId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    /**
     * Fetches all scheduled appointments for a provider on a given date.
     */
    @Query("SELECT a FROM Appointment a " +
           "WHERE a.providerId = :providerId " +
           "AND a.status = 'SCHEDULED' " +
           "AND a.startTime >= :dayStart " +
           "AND a.endTime <= :dayEnd " +
           "ORDER BY a.startTime ASC")
    List findProviderAppointmentsForDay(
            @Param("providerId") Long providerId,
            @Param("dayStart") LocalDateTime dayStart,
            @Param("dayEnd") LocalDateTime dayEnd
    );
}
