package ru.gb.example_hw_sem5.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/*
Энумератор для перечисления статуса
 */
@RequiredArgsConstructor
@Getter
public enum TaskStatus {
    NOT_STARTED("Hе начата"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершена");

    private final String value;

}