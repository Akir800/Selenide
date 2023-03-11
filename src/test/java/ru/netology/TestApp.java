package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class TestApp {

    public String addDays(int days) throws ParseException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(gregorianCalendar.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, days);
        return sdf.format(c.getTime());
    }

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1000x800";
        open("http://localhost:9999");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
    }

    @Test
    void testOne() throws ParseException {
        String date = addDays(20);

        $x("//input[@placeholder='Город']").setValue("Калининград");
        $x("//input[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Владимир Малков-Бельский");
        $x("//input[@name='phone']").setValue("+79261549122");
        $x("//span[@class='checkbox__box']").click();
        $x("//span[@class='button__text']").click();

        String notification = "Встреча успешно забронирована на " + date;
        $x("//div[@class='notification__content']").should(appear, Duration.ofSeconds(15))
                .should(Condition.text(notification));
    }
}