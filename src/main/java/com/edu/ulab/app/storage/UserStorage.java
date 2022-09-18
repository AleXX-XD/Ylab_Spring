package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserStorage extends Storage<User> {

    public void addBook(Long userId, Long bookId) {
        User user = get(userId);
        if (user == null) {
            throw new NotFoundException("The user with id = " + userId + " was not found");
        }
        user.addBook(bookId);
    }
}
