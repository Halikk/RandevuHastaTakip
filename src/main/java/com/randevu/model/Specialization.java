package com.randevu.model;

/**
 * Enum representing medical specializations for doctors
 */
public enum Specialization {
    CARDIOLOGY("Kardiyoloji"),
    DERMATOLOGY("Dermatoloji"),
    NEUROLOGY("Nöroloji"),
    ORTHOPEDICS("Ortopedi"),
    OPHTHALMOLOGY("Göz Hastalıkları"),
    GYNECOLOGY("Kadın Hastalıkları ve Doğum"),
    UROLOGY("Üroloji"),
    PSYCHIATRY("Psikiyatri"),
    PEDIATRICS("Çocuk Hastalıkları"),
    INTERNAL_MEDICINE("İç Hastalıkları"),
    GENERAL_SURGERY("Genel Cerrahi");
    
    private final String displayName;
    
    Specialization(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
} 