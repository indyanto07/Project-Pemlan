/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class movie {
    // Atribut disesuaikan dengan Movie Journal
    private int id;
    private String judul;
    private String genre;
    private String rating;
    private String review;
    // Constructor Kosong
    public movie() {
    }

    // Constructor Lengkap (Biasanya untuk READ/Tampil Data)
    public movie(int id, String judul, String genre, String rating, String review) {
        this.id = id;
        this.judul = judul;
        this.genre = genre;
        this.rating = rating;
        this.review = review;
    }

    // Constructor Tanpa ID (Khusus tugas kamu: CREATE/Insert)
    public movie(String judul, String genre, String rating, String review) {
        this.judul = judul;
        this.genre = genre;
        this.rating = rating;
        this.review = review;
        
    }

    // Getter dan Setter (Klik kanan di NetBeans > Insert Code > Getter and Setter > Select All)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    
}
