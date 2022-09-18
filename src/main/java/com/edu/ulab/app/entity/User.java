package com.edu.ulab.app.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User extends AbstractEntity {

    private String fullName;
    private String title;
    private int age;
    private List<Long> bookIdList = new ArrayList<>();

    public void addBook(Long id) {
        bookIdList.add(id);
    }
}
