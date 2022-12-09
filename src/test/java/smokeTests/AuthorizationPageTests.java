package smokeTests;

import baseTest.BaseTests;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import page.LoginPage;
import page.MainPage;
import utils.ConfigProvider;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorizationPageTests extends BaseTests {

    @Test
    @Description("Validate that user can be successfully Log in to the Trello")
    public void validateUserCanLogIn() {
        String login = ConfigProvider.ADMIN_LOGIN;
        String password = ConfigProvider.ADMIN_PASSWORD;
        LoginPage loginPage = new LoginPage();
        MainPage mainPage = loginPage.logIn(login, password);
        assertThat(mainPage.validateMainPageIsLoaded()).isTrue();
    }
}
