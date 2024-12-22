package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ContactManagerApplication {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(ContactManagerApplication.class);
    }
}