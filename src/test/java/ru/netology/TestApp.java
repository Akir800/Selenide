package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class TestApp {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1000x800";
        open("http://localhost:9999");
    }

    @Test
    void testOne() {
        String planningDate = generateDate(4);
        $x("//input[@placeholder='Дата встречи']")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);

        $x("//input[@placeholder='Город']").setValue("Калининград");
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Владимир Малков-Бельский");
        $x("//input[@name='phone']").setValue("+79261549122");
        $x("//span[@class='checkbox__box']").click();
        $x("//span[@class='button__text']").click();

        String notification = "Встреча успешно забронирована на " + planningDate;
        $x("//div[@class='notification__content']")
                .should(appear, Duration.ofSeconds(11)).should(Condition.text(notification));
    }
}
