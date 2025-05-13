package com.randevu.repository;

import com.randevu.model.Appointment;
import com.randevu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByPatientOrderByAppointmentDateTimeDesc(User patient);
    
    List<Appointment> findByDoctorOrderByAppointmentDateTimeDesc(User doctor);
    
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDateTime BETWEEN :start AND :end")
    List<Appointment> findByDoctorAndTimeRange(
            @Param("doctorId") Long doctorId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
    
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDateTime = :dateTime")
    boolean existsByDoctorAndDateTime(@Param("doctorId") Long doctorId, @Param("dateTime") LocalDateTime dateTime);
} 