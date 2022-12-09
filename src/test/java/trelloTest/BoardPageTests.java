package trelloTest;

import baseTest.BaseTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.BoardPage;
import page.LoginPage;
import page.MainPage;
import utils.ConfigProvider;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.ConfigProvider.*;
import static utils.StringUtils.generateRandomValue;

public class BoardPageTests extends BaseTests {
    protected MainPage mainPage;

    @BeforeMethod
    public void initClass() {
        LoginPage loginPage = new LoginPage();
        mainPage = loginPage.logIn(ConfigProvider.ADMIN_LOGIN, ConfigProvider.ADMIN_PASSWORD);
    }

    @Test
    public void validateBoardCanBeCreated() {
        String boardTitle = ConfigProvider.BOARD_TITLE + generateRandomValue();
        BoardPage boardPage = mainPage.createBoard(boardTitle)
                .submitBoard();
        assertThat(boardPage.validateBoardTitle(boardTitle)).isTrue();
    }

    @Test
    public void validateBoardCanNotBeCreatedWithEmptyBoard() {
        mainPage.createBoard("");
        assertThat(mainPage.validateSubmitBoardButtonIsDisabled()).isTrue();
    }

    @Test
    public void validateBoardCanBeFound() {
        String boardTitle = ConfigProvider.BOARD_TITLE + generateRandomValue();
        mainPage.createBoard(boardTitle)
                .submitBoard()
                .backToHomePage();
        assertThat(mainPage.validateMainPageIsLoaded()).isTrue();
        assertThat(mainPage.searchForBoard(boardTitle)
                .validateBoardTitle(boardTitle)).isTrue();
    }

    @Test
    public void validateListCanBeCreatedWithinBoard() {
        String boardTitle = ConfigProvider.BOARD_TITLE + generateRandomValue();
        BoardPage boardPage = mainPage.createBoard(boardTitle)
                .submitBoard();
        assertThat(boardPage.validateBoardsPageIsLoaded()).isTrue();
        boardPage.createList(LIST_TITLE);
        boardPage.validateListNameIsPresent(LIST_TITLE);
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
    public void validateCardCanBeCreatedWithinList() throws InterruptedException {
        String boardTitle = ConfigProvider.BOARD_TITLE + generateRandomValue();
        BoardPage boardPage = mainPage.createBoard(boardTitle)
                .submitBoard();
        assertThat(boardPage.validateBoardsPageIsLoaded()).isTrue();
        boardPage.createList(LIST_TITLE);
        assertThat(boardPage.validateListNameIsPresent(LIST_TITLE)).isTrue();
        boardPage.createCard(LIST_TITLE, CARD_TITLE);
        boardPage.validateCardIsPresent(LIST_TITLE, CARD_TITLE);
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

    @Test
    public void validateCardCanContainDescription() {
        BoardPage boardPage = cardCreation(ConfigProvider.BOARD_TITLE + generateRandomValue(), LIST_TITLE, CARD_TITLE);
        boardPage.openCardByName(LIST_TITLE, CARD_TITLE)
                .addDescriptionToTheCard(CARD_DESCRIPTION);

        assertThat(boardPage.validateDescriptionTextIsCorrect(CARD_DESCRIPTION)).isTrue();
        boardPage.closeCardDialog();
    }

    @Test
    public void validateCardCanContainChecklist() {
        BoardPage boardPage = cardCreation(ConfigProvider.BOARD_TITLE + generateRandomValue(), LIST_TITLE, CARD_TITLE);
        boardPage.openCardByName(LIST_TITLE, CARD_TITLE)
                .addChecklistToTheCard(CARD_CHECKLIST_NAME);
        assertThat(boardPage.validateCheckListTitle(CARD_CHECKLIST_NAME)).isTrue();
        List<String> checklistItems = Arrays.asList(CARD_CHECKLIST_FIRST_ITEM, CARD_CHECKLIST_SECOND_ITEM, CARD_CHECKLIST_THIRD_ITEM);
        boardPage.addCheckListItems(checklistItems);
        assertThat(boardPage.validateCheckListItemsArePresent(checklistItems)).isTrue();
        boardPage.closeCardDialog();
    }

    @Test
    public void validateCardCanContainComment() {
        BoardPage boardPage = cardCreation(ConfigProvider.BOARD_TITLE + generateRandomValue(), LIST_TITLE, CARD_TITLE);
        boardPage.openCardByName(LIST_TITLE, CARD_TITLE)
                .addCommentsToTheCard(CARD_COMMENT);
        assertThat(boardPage.validateCommentIsPresent(CARD_COMMENT));
        boardPage.closeCardDialog();
    }

    @Test
    public void validateCardCanContainLabel() {
        BoardPage boardPage = cardCreation(ConfigProvider.BOARD_TITLE + generateRandomValue(), LIST_TITLE, CARD_TITLE);
        boardPage.openCardByName(LIST_TITLE, CARD_TITLE)
                .addLabelToTheCard(CARD_LABEL)
                .closeCardDialog();
        boardPage.openCardByName(LIST_TITLE, CARD_TITLE);
        assertThat(boardPage.validateLabelIsPresent(CARD_LABEL));
        boardPage.closeCardDialog();
    }

    public BoardPage cardCreation(String boardTitle, String listTitle, String cardTitle) {
        BoardPage boardPage = mainPage.createBoard(boardTitle)
                .submitBoard();
        assertThat(boardPage.validateBoardsPageIsLoaded()).isTrue();
        boardPage.createList(listTitle);
        assertThat(boardPage.validateListNameIsPresent(listTitle)).isTrue();
        boardPage.createCard(listTitle, cardTitle);
        assertThat(boardPage.validateCardIsPresent(listTitle, cardTitle)).isTrue();
        return new BoardPage();
    }
}
