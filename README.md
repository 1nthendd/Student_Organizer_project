StudentOrganizer

Nesne Tabanlı Programlama (OOP) Projesi – Java

📌 Proje Tanımı

StudentOrganizer, üniversite öğrencilerinin ders programı, sınavlar, notlar ve görevlerini tek bir uygulama üzerinden yönetmelerini sağlayan bir Java konsol projesidir.
Proje, nesne tabanlı programlama prensiplerine uygun şekilde tasarlanmıştır.

🎯 Amaç

Bu projenin amacı:

OOP prensiplerini uygulamak

Sınıf yapıları ve paket organizasyonunu öğrenmek

Konsol tabanlı bir öğrenci yönetim sistemi geliştirmek

Java ile menü yapısı, nesneler arası iletişim ve veri işleme pratiği yapmak

🧱 Kullanılan OOP Kavramları

Bu projede aşağıdaki nesne tabanlı programlama kavramları kullanılmıştır:

Kavram	Açıklama
Sınıf (Class)	Öğrenci, Ders, Not, Sınav gibi yapılara ait modeller oluşturuldu
Nesne (Object)	Program boyunca sınıflardan nesneler türetildi
Kapsülleme (Encapsulation)	private değişkenler ve getter/setter metotları kullanıldı
Çok Biçimlilik (Polymorphism)	Menü class’ları üzerinden farklı işlemler için metotlar yapısı
Miras (Inheritance) (istenirse)	Geliştirmeye açık olacak şekilde temel sınıflar
Paket Yapısı	main/ altında menü ve işlem dosyaları düzenlendi
📁 Proje Yapısı
StudentOrganizer
│
└── src
    └── main
        ├── StudentOrganizerApp.java     → Uygulamanın ana çalışma dosyası
        ├── MainMenu.java                → Ana menü
        ├── DersProgramiMenu.java        → Ders programı işlemleri
        ├── NotlarMenu.java              → Not yönetimi
        ├── SinavlarMenu.java            → Sınav ve tarih işlemleri
        ├── TakvimMenu.java              → Takvim ve hatırlatıcı fonksiyonları

📌 Özellikler

📚 Ders Programı Yönetimi

Ders ekleme / listeleme / silme

✏️ Not Yönetimi

Derslere ait notlar ekleme

Ortalamayı görüntüleme

🧪 Sınav Takibi

Sınav ekleme, sınav paunı hesaplama


📅 Takvim Menüsü

Günlük görev/gün planı ekleme

🚀 Çalıştırma

Proje dosyalarını IDE’ye (Eclipse / IntelliJ / NetBeans) ekleyin

StudentOrganizerApp.java dosyasını çalıştırın

Konsoldan menüyü takip ederek kullanın


👤 Geliştirici

Barakhoev Makhmud 
Ramin Erkenov
Bolu Abant İzzet Baysal Üniversitesi – Bilgisayar Mühendisliği
Yönetim, geliştirme ve raporlama
