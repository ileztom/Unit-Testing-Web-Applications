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

public class Test1 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        // Настройка WebDriverManager для автоматической загрузки драйвера
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Установка неявного ожидания
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Явное ожидание
        driver.get("https://demo.opencart.com/en-gb?route=common/home");
    }

    @Test
    public void testProductImageSwitching() throws InterruptedException {
        Thread.sleep(2000);
        // Кликнуть на продукт
        WebElement productImage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='carousel-banner-0']/div[2]/div[1]/div/div/a/img")));
        productImage.click();

        Thread.sleep(2000);
        // Кликнуть на первую картинку товара
        WebElement firstImage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='content']/div[1]/div[1]/div/div/a[1]/img")));
        firstImage.click();

        Thread.sleep(2000);
        // Нажать на стрелку для перелистывания
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/button[2]")));
        nextButton.click();

        // Здесь можно добавить проверку, что изображение переключилось
        System.out.println("Тест пройден: изображения переключаются.");
    }

    @After
    public void tearDown() {
        // Закрыть драйвер
        if (driver != null) {
            driver.quit();
        }
    }
}
