package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataHelper {
    private DataHelper() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    @Value
    public static class DataInfo {
        String date;
        String name;
        String phone;
        String city;
    }

    public static String setDate(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String setName() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String setPhone() {
        return faker.numerify("+79#########");
    }

    public static String setCity() {
        String[] city = {"Кемерово", "Майкоп", "Москва", "Симферополь", "Смоленск", "Тамбов", "Санкт-Петербург"};
        int rnd = new Random().nextInt(city.length);
        return city[rnd];
    }
}
