package se.yrgo.test;

import java.util.*;

import jakarta.persistence.*;

import se.yrgo.domain.*;

public class Main {
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

	public static void main(String[] args) {
		setUpData();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		// Task 2
		Author author = em.find(Author.class, 8);
		List<Book> result = em.createQuery("select author.booksPublished from Author as author where author.name = :name")
				.setParameter("name", author.getName())
				.getResultList();
		for (Book b : result) {
			System.out.println(b);
		}

		// Task 3
		Book fearless = em.find(Book.class, 3);
		List<Reader> readers = em.createQuery("from Reader reader where :book member of reader.readBooks")
				.setParameter("book", fearless)
				.getResultList();
		for (Reader r : readers) {
			System.out.println(r);
		}

		// Task 4
		List<Author> authors = em.createQuery("select distinct author from Author as author join author.booksPublished as book join book.readers as reader where reader.id is not null")
				.getResultList();
		for (Author a : authors) {
			System.out.println(a);
		} 

		// Task 5
		List<Object[]> resultList = em.createQuery("select author.name, count(book) from Author author join author.booksPublished as book group by author.name")
				.getResultList();
		for(Object[] obj : resultList){
			System.out.println("Author: " + obj[0] + "  -  " + "Published books: " + obj[1]);
		} 

		// Task 6
		List<Book> bookList = em.createNamedQuery("searchByGenre", Book.class)
				.setParameter("genre", "Science fiction")
				.getResultList();
		for(Book b : bookList){
			System.out.println(b);
		}

		tx.commit();
		em.close();
	}

	public static void setUpData() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Author author1 = new Author("Lauren Roberts", "American");
		Author author2 = new Author("George Orwell", "British");
		Author author3 = new Author("Rebecca Yarros", "American");

		Book b1 = new Book("Fearless", "Fantasy", "2025-04-08");
		Book b2 = new Book("Powerful", "Fantasy", "2024-04-11");
		Book b3 = new Book("Reckless", "Fantasy", "2024-07-08");
		Book b4 = new Book("1984", "Science fiction", "1984-07-03");
		Book b5 = new Book("Animal farm", "Classic", "2000-02-24");
		Book b6 = new Book("Fourth Wing", "Fantasy", "2023-05-02");
		Book b7 = new Book("Iron Flame", "Fantasy", "2024-11-19");
		Book b8 = new Book("Onyx Storm", "Fantasy", "2025-01-21");

		author1.addBookToPublished(b1);
		author1.addBookToPublished(b2);
		author1.addBookToPublished(b3);
		author2.addBookToPublished(b4);
		author2.addBookToPublished(b5);
		author3.addBookToPublished(b6);
		author3.addBookToPublished(b7);
		author3.addBookToPublished(b8);

		em.persist(author1);
		em.persist(author2);
		em.persist(author3);

		Reader reader1 = new Reader("Jona Smith", "smith.jona@gmail.com");
		Reader reader2 = new Reader("Hannah McDaniels", "hannah@gmail.com");
		Reader reader3 = new Reader("Sarah Crawly", "crawly.sarah@gmail.com");

		reader1.addToReadBooks(b1);
		reader1.addToReadBooks(b2);
		reader1.addToReadBooks(b3);
		reader1.addToReadBooks(b7);
		reader1.addToReadBooks(b8);
		reader2.addToReadBooks(b1);
		reader2.addToReadBooks(b2);
		reader2.addToReadBooks(b3);
		reader3.addToReadBooks(b1);

		em.persist(reader1);
		em.persist(reader2);
		em.persist(reader3);

		tx.commit();
		em.close();
	}

}
