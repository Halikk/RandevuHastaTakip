package com.randevu.controller;

import com.randevu.model.Appointment;
import com.randevu.model.User;
import com.randevu.service.AppointmentService;
import com.randevu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final UserService userService;
    private final AppointmentService appointmentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User currentUser = getCurrentUser();
        List<Appointment> appointments = appointmentService.getPatientAppointments(currentUser);
        
        model.addAttribute("user", currentUser);
        model.addAttribute("appointments", appointments);
        return "patient/dashboard";
    }

    @GetMapping("/book-appointment")
    public String bookAppointmentForm(Model model) {
        List<User> doctors = userService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        model.addAttribute("appointment", new Appointment());
        return "patient/book-appointment";
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(
            @RequestParam Long doctorId,
            @RequestParam LocalDate appointmentDate,
            @RequestParam LocalTime appointmentTime,
            RedirectAttributes redirectAttributes) {
        
        try {
            User patient = getCurrentUser();
            User doctor = userService.getUserById(doctorId)
                    .orElseThrow(() -> new IllegalArgumentException("Doktor bulunamadı"));
            
            // Create appointment
            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setAppointmentDateTime(LocalDateTime.of(appointmentDate, appointmentTime));
            
            appointmentService.createAppointment(appointment);
            
            redirectAttributes.addFlashAttribute("success", "Randevu başarıyla oluşturuldu");
            return "redirect:/patient/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patient/book-appointment";
        }
    }

    @GetMapping("/appointments/{id}")
    public String viewAppointment(@PathVariable Long id, Model model) {
        User currentUser = getCurrentUser();
        
        Appointment appointment = appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Randevu bulunamadı"));
        
        // Security check - patients can only view their own appointments
        if (!appointment.getPatient().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("Erişim reddedildi");
        }
        
        model.addAttribute("appointment", appointment);
        return "patient/view-appointment";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByUsername(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Kullanıcı bulunamadı"));
    }
} 