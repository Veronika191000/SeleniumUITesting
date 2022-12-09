package page;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ConfigProvider;

public class LoginPage extends BasePage {
    @FindBy(id = "user")
    private WebElement loginField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement continueButton;

    @FindBy(xpath = "//input[@value='Log in'] | //button[@id='login-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[contains(@class, 'Buttonsstyles')]//a[text()='Log in']")
    private WebElement logInButton;

    @FindBy(xpath = "//div[@id='error']/p[@class='error-message'] | //div[@id='login-error']")
    private WebElement errorMessage;

    public LoginPage() {
        driver.get(ConfigProvider.URL);
        PageFactory.initElements(driver, this);
    }

    @Step("Login to the main page with name: {userNameValue} and password: {userPasswordValue}")
    public MainPage logIn(String userNameValue, String userPasswordValue) {
        logInButton.click();
        loginField.sendKeys(userNameValue);
        continueButton.click();
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.sendKeys(userPasswordValue);
        submitButton.click();
        return new MainPage();
    }

    @Step("Validate that error is present: {errorText}")
    public boolean errorMessageContainsCorrectText(String errorText) {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText().contains(errorText);
    }
}
