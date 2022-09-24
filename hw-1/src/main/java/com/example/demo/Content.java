package com.example.demo;

import com.google.common.util.concurrent.CycleDetectingLockFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface Content {
    String contentField();
    String photo();
}
