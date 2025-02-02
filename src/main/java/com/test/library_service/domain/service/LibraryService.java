package com.test.library_service.domain.service;

import com.test.library_service.domain.exception.ObjectNotFoundException;
import com.test.library_service.domain.model.Book;
import com.test.library_service.domain.model.Borrowing;
import com.test.library_service.domain.port.LibraryRepository;
import com.test.library_service.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;

    public List<Book> getBooks() {
        return libraryRepository.getBooks().stream().toList();
    }

    public Book addBook(String title) {
        var book = new Book(title);
        return libraryRepository.saveBook(book);
    }

    public Borrowing borrowBook(UUID userId, UUID bookId) {
        var user = userRepository.getUserById(userId).orElseThrow(() -> new ObjectNotFoundException(userId));
        var book = libraryRepository.getBookById(bookId).orElseThrow(() -> new ObjectNotFoundException(bookId));

        if (!isBookBorrowed(bookId)) {
            var newBorrowedBook = Borrowing.builder().id(UUID.randomUUID()).user(user).book(book).borrowDate(Instant.now()).build();
            return libraryRepository.saveBorrowing(newBorrowedBook);
        }
        throw new UnsupportedOperationException("Book is not borrowed");
    }

    public List<Borrowing> getBorrowedBooksByUser(UUID userId) {
        var user = userRepository.getUserById(userId);
        if (user.isEmpty()) throw new ObjectNotFoundException(userId);
        return libraryRepository.getBorrowingByUserId(userId).stream().toList();
    }

    public Borrowing returnBorrowedBook(UUID borrowingId, Optional<Instant> returnDate) {
        var booking = libraryRepository.getBorrowingById(borrowingId).orElseThrow(() -> new ObjectNotFoundException(borrowingId));
        if (returnDate.isEmpty()) {
            booking.setReturnDate(Instant.now());
        }
        return libraryRepository.saveBorrowing(booking);
    }

    private boolean isBookBorrowed(UUID bookId) {
        return libraryRepository.getBorrowingByBookId(bookId).isPresent();
    }

}
