package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.BookStorage;
import com.edu.ulab.app.storage.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookStorage bookStorage;
    private final UserStorage userStorage;
    private final BookMapper mapper;

    public BookServiceImpl(BookStorage bookStorage, UserStorage userStorage, BookMapper mapper) {
        this.bookStorage = bookStorage;
        this.userStorage = userStorage;
        this.mapper = mapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookStorage.save(mapper.bookDtoToBook(bookDto));
        userStorage.addBook(book.getUserId(), book.getId());
        return mapper.bookToBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        deleteBookById(bookDto.getId());
        return createBook(bookDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookStorage.get(id);
        return mapper.bookToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookStorage.delete(id);
    }
}
