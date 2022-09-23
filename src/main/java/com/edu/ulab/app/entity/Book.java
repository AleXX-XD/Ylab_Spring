package com.edu.ulab.app.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book extends AbstractEntity {

    private Long userId;
    private String title;
    private String author;
    private long pageCount;
}
