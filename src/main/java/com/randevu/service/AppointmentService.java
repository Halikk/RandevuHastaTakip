package com.randevu.service;

import com.randevu.model.Appointment;
import com.randevu.model.User;
import com.randevu.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    
    @Value("${app.appointment.min-hours-in-advance}")
    private int minHoursInAdvance;
    
    @Value("${app.appointment.max-days-in-advance}")
    private int maxDaysInAdvance;

    public List<Appointment> getPatientAppointments(User patient) {
        return appointmentRepository.findByPatientOrderByAppointmentDateTimeDesc(patient);
    }

    public List<Appointment> getDoctorAppointments(User doctor) {
        return appointmentRepository.findByDoctorOrderByAppointmentDateTimeDesc(doctor);
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public void createAppointment(Appointment appointment) {
        // Validation
        validateAppointmentDateTime(appointment.getAppointmentDateTime());
        validateDoctorAvailability(appointment.getDoctor().getId(), appointment.getAppointmentDateTime());
        
        // Set initial status
        appointment.setStatus(Appointment.Status.PENDING);

        appointmentRepository.save(appointment);
    }
    
    public void updateAppointmentStatus(Long id, Appointment.Status status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Randevu bulunamadı"));
        
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }
    
    public void addDoctorNotes(Long id, String notes) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Randevu bulunamadı"));
        
        appointment.setDoctorNotes(notes);
        appointmentRepository.save(appointment);
    }
    
    private void validateAppointmentDateTime(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minAllowedTime = now.plusHours(minHoursInAdvance);
        LocalDateTime maxAllowedTime = now.plusDays(maxDaysInAdvance);
        
        if (dateTime.isBefore(minAllowedTime)) {
            throw new IllegalArgumentException("Randevu en az " + minHoursInAdvance + " saat sonrası için alınmalıdır");
        }
        
        if (dateTime.isAfter(maxAllowedTime)) {
            throw new IllegalArgumentException("Randevu en fazla " + maxDaysInAdvance + " gün sonrası için alınabilir");
        }
    }
    
    private void validateDoctorAvailability(Long doctorId, LocalDateTime dateTime) {
        boolean isBooked = appointmentRepository.existsByDoctorAndDateTime(doctorId, dateTime);
        if (isBooked) {
            throw new IllegalArgumentException("Doktorun bu saatte başka bir randevusu bulunmaktadır");
        }
    }
} 