package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class Test5 {
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
    public void testSearchFunctionality() {
        try {
            // Находим поле ввода поиска и вводим "IPhone"
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"search\"]/input")));
            searchInput.sendKeys("IPhone");
            Thread.sleep(1500);

            // Нажимаем на кнопку поиска
            WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"search\"]/button"));
            searchButton.click();

            // Ожидаем, пока результаты поиска загрузятся
            // Предполагается, что на странице результатов поиска есть элемент с определенным текстом или классом
            // Здесь мы проверяем, что результаты поиска содержат слово "IPhone"
            // Например, можно проверить наличие заголовка или списка продуктов

            // В данном случае предположим, что заголовок страницы изменился на "Search - IPhone"
            WebElement searchTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/h1")));
            String searchTitleText = searchTitle.getText();

            // Проверяем, что заголовок содержит "IPhone"
            assertTrue("Тест не пройден: результаты поиска не содержат 'IPhone'.", searchTitleText.contains("IPhone"));

            System.out.println("Тест пройден: поиск по 'IPhone' работает.");
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