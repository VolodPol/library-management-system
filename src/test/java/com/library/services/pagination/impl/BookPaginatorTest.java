package com.library.services.pagination.impl;


import com.library.commands.RequestContent;
import com.library.dao.BookDao;
import com.library.entity.Book;
import com.library.entity.sorting.SortBy;
import com.library.entity.sorting.SortOrder;
import com.library.exceptions.DaoException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.library.services.pagination.Paginator;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class BookPaginatorTest {
    private Paginator<Book> paginator;
    @Mock
    private BookDao bookDao;
    @Mock
    private RequestContent content;

    @ParameterizedTest
    @MethodSource({"provideArguments"})
    public void provideData(int page, String sortOrder, String sortBy, int recNum, int allRecords)
            throws DaoException {
        MockitoAnnotations.openMocks(this);

        when(content.getParameter("page")).thenReturn(String.valueOf(page));
        when(content.getParameter("sortOrder")).thenReturn(sortOrder);
        when(content.getParameter("sortBy")).thenReturn(sortBy);
        when(content.getParameter("recNum")).thenReturn(String.valueOf(recNum));

        List<Book> books = provideBookList(recNum);
        when(bookDao.findAll((page - 1) * recNum, recNum,
                SortOrder.valueOf(sortOrder.toUpperCase()),
                SortBy.valueOf(sortBy.toUpperCase())))
                .thenReturn(books);
        when(bookDao.getNumOfRecs()).thenReturn(allRecords);

        paginator = new BookPaginator(bookDao);

        assertAll(
                () -> assertEquals(books, paginator.provideData(content)),
                () -> assertEquals(page, paginator.getCurrentPage()),
                () -> assertEquals(Math.ceil((double) allRecords / recNum), paginator.getNumberOfPages()),
                () -> assertEquals(recNum, paginator.getRecordsPerPage())
        );

        verify(content, times(4)).getParameter(anyString());
        verify(content, times(2)).setRequestAttribute(anyString(), any(Object.class));

        verify(bookDao, times(1)).findAll((page - 1) * recNum, recNum,
                SortOrder.valueOf(sortOrder.toUpperCase()),
                SortBy.valueOf(sortBy.toUpperCase()));
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(1, "asc", "title", 4, 14),
                Arguments.of(3, "asc", "author", 3, 15),
                Arguments.of(2, "desc", "publication", 5, 12),
                Arguments.of(4, "asc", "date", 2, 11),
                Arguments.of(1, "desc", "title", 6, 7)
        );
    }
    private List<Book> provideBookList(int number) {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < number; i++) {books.add(new Book());}
        return books;
    }
}