package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class BasicEtagWriter implements EtagWriter {

    @Override
    public String write(Object object) {
        return String.format("%d", object.hashCode());
    }

}
