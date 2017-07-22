package com.kgajay.demo.utils;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ajay.kg created on 11/06/17.
 */
public enum SpringProvider {

    INSTANCE;

    private AnnotationConfigApplicationContext context;

    private SpringProvider() {
        context = new AnnotationConfigApplicationContext();
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

}