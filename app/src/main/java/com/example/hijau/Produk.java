package com.example.hijau;

public class Produk {
    private String nama;
    private String kalori;
    private String harga;
    private int gambarResId;

    public Produk(String nama, String kalori, String harga, int gambarResId) {
        this.nama = nama;
        this.kalori = kalori;
        this.harga = harga;
        this.gambarResId = gambarResId;
    }

    public String getNama() {
        return nama;
    }

    public String getKalori() {
        return kalori;
    }

    public String getHarga() {
        return harga;
    }

    public int getGambarResId() {
        return gambarResId;
    }
}
