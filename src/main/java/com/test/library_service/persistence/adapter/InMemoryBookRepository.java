package com.test.library_service.persistence.adapter;

import com.test.library_service.domain.model.Book;
import com.test.library_service.domain.model.Borrowing;
import com.test.library_service.domain.port.LibraryRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
//TODO Should use Entity instead of domain model, but tolerated for this simple demo
public class InMemoryBookRepository implements LibraryRepository {

    private final Set<Book> books = new HashSet<>();
    private final Set<Borrowing> borrowings = new HashSet<>();

    @Override
    public Book saveBook(Book book) {
        books.add(book);
        return book;
    }

    @Override
    public Optional<Book> getBookById(UUID bookId) {
        return books.stream().filter(e -> bookId.equals(e.getId())).findFirst();
    }

    @Override
    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public Borrowing saveBorrowing(Borrowing borrowing) {
        borrowings.remove(borrowing);
        borrowings.add(borrowing);
        return borrowing;
    }

    @Override
    public Optional<Borrowing> getBorrowingById(UUID bookingId) {
        return borrowings.stream().filter(b -> b.getId().equals(bookingId)).findFirst();
    }

    @Override
    public Optional<Borrowing> getBorrowingByBookId(UUID bookId) {
        return borrowings.stream().filter(b -> b.getBook().getId().equals(bookId)).findFirst();
    }


    @Override
    public Set<Borrowing> getBorrowingByUserId(UUID userId) {
        return borrowings.stream().filter(b -> b.getUser().getId().equals(userId)).collect(Collectors.toSet());
    }

}
