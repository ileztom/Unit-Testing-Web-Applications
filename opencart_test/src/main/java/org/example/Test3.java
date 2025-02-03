package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class Test3 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        // Настройка WebDriverManager для автоматической загрузки драйвера
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Установка неявного ожидания
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Явное ожидание
        driver.get("https://demo.opencart.com/en-gb?route=common/home"); // Открываем главную страницу сайта
    }

    @Test
    public void testEmptyPCCategory() {
        try {
            // Наводим курсор на пункт меню "Desktops"
            WebElement desktopsMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"narbar-menu\"]/ul/li[1]/a")));
            Actions actions = new Actions(driver);
            actions.moveToElement(desktopsMenu).perform();
            Thread.sleep(1500);

            // Ожидаем появления выпадающего меню и кликаем на "PC"
            WebElement pcOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"narbar-menu\"]/ul/li[1]/div/div/ul/li[1]/a")));
            pcOption.click();
            Thread.sleep(1500);

            // Проверяем, что страница пустая, то есть нет товаров
            // Для этого проверяем наличие кнопки "Continue"
            boolean isContinueButtonPresent = false;
            try {
                WebElement continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div[2]/a")));
                isContinueButtonPresent = continueButton.isDisplayed();
            } catch (Exception e) {
                // Если кнопка "Continue" не найдена, то ничего не делаем
            }

            // Если кнопка "Continue" присутствует, то нажимаем на неё
            if (isContinueButtonPresent) {
                WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/a"));
                continueButton.click();
                System.out.println("Тест пройден: страница пустая, кнопка 'Continue' нажата.");
            } else {
                // Если кнопка "Continue" отсутствует, то тест не пройден
                assertTrue("Тест не пройден: страница не пустая, товары присутствуют.", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("Тест не пройден из-за ошибки: " + e.getMessage(), false);
        }
    }

    @After
    public void tearDown() {
        // Закрыть драйвер
        if (driver != null) {
            driver.quit();
        }
    }
}