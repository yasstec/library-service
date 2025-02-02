package com.test.library_service.api.endpoint;

import com.test.library_service.domain.service.LibraryService;
import com.test.service.library_service.api.dto.*;
import com.test.service.library_service.api.endpoint.LibraryApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.test.library_service.api.mapper.LibraryMapper.LIBRARY_API_MAPPER;

@RestController
@RequiredArgsConstructor
public class BookController implements LibraryApi {

    private final LibraryService libraryService;

    @Override
    public ResponseEntity<BookDto> addNewBook(BookCreationRequestDto bookCreationRequestDto) {
        var createdBook = libraryService.addBook(bookCreationRequestDto.getTitle());
        var bookDto = LIBRARY_API_MAPPER.toBook(createdBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }

    @Override
    public ResponseEntity<List<BookDto>> getBooks() {
        var books = libraryService.getBooks();
        var bookDtos = LIBRARY_API_MAPPER.toBookDtos(books);
        return ResponseEntity.ok(bookDtos);
    }

    @Override
    public ResponseEntity<BorrowingDto> borrowBook(BorrowRequestDto borrowRequestDto) {
        var borrowedBook = libraryService.borrowBook(borrowRequestDto.getUserId(), borrowRequestDto.getBookId());
        var borrowedBookDto = LIBRARY_API_MAPPER.toBorrowingDto(borrowedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowedBookDto);
    }

    @Override
    public ResponseEntity<List<BorrowingDto>> getBorrowedBooks(UUID userId) {
        var borrowedBooks = libraryService.getBorrowedBooksByUser(userId);
        var borrowedBookDtos = LIBRARY_API_MAPPER.toBorrowingDtos(borrowedBooks);
        return ResponseEntity.ok(borrowedBookDtos);
    }

    @Override
    public ResponseEntity<BorrowingDto> returnBook(UUID bookingId, ReturnRequestDto returnRequestDto) {
        var returnDate = Optional.ofNullable(returnRequestDto.getReturnDate()).map(Instant::parse);
        var updatedBorrowing = libraryService.returnBorrowedBook(bookingId, returnDate);
        var borrowingDto = LIBRARY_API_MAPPER.toBorrowingDto(updatedBorrowing);
        return ResponseEntity.ok(borrowingDto);
    }

}
