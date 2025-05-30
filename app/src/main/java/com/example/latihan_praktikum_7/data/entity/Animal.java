package com.example.latihan_praktikum_7.data.entity;

public class Animal {
    private int id;
    private String name;
    private String species;
    private String description;
    private int isFavorite; // Menambahkan properti isFavorite

    // Konstruktor dengan isFavorite
    public Animal(int id, String name, String species, String description, int isFavorite) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.description = description;
        this.isFavorite = isFavorite;
    }

    // Getter dan Setter untuk isFavorite
    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    // Getter dan Setter untuk id, name, species, dan description
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
