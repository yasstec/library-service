package com.test.library_service.api.mapper;

import com.test.library_service.domain.model.Book;
import com.test.library_service.domain.model.Borrowing;
import com.test.service.library_service.api.dto.BookDto;
import com.test.service.library_service.api.dto.BorrowingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    LibraryMapper LIBRARY_API_MAPPER = Mappers.getMapper(LibraryMapper.class);

    List<BookDto> toBookDtos(List<Book> books);

    BookDto toBook(Book book);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "borrowDate", target = "borrowDate")
    @Mapping(source = "returnDate", target = "returnDate")
    BorrowingDto toBorrowingDto(Borrowing borrowing);

    @Mapping(source = "book.id", target = "id")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "borrowDate", target = "borrowDate")
    @Mapping(source = "returnDate", target = "returnDate")
    List<BorrowingDto> toBorrowingDtos(List<Borrowing> borrowings);

}
