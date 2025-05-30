# 🐾 Zoonary – Aplikasi Katalog Hewan

**Zoonary** adalah aplikasi Android interaktif yang memungkinkan pengguna untuk menjelajahi, menambahkan, dan mengelola katalog berbagai jenis hewan.  
Dibangun menggunakan **Android Studio** dan menerapkan prinsip **Clean Architecture** untuk struktur kode yang rapi dan terorganisir.

<p align="center">
  <img src="https://github.com/user-attachments/assets/ed1f8d92-ceba-4639-be89-c0b83d8e29cf" alt="Logo Zoonary" width="200"/>
</p>

---

## ✨ Fitur Unggulan

- ✅ **Autentikasi** – Login & Register pengguna  
- 📍 **Deteksi Lokasi** – Lokasi pengguna ditampilkan di halaman Home  
- 🔍 **Pencarian** – Cari hewan berdasarkan nama atau spesies  
- 🐶 **CRUD Hewan** – Tambah, Edit, dan Hapus data hewan  
- ⭐ **Favorit** – Tambahkan hewan ke daftar favorit dengan satu klik  
- ❤️ **Halaman Favorit** – Menampilkan hewan yang disukai  
- ⚙️ **Pengaturan** – Ubah preferensi dan Logout  
- 🔔 **Notifikasi** – Muncul saat menambahkan hewan ke favorit  
- 📡 **API + Penyimpanan Lokal** – Retrofit + SQLite + SharedPreferences  
- 🚀 **Performa Cepat** – Menggunakan Handler & background threading  

---

## 🗂️ Struktur Folder (Clean Architecture)
com.example.latihan_praktikum_7/  
├── data/  
│   ├── database/  
│   │   ├── DatabaseContract.java  
│   │   └── DatabaseHelper.java  
│   ├── entity/  
│   │   └── Animal.java  
│   ├── network/  
│   │   ├── ApiService.java  
│   │   └── RetrofitClient.java  
│   └── repository/  
│       └── (kosong / tambahkan jika ada)  
├── domain/  
│   └── (kosong / tambahkan jika perlu)  
├── presentation/  
│   ├── adapter/  
│   │   └── AnimalAdapter.java  
│   ├── ui/  
│   │   ├── favorit/  
│   │   │   └── FavoritFragment.java  
│   │   ├── home/  
│   │   │   ├── HomeFragment.java  
│   │   │   └── MainActivity.java  
│   │   ├── konten/  
│   │   │   └── KontenFragment.java  
│   │   ├── login/  
│   │   │   ├── LoginActivity.java  
│   │   │   ├── LoginFragment.java  
│   │   │   └── RegisterFragment.java  
│   │   └── setting/  
│   │       └── SettingFragment.java  
│   └── viewmodel/  
│       └── AnimalViewModel.java  


### 📁 Resources  
res/  
├── drawable/  
│   ├── bg_searchbar.xml  
│   ├── ic_edit.xml  
│   ├── ic_favorite.xml  
│   ├── ic_google.xml  
│   ├── ic_home.xml  
│   ├── ic_launcher_background.xml  
│   ├── ic_launcher_foreground.xml  
│   ├── ic_star_filled.png  
│   ├── ic_settings.xml  
│   └── rounded_button.xml  
├── layout/  
│   ├── activity_login.xml  
│   ├── activity_main.xml  
│   ├── fragment_favorit.xml  
│   ├── fragment_home.xml  
│   ├── fragment_konten.xml  
│   ├── fragment_login.xml  
│   ├── fragment_register.xml  
│   ├── fragment_setting.xml  
│   └── item_animal.xml  
├── menu/  
│   └── bottom_nav_menu.xml  
├── mipmap/  
│   └── (ikon launcher default Android)  
└── navigation/  
    └── nav_graph.xml  
 

---

## 📸 Screenshot Aplikasi

| Login | Register |
|-------|----------|
| ![Login](https://github.com/user-attachments/assets/e20a5b28-dfc9-48dc-91fd-58015af7c3e5) | ![Register](https://github.com/user-attachments/assets/5cd3bc00-c1a7-4376-865f-04f80d8c2857) |

| Home | Konten |
|------|--------|
| ![Home](https://github.com/user-attachments/assets/5c291fb0-8bb2-44f4-af1c-b6b1687f51a1) | ![Konten](https://github.com/user-attachments/assets/1e882c30-8096-4e9f-94b5-bde16136f4f7) |

| Favorit | Setting |
|---------|---------|
| ![Favorit](https://github.com/user-attachments/assets/1fa0d64d-20ce-4727-944d-d24fa4574c83) | ![Setting](https://github.com/user-attachments/assets/e273ec6b-5ccc-40ed-a5bc-39b4a5defd44) |

---

## 📦 Download APK

📥 [Unduh Zoonary v1.0](https://github.com/dHunter1811/Zoonary/releases/tag/v1.0)

---

## 🛠️ Teknologi yang Digunakan

- Kotlin  
- Android SDK  
- Retrofit  
- SQLite  
- SharedPreferences  
- Sensor & Notification  
- Clean Architecture Pattern  

---

## 👨‍💻 Tentang Pengembang

Dikembangkan oleh [Muhammad Dimas Aditya](https://github.com/dHunter1811)  
Universitas Lambung Mangkurat – Pendidikan Komputer 2023
