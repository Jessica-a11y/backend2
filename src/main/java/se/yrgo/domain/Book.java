package se.yrgo.domain;

import java.util.*;

import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    @Column(length = 20)
    private String title;
    @Column(length = 20)
    private String genre;
    @Column(length = 20)
    private String publicationYear; 
    @ManyToMany
    List<Reader> readers;

    public Book(String title, String genre, String publicationYear){
        this.title = title;
        this.genre = genre;
        this.publicationYear = publicationYear;
        readers = new ArrayList<Reader>();
    }

    public Book(){}

    public List<Reader> getReaders(){
        return readers;
    }

    public void addReader(Reader reader){
        this.readers.add(reader);
    }

    public String toString(){
        return String.format("Title: %s  -  Genre: %s  -  Publication Year: %s", title, genre, publicationYear);
    }

}