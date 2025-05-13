# Randevu ve Hasta Takip Sistemi

Bu proje, doktorlar ve hastalar arasındaki randevu sürecini yönetmek için tasarlanmış bir Spring Boot uygulamasıdır.

## Özellikler

### Kullanıcı Rolleri
- **Hasta**: Kayıt olur, giriş yapar, randevu alır, geçmiş randevularını görür.
- **Doktor**: Giriş yapar, kendisine gelen randevuları görür, onaylar veya reddeder, not ekler.

### Randevu Sistemi
- Randevu tarihi ve saati seçimi
- Aynı saatte çakışan randevuların engellenmesi
- Randevunun durumları: Beklemede, Onaylandı, Reddedildi

### Hasta Takibi
- Doktorlar randevulara özel not ekleyebilir
- Hasta geçmiş randevularını ve doktor notlarını görebilir

## Teknolojiler

- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Frontend**: HTML, CSS, Bootstrap, Thymeleaf
- **Veritabanı**: H2 Database (geliştirme için)

## Kurulum

1. Projeyi klonlayın:
   ```bash
   git clone https://github.com/your-username/randevu-hasta-takip.git
   cd randevu-hasta-takip
   ```

2. Uygulamayı derleyin ve çalıştırın:
   ```bash
   ./mvnw spring-boot:run
   ```
   ya da Windows için:
   ```bash
   mvnw.cmd spring-boot:run
   ```

3. Tarayıcınızdan uygulamaya erişin:
   ```
   http://localhost:8080
   ```

## Kullanım

1. Önce bir hesap oluşturun (hasta veya doktor rolü seçilebilir)
2. Giriş yapın
3. Roller bazında işlemler:
   - **Hasta**: Randevu al, randevu listesini görüntüle, randevu detaylarını gör
   - **Doktor**: Randevuları listele, randevuları onayla/reddet, hastaya not ekle

## H2 Veritabanına Erişim

Geliştirme aşamasında H2 veritabanına şu adres üzerinden erişebilirsiniz:
```
http://localhost:8080/h2-console
```

Giriş bilgileri:
- JDBC URL: `jdbc:h2:file:./data/randevudb`
- Kullanıcı adı: `sa`
- Şifre: `password` 