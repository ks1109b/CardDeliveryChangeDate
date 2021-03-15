package ru.netology.web.tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryChangeDateTest {

    private Faker faker;
    DataHelper dataHelper = new DataHelper();

    @Test
    void shouldSendFormAgain() {
        faker = new Faker(new Locale("ru"));
        open("http://localhost:9999/");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);

        $("[data-test-id=city] input").setValue(dataHelper.setCity(faker));
        $("[data-test-id=date] input").setValue(dataHelper.setDate(5));
        $("[data-test-id=name] input").setValue(dataHelper.setName(faker));
        $("[data-test-id=phone] input").setValue(dataHelper.setPhone(faker));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldBe((visible))
                .shouldHave(text("Успешно!"));
        String date = $("[data-test-id=date] input").getValue();
        String text = $("[data-test-id='success-notification'] .notification__content").text();
        assertEquals("Встреча успешно запланирована на " + date, text);

        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(dataHelper.setDate(10));
        $(withText("Запланировать")).click();
        $("[data-test-id=replan-notification]")
                .shouldBe((visible))
                .shouldHave(text("Необходимо подтверждение"));
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldBe((visible))
                .shouldHave(text("Успешно!"));
        String expected = $("[data-test-id=date] input").getValue();
        String actual = $("[data-test-id='success-notification'] .notification__content").text();
        assertEquals("Встреча успешно запланирована на " + expected, actual);
    }
}
