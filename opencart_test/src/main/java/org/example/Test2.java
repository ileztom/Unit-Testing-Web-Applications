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

public class Test2 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws InterruptedException {
        // Настройка WebDriverManager для автоматической загрузки драйвера
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Установка неявного ожидания
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Явное ожидание
        driver.get("https://demo.opencart.com/en-gb?route=common/home");
        Thread.sleep(5000);
    }

    @Test
    public void testChangeCurrency() throws InterruptedException {
        Thread.sleep(1500);
        // Открыть меню выбора валюты
        WebElement currencyMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form-currency']/div/a/span")));
        currencyMenu.click();

        Thread.sleep(1500);
        // Выбрать евро
        WebElement euroOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form-currency']/div/ul/li[1]/a")));
        euroOption.click();

        // Проверка, что валюта изменилась на евро
        WebElement currencyDisplay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form-currency']/div/a/span")));
        String currentCurrency = currencyDisplay.getText();
        System.out.println("Текущая валюта: " + currentCurrency);
        assert currentCurrency.equals("€ Euro") : "Валюта не изменилась на евро!";

        Thread.sleep(1500);
        // Открыть меню выбора валюты снова
        currencyMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form-currency']/div/a/span")));
        currencyMenu.click();

        Thread.sleep(1500);
        // Выбрать доллар
        WebElement dollarOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form-currency']/div/ul/li[3]/a")));
        dollarOption.click();

        // Проверка, что валюта изменилась на доллар
        currencyDisplay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form-currency']/div/a/span")));
        currentCurrency = currencyDisplay.getText();
        System.out.println("Текущая валюта: " + currentCurrency);
        assert currentCurrency.equals("$ US Dollar") : "Валюта не изменилась на доллар!";
    }

    @After
    public void tearDown() {
        // Закрыть драйвер
        if (driver != null) {
            driver.quit();
        }
    }
}
