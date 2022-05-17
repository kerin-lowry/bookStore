package com.fdmgroup.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.bookstore.data.UserRepository;
import com.fdmgroup.bookstore.model.User;

@ExtendWith(MockitoExtension.class)
class TestAuthenticationService {
	
	AuthenticationService authenticationService;
	
	@Mock
	User mockUser1;
	
	@Mock
	UserRepository<User, Integer> mockUserRepository;

	@BeforeEach
	void setUp() throws Exception {
		authenticationService = new AuthenticationService(mockUserRepository);
	}

	@Test
	void test_authenticateMethod_withValidatedUser() throws UserNotFoundException {
		when(mockUserRepository.validate("user1", "password1")).thenReturn(true);
		when(mockUserRepository.findByUsername("user1")).thenReturn(mockUser1);
		User anotherUser = authenticationService.authenticate("user1", "password1");
		assertEquals(mockUser1, anotherUser);
		verify(mockUserRepository).validate(anyString(), anyString());
		verify(mockUserRepository).findByUsername(anyString());
	}
	
	@Test
	void test_authenticateMethod_withInvalidUser() {
		when(mockUserRepository.validate("user1", "password1")).thenReturn(false);
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			User user = authenticationService.authenticate("user1", "password1");
		});
	}
	
	@Test
	void test_findByIdMethod_withValidId() throws UserNotFoundException {
		when(mockUserRepository.findById((Integer) 1)).thenReturn(mockUser1);
		User anotherUser = authenticationService.findById(1);
		assertEquals(mockUser1, anotherUser);
		verify(mockUserRepository,times(2)).findById(anyInt());
	}
	
	@Test
	void test_findByIdMethod_withUserNotFound() {
		when(mockUserRepository.findById(1)).thenReturn(null);
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			User user = authenticationService.findById(1);
		});
	}

}
