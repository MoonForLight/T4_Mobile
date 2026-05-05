# 1. Inisialisasi folder project sebagai repository Git
git init

# 2. Tambahkan semua file ke dalam staging area
# (Android Studio sudah otomatis menyediakan file .gitignore agar file sampah tidak ikut terupload)
git add .

# 3. Lakukan commit pertama Anda
git commit -m "Initial commit - Student Contact App complete features"

# 4. Atur branch utama ke 'main'
git branch -M main

# 5. Hubungkan project lokal dengan repository GitHub Anda
# GANTI URL di bawah dengan URL yang Anda salin dari GitHub tadi
git remote add origin https://github.com/MoonForLight/T4_Mobile.git

# 6. Push kode Anda ke GitHub
git push -u origin main