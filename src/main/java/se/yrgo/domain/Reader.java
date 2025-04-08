package se.yrgo.domain;

import java.util.*;

import jakarta.persistence.*;

@Entity
public class Reader{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    @Column(length = 30)
    private String name;
    @Column(length = 30)
    private String email;
    @ManyToMany(mappedBy = "readers")
    List<Book> readBooks;

    public Reader(String name, String email){
        this.name = name;
        this.email = email;
        readBooks = new ArrayList<Book>();
    }

    public Reader(){}

    public List<Book> getReadBooks() {
        return readBooks;
    }

    public void addToReadBooks(Book book){
        this.readBooks.add(book);
        book.getReaders().add(this);
    }

    public String toString(){
        return String.format("Name: %s  -  Email: %s", name, email);
    }
}