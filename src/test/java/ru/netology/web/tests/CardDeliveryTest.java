package ru.netology.web.tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class CardDeliveryTest {

    private Faker faker;
    DataHelper dataHelper = new DataHelper();

    @BeforeEach
    void setUp() {
        faker = new Faker(new Locale("ru"));
        open("http://localhost:9999/");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
    }

    @Test
    void shouldSendForm() {
        String date = dataHelper.setDate(5);
        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldBe((visible), Duration.ofSeconds(11))
                .shouldHave(text("Успешно!"), Duration.ofSeconds(5));
        String text = $("[data-test-id='success-notification'] .notification__content").text();
        assertEquals("Встреча успешно запланирована на " + date, text);
    }

    @Test
    void shouldSendFormWithCityFromList() {
        String date = dataHelper.setDate(5);
        $("[data-test-id=city] input").setValue("Мо");
        $(withText("Москва")).click();
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldBe((visible), Duration.ofSeconds(11))
                .shouldHave(text("Успешно!"), Duration.ofSeconds(5));
        String text = $("[data-test-id='success-notification'] .notification__content").text();
        assertEquals("Встреча успешно запланирована на " + date, text);
    }

    @Test
    void shouldGetErrorIfEmptyForm() {
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfOnlyCity() {
        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $(withText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub")
                .shouldHave(text("Неверно введена дата"));
    }

    @Test
    void shouldGetErrorIfOnlyDate() {
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfOnlyName() {
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfOnlyPhone() {
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfOnlyAgreement() {
        $("[data-test-id=city] input").click();
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorWithoutCity() {
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfInvalidCity() {
        $("[data-test-id=city] input").setValue("Moscow");
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldGetErrorWithoutDate() {
        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub")
                .shouldHave(text("Неверно введена дата"));
    }

    @Test
    void shouldGetErrorIfInvalidDate() {
        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $("[data-test-id=date] input").setValue(dataHelper.setDate(1));
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub")
                .shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldGetErrorWithoutName() {
        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfInvalidName() {
        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $("[data-test-id=name] input").setValue("John");
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(text("Имя и Фамилия указаны неверно"));
    }

    @Test
    void shouldGetErrorWithoutPhone() {
        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldGetErrorIfInvalidPhone() {
        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=phone] input").setValue("89270000000");
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(text("Телефон указан неверно"));
    }

    @Test
    void shouldGetErrorWithoutAgreement() {
        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $(withText("Запланировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text")
                .shouldBe(visible)
                .shouldHave(text("Я соглашаюсь с условиями"));
    }
}