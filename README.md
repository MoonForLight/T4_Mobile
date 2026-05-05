# Student Contact App

Aplikasi pengelolaan data mahasiswa yang dirancang untuk memudahkan manajemen kontak, catatan, serta pengaturan profil pengguna. Aplikasi ini mengintegrasikan berbagai metode penyimpanan data di Android untuk memberikan pengalaman pengguna yang optimal.

## Identitas
- **Nama:** Lalu Muhammad Rizaldi Kurniawan
- **NIM:** F1D023101020

---

## Deskripsi Aplikasi
Student Contact App adalah aplikasi berbasis Android yang memungkinkan pengguna untuk:
1.  Melakukan autentikasi (Login) dengan fitur *Remember Me*.
2.  Mengelola data mahasiswa (Tambah, Lihat, Edit, dan Hapus) secara persisten.
3.  Melakukan pencarian mahasiswa berdasarkan Nama atau NIM secara *real-time*.
4.  Menambahkan catatan khusus untuk setiap mahasiswa yang disimpan ke dalam file internal.
5.  Mengatur preferensi aplikasi seperti Dark Mode dan Notifikasi.

---

## Screenshot Aplikasi
![Lalu Muhammad Rizaldi Kurniawan](<Screenshot 2026-05-05 141645.png>) ![Lalu Muhammad Rizaldi Kurniawan](<Screenshot 2026-05-05 141626.png>) ![Lalu Muhammad Rizaldi Kurniawan](<Screenshot 2026-05-05 141631.png>) ![Lalu Muhammad Rizaldi Kurniawan](<Screenshot 2026-05-05 141639.png>)

---

## Metode Penyimpanan Data
Aplikasi ini menggunakan tiga metode penyimpanan data yang berbeda sesuai dengan fungsinya:

1.  **SharedPreferences**: 
    - **Kegunaan**: Menyimpan sesi login (*isLoggedIn*, *username*) dan pengaturan aplikasi (Dark Mode, Notifikasi).
    - **Alasan**: Sangat efisien untuk menyimpan data sederhana berupa pasangan kunci-nilai (*key-value pairs*) yang perlu diakses dengan cepat.

2.  **Internal Storage (File)**: 
    - **Kegunaan**: Menyimpan catatan (*notes*) spesifik untuk setiap mahasiswa dalam format file `.txt`.
    - **Alasan**: Cocok untuk menyimpan data teks yang lebih panjang dan bersifat privat bagi aplikasi, tanpa memerlukan struktur tabel database yang kompleks.

3.  **Room Database (SQLite)**: 
    - **Kegunaan**: Menyimpan data utama mahasiswa seperti Nama, NIM, Prodi, Email, dan Semester.
    - **Alasan**: Memberikan kemampuan untuk melakukan operasi CRUD (Create, Read, Update, Delete) yang kuat, mendukung pencarian terstruktur, dan pengelolaan data yang lebih aman dan teratur.

---

## Kendala dan Solusi
Selama pengembangan aplikasi, terdapat beberapa kendala yang dihadapi:
