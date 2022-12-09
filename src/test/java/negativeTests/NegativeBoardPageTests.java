package negativeTests;

import baseTest.BaseTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.BoardPage;
import page.LoginPage;
import page.MainPage;
import utils.ConfigProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.ConfigProvider.LIST_TITLE;
import static utils.StringUtils.generateRandomValue;

public class NegativeBoardPageTests extends BaseTests {
    protected MainPage mainPage;

    @BeforeMethod
    public void initClass() {
        LoginPage loginPage = new LoginPage();
        mainPage = loginPage.logIn(ConfigProvider.ADMIN_LOGIN, ConfigProvider.ADMIN_PASSWORD);
    }

    @Test
    public void validateBoardCanNotBeCreatedWithEmptyBoard() {
        mainPage.createBoard("");
        assertThat(mainPage.validateSubmitBoardButtonIsDisabled()).isTrue();
    }

    @Test
    public void validateListCanNotBeCreatedWithEmptyTitleWithinBoard() {
        String boardTitle = ConfigProvider.BOARD_TITLE + generateRandomValue();
        BoardPage boardPage = mainPage.createBoard(boardTitle)
                .submitBoard();
        assertThat(boardPage.validateBoardsPageIsLoaded()).isTrue();
        int currentListCount = boardPage.getListCount();
        boardPage.createList("");
        assertThat(boardPage.getListCount()).isEqualTo(currentListCount);
    }

    @Test
    public void validateCardCanNotBeCreatedWithEmptyName() {
        String boardTitle = ConfigProvider.BOARD_TITLE + generateRandomValue();
        BoardPage boardPage = mainPage.createBoard(boardTitle)
                .submitBoard();
        assertThat(boardPage.validateBoardsPageIsLoaded()).isTrue();
        boardPage.createList(LIST_TITLE);
        assertThat(boardPage.validateListNameIsPresent(LIST_TITLE)).isTrue();
        int currentCardCount = boardPage.getCardCount(LIST_TITLE);
        boardPage.createCard(LIST_TITLE, "");
        assertThat(boardPage.getCardCount(LIST_TITLE)).isEqualTo(currentCardCount);
    }
}
