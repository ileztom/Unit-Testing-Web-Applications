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

public class Test4 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws InterruptedException {
        // Настройка WebDriverManager для автоматической загрузки драйвера
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Установка неявного ожидания
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Явное ожидание
        driver.get("https://demo.opencart.com/en-gb?route=common/home"); // Открываем главную страницу сайта
        Thread.sleep(1500);
    }

    @Test
    public void testRegistration() {
        try {
            // Наводим курсор на пункт меню "My Account"
            WebElement myAccountMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"top\"]/div/div[2]/ul/li[2]/div/a/span")));
            myAccountMenu.click();
            Thread.sleep(1500);

            // Ожидаем появления выпадающего меню и кликаем на "Register"
            WebElement registerOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"top\"]/div/div[2]/ul/li[2]/div/ul/li[1]/a")));
            registerOption.click();
            Thread.sleep(1500);

            // Заполняем поля регистрации
            WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"input-firstname\"]")));
            firstNameField.sendKeys("Ахмед");
            Thread.sleep(1500);

            WebElement lastNameField = driver.findElement(By.xpath("//*[@id=\"input-lastname\"]"));
            lastNameField.sendKeys("Качок");
            Thread.sleep(1500);

            WebElement emailField = driver.findElement(By.xpath("//*[@id=\"input-email\"]"));
            emailField.sendKeys("ahmedsigma@sigma.ru");
            Thread.sleep(1500);

            WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"input-password\"]"));
            passwordField.sendKeys("88005553535");

            // Соглашаемся с политикой конфиденциальности
            WebElement privacyPolicyCheckbox = driver.findElement(By.xpath("//*[@id=\"form-register\"]/div/div/input"));
            privacyPolicyCheckbox.click();
            Thread.sleep(1500);

            // Нажимаем кнопку "Continue" для завершения регистрации
            WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"form-register\"]/div/button"));
            continueButton.click();

            // Проверяем, что регистрация прошла успешно
            // Ожидаем появления сообщения об успешной регистрации
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/h1")));
            String messageText = successMessage.getText();

            assertTrue("Тест не пройден: регистрация не удалась.", messageText.equals("Your Account Has Been Created!"));

            System.out.println("Тест пройден: регистрация успешно завершена.");
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