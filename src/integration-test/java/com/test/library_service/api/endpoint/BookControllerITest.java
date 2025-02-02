package com.test.library_service.api.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.library_service.domain.model.Book;
import com.test.library_service.domain.model.Borrowing;
import com.test.library_service.domain.model.User;
import com.test.library_service.domain.service.LibraryService;
import com.test.service.library_service.api.dto.BookCreationRequestDto;
import com.test.service.library_service.api.dto.BorrowRequestDto;
import com.test.service.library_service.api.dto.ReturnRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = BookController.class)
@ActiveProfiles("integration-test")
@SuppressWarnings("java:S100")
class BookControllerITest {

    private static final String BOOKS_API_PATH = "/api/v1/books";
    private static final String BORROWING_API_PATH = "/api/v1/borrowings";
    private static final String BORROWING_RETURN_API_PATH = BORROWING_API_PATH+"/{id}/return";

    private static final UUID BOOK_ID = UUID.randomUUID();
    private static final String BOOK_TITLE = "Book".concat(BOOK_ID.toString());
    private static final UUID USER_ID = UUID.randomUUID();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private LibraryService libraryService;

    @Test
    void should_create_newBook() throws Exception {
        // given
        var bookCreationRequestDto = createBookCreationDto();
        var book = new Book(BOOK_ID,BOOK_TITLE);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(libraryService.addBook(anyString())).thenReturn(book);

        // when
        mockMvc
                .perform(
                        post(BOOKS_API_PATH)
                                .content(new ObjectMapper().writeValueAsString(bookCreationRequestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // then
        BDDMockito.then(libraryService).should(times(1)).addBook(book.getTitle());
    }

    @Test
    void should_borrow_Book() throws Exception {
        // given
        var borrowRequestDto = createBorrowRequestDto();
        var borrowing = createBorrowing();
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(libraryService.borrowBook(any(),any())).thenReturn(borrowing);

        // when
        mockMvc
                .perform(
                        post(BORROWING_API_PATH)
                                .content(new ObjectMapper().writeValueAsString(borrowRequestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // then
        BDDMockito.then(libraryService).should(times(1)).borrowBook(borrowRequestDto.getUserId(),borrowRequestDto.getBookId());
    }

    @Test
    void should_return_Book() throws Exception {
        // given
        var borrowingId = UUID.randomUUID();
        var returnRequestDto = createReturnRequestDto();
        var borrowing = createBorrowing();
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(libraryService.returnBorrowedBook(any(),any())).thenReturn(borrowing);

        // when
        mockMvc
                .perform(
                        put(BORROWING_RETURN_API_PATH,borrowingId)
                                .content(new ObjectMapper().writeValueAsString(returnRequestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // then
        BDDMockito.then(libraryService).should(times(1)).returnBorrowedBook(borrowingId, Optional.empty());
    }

    @Test
    void should_getBorrowedBooks() throws Exception {
        // given
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        var borrowing = createBorrowing();
        when(libraryService.getBorrowedBooksByUser(USER_ID)).thenReturn(List.of(borrowing));

        // when
        mockMvc
                .perform(
                        get(BORROWING_API_PATH).param("userId",USER_ID.toString())
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookId").value(BOOK_ID.toString()));

        // then
        BDDMockito.then(libraryService).should(times(1)).getBorrowedBooksByUser(USER_ID);
    }

    private BookCreationRequestDto createBookCreationDto() {
        return new BookCreationRequestDto().title(BOOK_TITLE);
    }

    private BorrowRequestDto createBorrowRequestDto() {
        return new BorrowRequestDto().bookId(BOOK_ID).userId(USER_ID);
    }

    private Borrowing createBorrowing() {
        return Borrowing.builder().id(UUID.randomUUID()).user(new User(USER_ID)).book(new Book(BOOK_ID)).borrowDate(Instant.now()).build();
    }

    private ReturnRequestDto createReturnRequestDto() {
        return new ReturnRequestDto().bookId(BOOK_ID).userId(USER_ID);
    }
}
