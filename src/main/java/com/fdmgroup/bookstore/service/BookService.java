package com.fdmgroup.bookstore.service;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.bookstore.data.BookRepository;
import com.fdmgroup.bookstore.model.Book;
import com.fdmgroup.bookstore.model.BookGenre;

public class BookService {
	
	private BookRepository<Book, Integer> bookRepository;

	public BookService(BookRepository<Book, Integer> bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}
	
	public BookService() {
		super();
	}

	public List<Book> getAllBooks() {
		
		return bookRepository.findAll();
	}

	public List<Book> getBooksOfGenre(BookGenre genre) {
		List<Book> booksOfGenre = new ArrayList<>();
		for (Book book : getAllBooks()) {
			if (book.getBookGenre().equals(genre)) {
				booksOfGenre.add(book);
			}
		}
		return booksOfGenre;
	}

	public List<Book> searchBooksByTitle(String bookTitle) {
		List<Book> booksOfTitle = new ArrayList<>();
		for (Book book : getAllBooks()) {
			if (book.getTitle().equals(bookTitle)) {
				booksOfTitle.add(book);
			}
		}
		return booksOfTitle;
	}

	public List<Book> searchBooksByAuthorName(String bookAuthorNameToSearch) {
		List<Book> booksByAuthor = new ArrayList<>();
		for (Book book : getAllBooks()) {
			if (book.getAuthor().equals(bookAuthorNameToSearch)) {
				booksByAuthor.add(book);
			}
		}
		return booksByAuthor;
	}

	public Book findById(int id) throws ItemNotFoundException {
		if (bookRepository.findById(id) != null) {
			return bookRepository.findById(id);
		}
		throw new ItemNotFoundException("Book could not be found."); 
	}
	
	
	
	
}
