package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = getBookIdList(userBookRequest, createdUser.getId());

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(Long userId, UserBookRequest userBookRequest) {
        log.info("Received a request to update the user and books by id: {} / {}", userId, userBookRequest);

        UserDto user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("The user with id = " + userId + " was not found");
        }

        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        UserDto updatedUser = userService.updateUser(userId, userDto);

        List<Long> bookIdList = userService.getBookIdList(user);
        bookIdList.forEach(bookService::deleteBookById);

        bookIdList = getBookIdList(userBookRequest, updatedUser.getId());

        return UserBookResponse.builder()
                .userId(updatedUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("Received a request to get a user and his books by id: {}", userId);

        UserDto user = userService.getUserById(userId);
        log.info("The received user: {}", user);

        if (user == null) {
            throw new NotFoundException("The user with id = " + userId + " was not found");
        }

        List<Long> bookIdList = userService.getBookIdList(user);

        return UserBookResponse.builder()
                .userId(user.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("A request was received to delete the user and his books by id: {}", userId);

        UserDto user = userService.getUserById(userId);
        log.info("The received user: {}", user);

        if (user == null) {
            throw new NotFoundException("The user with id = " + userId + " was not found");
        }

        List<Long> bookIdList = userService.getBookIdList(user);
        bookIdList.forEach(bookService::deleteBookById);
        userService.deleteUserById(userId);
    }

    private List<Long> getBookIdList(UserBookRequest userBookRequest, Long userId) {
        List<Long> bookIdList = new ArrayList<>();
        try {
            bookIdList = userBookRequest.getBookRequests()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(bookMapper::bookRequestToBookDto)
                    .filter(bookDto -> bookDto.getTitle() != null && !bookDto.getTitle().equals(""))
                    .peek(bookDto -> bookDto.setUserId(userId))
                    .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                    .map(bookService::createBook)
                    .peek(createdBook -> log.info("Created book: {}", createdBook))
                    .map(BookDto::getId)
                    .toList();
            log.info("Collected book ids: {}", bookIdList);
        } catch (NullPointerException ex) {
            log.warn("The list of books is not specified");
        }
        return bookIdList;
    }
}
