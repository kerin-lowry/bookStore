package com.fdmgroup.bookstore.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fdmgroup.bookstore.data.BookRepository;
import com.fdmgroup.bookstore.model.Book;
import com.fdmgroup.bookstore.model.Order;
import com.fdmgroup.bookstore.model.User;

public class OrderingService {
	
	private BookRepository<Book, Integer> bookRepository;
	private BookService bookService;
	private AuthenticationService authenticationService;
	
	public OrderingService(BookRepository<Book, Integer> bookRepository, BookService bookService, AuthenticationService authenticationService) {
		super();
		this.bookRepository = bookRepository;
		this.bookService = bookService;
		this.authenticationService = authenticationService;
	}
	public OrderingService() {
		super();
	}
	public Order placeOrder(Book book, User customer) throws ItemNotFoundException, UserNotFoundException {
		if (bookService.findById(book.getItemid()) != null && authenticationService.findById(customer.getUserId()) != null) {
			Order newOrder = new Order(1, book, customer, LocalDateTime.now());
			orderRepository.save(book);
			return newOrder;
		}
		return null;
	}
	
}
