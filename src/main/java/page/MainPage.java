package page;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static utils.ElementUtils.clickOnElementJsExecutor;

public class MainPage extends BasePage {

    @FindBy(xpath = "//button[@data-testid='header-create-menu-button']")
    WebElement createMenuButton;

    @FindBy(xpath = "//button[@data-testid='header-create-board-button']")
    WebElement createBoardButton;

    @FindBy(xpath = "//input[@data-testid='create-board-title-input']")
    WebElement boardTitle;

    @FindBy(xpath = "//button[@data-testid='create-board-submit-button']")
    WebElement submitBoardButton;

    @FindBy(xpath = "//a[@aria-label='Back to home']")
    WebElement backToHomeButton;

    @FindBy(xpath = "//h3[text()='YOUR WORKSPACES']")
    WebElement workspaceSection;

    @FindBy(xpath = "//h3[text()='YOUR WORKSPACES']//following-sibling::div//ul/li")
    List<WebElement> boards;

    @FindBy(xpath = "//input[@placeholder='Search'] | //input[@placeholder='Search Trello']")
    WebElement searchField;

    //   @FindBy(xpath = "//div[@data-test-id='search-dialog-dialog-wrapper’]//span[text()='New Board 104']")
    @FindBy(xpath = "//div[@data-test-id=\"search-dialog-dialog-wrapper\"]//*[text()=\"New Board 104\"]")
    WebElement searchBoardResult;

    public MainPage() {
        PageFactory.initElements(driver, this);
    }

    @Step("Create board with name: {boardName}")
    public MainPage createBoard(String boardName) {
        createMenuButton.click();
        wait.until(ExpectedConditions.visibilityOf(createBoardButton));
        createBoardButton.click();
        wait.until(ExpectedConditions.visibilityOf(boardTitle));
        boardTitle.sendKeys(boardName);
        return this;
    }

    @Step("Search for board with name: {board}")
    public BoardPage searchForBoard(String board) {
        wait.until(ExpectedConditions.elementToBeClickable(searchField));
        clickOnElementJsExecutor(searchField, driver);
        searchField.sendKeys(board, Keys.ENTER);
        String boardPath = String.format("//div[@data-test-id=\"search-dialog-dialog-wrapper\"]//*[text()=\"%s\"]", board);
        driver.findElement(By.xpath(boardPath)).click();
        return new BoardPage();
    }

    @Step("validate that main page is loaded")
    public boolean validateMainPageIsLoaded() {
        return backToHomeButton.isDisplayed()
                && workspaceSection.isDisplayed()
                && searchField.isDisplayed();
    }

    @Step("Submit board")
    public BoardPage submitBoard() {
        wait.until(ExpectedConditions.elementToBeClickable(submitBoardButton));
        submitBoardButton.click();
        return new BoardPage();
    }

    @Step("Validate submit board button is disabled")
    public boolean validateSubmitBoardButtonIsDisabled() {
        return !submitBoardButton.isEnabled();
    }

    public MainPage backToHomePage() {
        wait.until(ExpectedConditions.elementToBeClickable(backToHomeButton));
        clickOnElementJsExecutor(backToHomeButton, driver);
        return new MainPage();
    }

/*    public boolean workspaceContainsBoard() {
        return boards.stream().anyMatch(board -> board.getText().equals(boardTitle));
    }*/
}
