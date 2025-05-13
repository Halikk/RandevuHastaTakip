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

import java.util.List;

@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final UserService userService;
    private final AppointmentService appointmentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User currentUser = getCurrentUser();
        List<Appointment> appointments = appointmentService.getDoctorAppointments(currentUser);
        
        model.addAttribute("user", currentUser);
        model.addAttribute("appointments", appointments);
        return "doctor/dashboard";
    }

    @GetMapping("/appointments/{id}")
    public String viewAppointment(@PathVariable Long id, Model model) {
        User currentUser = getCurrentUser();
        
        Appointment appointment = appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Randevu bulunamadı"));
        
        // Security check - doctors can only view their own appointments
        if (!appointment.getDoctor().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("Erişim reddedildi");
        }
        
        model.addAttribute("appointment", appointment);
        return "doctor/view-appointment";
    }

    @PostMapping("/appointments/{id}/update-status")
    public String updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam Appointment.Status status,
            RedirectAttributes redirectAttributes) {
        
        try {
            User currentUser = getCurrentUser();
            
            Appointment appointment = appointmentService.getAppointmentById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Randevu bulunamadı"));
            
            // Security check
            if (!appointment.getDoctor().getId().equals(currentUser.getId())) {
                throw new IllegalArgumentException("Erişim reddedildi");
            }
            
            appointmentService.updateAppointmentStatus(id, status);
            
            redirectAttributes.addFlashAttribute("success", "Randevu durumu güncellendi");
            return "redirect:/doctor/appointments/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/doctor/dashboard";
        }
    }

    @PostMapping("/appointments/{id}/add-notes")
    public String addNotes(
            @PathVariable Long id,
            @RequestParam String notes,
            RedirectAttributes redirectAttributes) {
        
        try {
            User currentUser = getCurrentUser();
            
            Appointment appointment = appointmentService.getAppointmentById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Randevu bulunamadı"));
            
            // Security check
            if (!appointment.getDoctor().getId().equals(currentUser.getId())) {
                throw new IllegalArgumentException("Erişim reddedildi");
            }
            
            appointmentService.addDoctorNotes(id, notes);
            
            redirectAttributes.addFlashAttribute("success", "Notlar başarıyla eklendi");
            return "redirect:/doctor/appointments/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/doctor/dashboard";
        }
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByUsername(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Kullanıcı bulunamadı"));
    }
} 