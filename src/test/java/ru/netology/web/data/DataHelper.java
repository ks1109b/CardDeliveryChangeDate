package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataHelper {

    @Value
    public static class DataInfo {
        private String date;
        private String name;
        private String phone;
        private String city;
    }

    public String setDate(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String setName(Faker faker) {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public String setPhone(Faker faker) {
        return faker.numerify("+79#########");
    }

    public String setCity(Faker faker) {
        String[] city = { "Кемерово", "Майкоп", "Москва", "Симферополь", "Смоленск", "Тамбов", "Санкт-Петербург"};
        int rnd = new Random().nextInt(city.length);
        return city[rnd];
//        return faker.address().city();
    }
}
