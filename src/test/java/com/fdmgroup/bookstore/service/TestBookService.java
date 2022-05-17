package com.fdmgroup.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.bookstore.data.BookRepository;
import com.fdmgroup.bookstore.model.Book;
import com.fdmgroup.bookstore.model.BookGenre;

@ExtendWith(MockitoExtension.class)
class TestBookService {
	
	BookService bookService;
	
	@Mock
	Book mockBook1, mockBook2, mockBook3;
	
	@Mock
	BookRepository<Book, Integer> mockBookRepository;

	@BeforeEach
	void setUp() throws Exception {
		bookService = new BookService(mockBookRepository);
	}

	@Test
	void test_getAllBooksMethod_returnsListOfBooks_usingMockBookRepositoryFindAllMethod() {
		List<Book> mockBookList = new ArrayList<>(Arrays.asList(mockBook1, mockBook2, mockBook3));
		when(mockBookRepository.findAll()).thenReturn(mockBookList);
		List<Book> returnedList = bookService.getAllBooks();
		assertEquals(mockBookList, returnedList);
		verify(mockBookRepository).findAll();
	}
	
	@Test
	void test_getBooksOfGenreMethod_returnsListOfBooksOfHistoryGenre_whenHistoryIsPassed() {
		List<Book> mockBookList = new ArrayList<>(Arrays.asList(mockBook1, mockBook2, mockBook3));
		when(mockBookRepository.findAll()).thenReturn(mockBookList);
		when(mockBook1.getBookGenre()).thenReturn(BookGenre.HISTORY);
		when(mockBook2.getBookGenre()).thenReturn(BookGenre.FANTASY);
		when(mockBook3.getBookGenre()).thenReturn(BookGenre.HISTORY);
		List<Book> mockHistoryBooks = new ArrayList<>(Arrays.asList(mockBook1, mockBook3));
		List<Book> returnedBooks = bookService.getBooksOfGenre(BookGenre.HISTORY);
		assertEquals(mockHistoryBooks, returnedBooks);
		verify(mockBookRepository).findAll();
	}
	
	@Test
	void test_searchBooksByTitleMethod_returnsListOfBooksOfTitle() {
		List<Book> mockBookList = new ArrayList<>(Arrays.asList(mockBook1, mockBook2, mockBook3));
		when(mockBookRepository.findAll()).thenReturn(mockBookList);
		when(mockBook1.getTitle()).thenReturn("Wind in the Willows");
		when(mockBook2.getTitle()).thenReturn("Harry Potter");
		when(mockBook3.getTitle()).thenReturn("Harry Potter");
		List<Book> mockHarryPotterBooks = new ArrayList<>(Arrays.asList(mockBook2, mockBook3));
		List<Book> returnedBooks = bookService.searchBooksByTitle("Harry Potter");
		assertEquals(mockHarryPotterBooks, returnedBooks);
		verify(mockBookRepository).findAll();
	}
	
	@Test
	void test_searchBooksByAuthorName_returnsListOfBooksByAuthor() {
		List<Book> mockBookList = new ArrayList<>(Arrays.asList(mockBook1, mockBook2, mockBook3));
		when(mockBookRepository.findAll()).thenReturn(mockBookList);
		when(mockBook1.getAuthor()).thenReturn("Gerald Durrel");
		when(mockBook2.getAuthor()).thenReturn("Gerald Durrel");
		when(mockBook3.getAuthor()).thenReturn("Stephen King");
		List<Book> mockGeraldDurrelBooks = new ArrayList<>(Arrays.asList(mockBook1, mockBook2));
		List<Book> returnedBooks = bookService.searchBooksByAuthorName("Gerald Durrel");
		assertEquals(mockGeraldDurrelBooks, returnedBooks);
		verify(mockBookRepository).findAll();
	}
	
	@Test
	void test_findByIdMethod_withValidId() throws ItemNotFoundException {
		when(mockBookRepository.findById((Integer) 1)).thenReturn(mockBook1);
		Book returnedBook = bookService.findById(1);
		assertEquals(mockBook1, returnedBook);
		verify(mockBookRepository,times(2)).findById(anyInt());
	}
	
	@Test
	void test_findByIdMethod_withBookNotFound() {
		when(mockBookRepository.findById(1)).thenReturn(null);
		Assertions.assertThrows(ItemNotFoundException.class, () -> {
			Book returnedBook = bookService.findById(1);
		});
	}
}
