package com.test.library_service.domain.service;

import com.test.library_service.domain.exception.ObjectNotFoundException;
import com.test.library_service.domain.model.Book;
import com.test.library_service.domain.model.Borrowing;
import com.test.library_service.domain.model.User;
import com.test.library_service.domain.port.LibraryRepository;
import com.test.library_service.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LibraryServiceTest {

    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LibraryService libraryService;

    private LibraryServiceTest() {
    }

    private UUID userId;
    private UUID bookId;
    private Book book;
    private User user;
    private Borrowing borrowing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        bookId = UUID.randomUUID();
        user = new User(userId, "John Doe");
        book = new Book(bookId, "Some Book Title");
        borrowing = Borrowing.builder().book(book).user(user).build();
    }

    @Test
    void should_getBooks() {
        //given
        when(libraryRepository.getBooks()).thenReturn(Set.of(book));
        //when
        List<Book> books = libraryService.getBooks();
        //then
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book, books.getFirst());
    }

    @Test
    void should_addBook() {
        //given
        when(libraryRepository.saveBook(any(Book.class))).thenReturn(book);
        //when
        Book addedBook = libraryService.addBook("Some Book Title");
        //then
        assertNotNull(addedBook);
        assertEquals("Some Book Title", addedBook.getTitle());
    }

    @Test
    void should_borrowBook() {
        //given
        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        when(libraryRepository.getBookById(bookId)).thenReturn(Optional.of(book));
        when(libraryRepository.getBorrowingByBookId(bookId)).thenReturn(Optional.empty());
        when(libraryRepository.saveBorrowing(any())).thenReturn(borrowing);
        //when
        var newBorrowing = libraryService.borrowBook(userId, bookId);
        //then
        assertNotNull(newBorrowing);
        assertEquals(user, newBorrowing.getUser());
        assertEquals(book, newBorrowing.getBook());
    }

    @Test
    void should_getBorrowedBooks_byUser() {
        //given
        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        when(libraryRepository.getBorrowingByUserId(userId)).thenReturn(Set.of(borrowing));
        //when
        List<Borrowing> borrowings = libraryService.getBorrowedBooksByUser(userId);
        //then
        assertNotNull(borrowings);
        assertEquals(1, borrowings.size());
        assertEquals(borrowing, borrowings.getFirst());
    }

    @Test
    void should_throws_ObjectNotFoundException_when_borrow_missing_book() {
        //given
        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        when(libraryRepository.getBookById(bookId)).thenReturn(Optional.empty());
        //when then
        assertThrows(ObjectNotFoundException.class, () -> libraryService.borrowBook(userId, bookId));
    }

    @Test
    void should_not_BorrowBook_when_bookAlreadyBorrowed() {
        //given
        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        when(libraryRepository.getBookById(bookId)).thenReturn(Optional.of(book));
        when(libraryRepository.getBorrowingByBookId(bookId)).thenReturn(Optional.of(borrowing));
        //when then
        assertThrows(UnsupportedOperationException.class, () -> libraryService.borrowBook(userId, bookId));
    }

    @Test
    void should_throws_ObjectNotFoundException_when_getBorrowedBooks_user_notFound() {
        //given
        when(userRepository.getUserById(userId)).thenReturn(Optional.empty());
        // when then
        assertThrows(ObjectNotFoundException.class, () -> libraryService.getBorrowedBooksByUser(userId));
    }

    @Test
    void should_returnBorrowedBook() {
        //given
        when(libraryRepository.getBorrowingById(any(UUID.class))).thenReturn(Optional.of(borrowing));
        var returnDate = Instant.now();
        var savedReturnedBook = Borrowing.builder().user(user).book(book).returnDate(returnDate).build();
        when(libraryRepository.saveBorrowing(any(Borrowing.class))).thenReturn(savedReturnedBook);
        //when
        Borrowing returnedBorrowing = libraryService.returnBorrowedBook(UUID.randomUUID(), Optional.of(returnDate));
        //then
        assertNotNull(returnedBorrowing);
        assertNotNull(returnedBorrowing.getReturnDate());
    }

    @Test
    void should_throws_ObjectNotFoundException_when_return_missing_borrowedBook() {
        //given
        when(libraryRepository.getBorrowingById(any(UUID.class))).thenReturn(Optional.empty());
        //when then
        assertThrows(ObjectNotFoundException.class, () -> libraryService.returnBorrowedBook(UUID.randomUUID(), Optional.of(Instant.now())));
    }

}
