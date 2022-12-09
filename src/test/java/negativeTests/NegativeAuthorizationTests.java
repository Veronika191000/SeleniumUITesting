package negativeTests;

import baseTest.BaseTests;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.BoardPage;
import page.LoginPage;
import page.MainPage;
import utils.ConfigProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.ConfigProvider.LIST_TITLE;
import static utils.StringUtils.generateRandomValue;

public class NegativeAuthorizationTests extends BaseTests {

    @Test
    @Description("Validate that user can not Log in to the Trello with incorrect email")
    public void validateUserCanNotLoginWithIncorrectEmail() {
        String incorrectLogin = ConfigProvider.INCORRECT_ADMIN_LOGIN;
        String password = ConfigProvider.ADMIN_PASSWORD;
        LoginPage loginPage = new LoginPage();
        loginPage.logIn(incorrectLogin, password);
        String errorEmailMessage = "There isn't an account for this email";
        assertThat(loginPage.errorMessageContainsCorrectText(errorEmailMessage)).isEqualTo(true);
    }

    @Test
    @Description("Validate that user can not Log in to the Trello with incorrect password")
    public void validateUserCanNotLoginWithIncorrectPassword() {
        String incorrectLogin = ConfigProvider.ADMIN_LOGIN;
        String password = ConfigProvider.INCORRECT_ADMIN_PASSWORD;
        LoginPage loginPage = new LoginPage();
        loginPage.logIn(incorrectLogin, password);
        String errorPasswordMessage = "Incorrect email address and / or password.";
        assertThat(loginPage.errorMessageContainsCorrectText(errorPasswordMessage)).isEqualTo(true);
    }
}
