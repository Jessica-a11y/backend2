package se.yrgo.domain;

import java.util.*;

import jakarta.persistence.*;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private String nationality;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BOOK_FK")
    Set<Book> booksPublished;

    public Author(String name, String nationality){
        this.name = name;
        this.nationality = nationality;
        this.booksPublished = new HashSet<Book>();
    }

    public Author(){}

    public String getName(){
        return name;
    }

    public void addBookToPublished(Book book){
        this.booksPublished.add(book);
    }

    public String toString(){
        return String.format("Name: %s  -  Nationality: %s", name, nationality);
    }
}