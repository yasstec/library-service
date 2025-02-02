package com.test.library_service.domain.port;

import com.test.library_service.domain.model.Book;
import com.test.library_service.domain.model.Borrowing;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface LibraryRepository {

    Book saveBook(Book book);

    Optional<Book> getBookById(UUID bookId);

    Set<Book> getBooks();

    Borrowing saveBorrowing(Borrowing borrowing);

    Optional<Borrowing> getBorrowingById(UUID bookingId);

    Optional<Borrowing> getBorrowingByBookId(UUID bookId);

    Set<Borrowing> getBorrowingByUserId(UUID userId);

}
