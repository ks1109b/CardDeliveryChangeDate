package ru.netology.web.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryChangeDateTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSendFormAgain() {
        open("http://localhost:9999/");

        $("[data-test-id=city] input").setValue(DataHelper.setCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(DataHelper.setDate(5));
        $("[data-test-id=name] input").setValue(DataHelper.setName());
        $("[data-test-id=phone] input").setValue(DataHelper.setPhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldBe((visible)).shouldHave(text("Успешно!"));
        String date = $("[data-test-id=date] input").getValue();
        String text = $("[data-test-id='success-notification'] .notification__content").text();
        assertEquals("Встреча успешно запланирована на " + date, text);

        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(DataHelper.setDate(10));
        $(withText("Запланировать")).click();
        $("[data-test-id=replan-notification]")
                .shouldBe((visible)).shouldHave(text("Необходимо подтверждение"));
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldBe((visible)).shouldHave(text("Успешно!"));
        String expected = $("[data-test-id=date] input").getValue();
        String actual = $("[data-test-id='success-notification'] .notification__content").text();
        assertEquals("Встреча успешно запланирована на " + expected, actual);
    }
}
