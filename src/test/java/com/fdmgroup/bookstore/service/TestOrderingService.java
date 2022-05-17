package com.fdmgroup.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.fdmgroup.bookstore.data.BookRepository;
import com.fdmgroup.bookstore.model.Book;
import com.fdmgroup.bookstore.model.Order;
import com.fdmgroup.bookstore.model.User;

class TestOrderingService {
	
	OrderingService orderingService;
	
	@Mock
	BookService mockBookService;
	
	@Mock
	AuthenticationService mockAuthenticationService;
	
	@Mock
	Book mockBook1;
	
	@Mock
	User mockUser1;
	
	@Mock
	BookRepository<Book, Integer> mockBookRepository;

	@BeforeEach
	void setUp() throws Exception {
		orderingService = new OrderingService(mockBookRepository, mockBookService, mockAuthenticationService);
	}

	@Test
	void test_placeOrderMethod_withValidBookAndUser() throws UserNotFoundException, ItemNotFoundException {
		when(mockAuthenticationService.findById(1)).thenReturn(mockUser1);
		when(mockBookService.findById(1)).thenReturn(mockBook1);
		Order returnedOrder = orderingService.placeOrder(mockBook1, mockUser1);
		verify(mockAuthenticationService, times(2)).findById(1);
		verify(mockBookService, times(2)).findById(1);
	}
	
	@Test
	void test_placeOrderMethod_withInValidBookAndValidUser() throws UserNotFoundException, ItemNotFoundException {
		when(mockAuthenticationService.findById(1)).thenReturn(mockUser1);
		when(mockBookService.findById(1)).thenReturn(null);
		Order returnedOrder = orderingService.placeOrder(mockBook1, mockUser1);
		verify(mockAuthenticationService, times(2)).findById(1);
		verify(mockBookService).findById(1);
	}
	
	@Test
	void test_placeOrderMethod_withValidBookAndInvalidUser() throws UserNotFoundException, ItemNotFoundException {
		when(mockAuthenticationService.findById(1)).thenReturn(null);
		when(mockBookService.findById(1)).thenReturn(mockBook1);
		Order returnedOrder = orderingService.placeOrder(mockBook1, mockUser1);
		verify(mockAuthenticationService).findById(1);
		verify(mockBookService, times(2)).findById(1);
	}
	
}
