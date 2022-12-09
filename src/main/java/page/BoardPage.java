package page;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

import static utils.ElementUtils.clickOnElementJsExecutor;
import static utils.ElementUtils.waitForElement;

public class BoardPage extends BasePage {

    public final String PATH_TO_LIST = "//textarea[contains(text(),'%s')]";
    public final String PATH_TO_CARDS = PATH_TO_LIST + "//parent::div/following-sibling::div/a[contains(@class,'list-card')]";
    public final String PATH_TO_CREATE_CARD = PATH_TO_LIST + "//parent::div/following-sibling::div[contains(@class,'card-composer')]/a";
    public final String PATH_TO_CARD_NAME_FIELD = PATH_TO_LIST + "//parent::div/following-sibling::div//textarea[contains(@class,'list-card')]";
    public final String PATH_TO_SUBMIT_CARD = PATH_TO_LIST + "//parent::div/following-sibling::div//input[contains(@class,'nch-button')]";
    public final String PATH_TO_CARD = PATH_TO_LIST + "//parent::div/following-sibling::div/a//span[text()='%s']";

    public final String PATH_TO_CARD_FUNCTIONS = "//h3[contains(text(), 'Add to card')]/parent::div//following-sibling::div/a[@title='%s']";

    public BoardPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[contains(@class,'board-header')]")
    WebElement boardTitle;

    @FindBy(xpath = "//a[@aria-label='Back to home']")
    WebElement backToHomeButton;

    @FindBy(xpath = "//a[contains(@class, 'open-add-list')]")
    WebElement createList;

    @FindBy(xpath = "//input[@class='list-name-input']")
    WebElement inputListNameField;

    @FindBy(xpath = "//input[contains(@class, 'add-button')]")
    WebElement addList;

    @FindBy(xpath = "//textarea[contains(@class,'list-header-name')]")
    List<WebElement> listHeaderName;

    @FindBy(xpath = "//textarea[contains(@class,'card-description')]")
    WebElement descriptionField;

    @FindBy(css = "div.js-desc")
    WebElement descriptionText;

    @FindBy(xpath = "//div[contains(@class,'description-edit')]//input[contains(@class, 'save-edit')]")
    WebElement saveEditsButtons;

    @FindBy(xpath = "//input[@id='id-checklist']")
    WebElement checklistTitleField;

    @FindBy(xpath = "//input[contains(@class, 'add-checklist')]")
    WebElement addChecklistButton;

    @FindBy(xpath = "//div[contains(@class,'checklist-title')]/h3")
    WebElement checkListTitle;

    @FindBy(xpath = "//textarea[@placeholder='Add an item']")
    WebElement addChecklistItemField;

    @FindBy(css = "input.js-add-checklist-item")
    WebElement addChecklistItem;

    @FindBy(css = "span.js-checkitem-name")
    List<WebElement> checklistItems;

    //comments
    @FindBy(css = "textarea.comment-box-input")
    WebElement addCommentField;

    @FindBy(css = "input.js-add-comment")
    WebElement addCommentButton;

    @FindBy(xpath = "//div[@class='window-module']//div[@class='comment-container']//div[contains(@class,'current-comment')]/p")
    List<WebElement> comments;

    //labels
    @FindBy(xpath = "//input[@placeholder='Search labels…']")
    WebElement labelsInputSearch;

    @FindBy(xpath = "//button[@aria-label='Close popover']")
    WebElement closePopUp;

    @FindBy(xpath = "//h3[text()='Labels']/parent::div//button[contains(@aria-label,'Color')]")
    List<WebElement> labels;

    @FindBy(xpath = "//section[@data-testid='labels-popover-labels-screen']//ul/li//div[@role='menuitem']/parent::div")
    List<WebElement> labelsToPickUp;

    @FindBy(xpath = "//a[@aria-label='Close dialog']")
    WebElement closeCardDialog;

    @Step("Validate board title")
    public boolean validateBoardTitle(String board) {
        return boardTitle.getText().equals(board);
    }

    @Step("Create list: {listName} within the board with name")
    public BoardPage createList(String listName) {
        wait.until(ExpectedConditions.visibilityOf(inputListNameField));
        inputListNameField.sendKeys(listName);
        addList.click();
        return this;
    }

    @Step("Validate list is present on the board")
    public boolean validateListNameIsPresent(String listName) {
        return listHeaderName.stream().anyMatch(list -> list.getText().equals(listName));
    }

    @Step("Get number of the lists within the board")
    public int getListCount() {
        return listHeaderName.size();
    }

    public MainPage backToHomePage() {
        wait.until(ExpectedConditions.elementToBeClickable(backToHomeButton));
        clickOnElementJsExecutor(backToHomeButton, driver);
        return new MainPage();
    }

    @Step("Validate that board page is loaded")
    public boolean validateBoardsPageIsLoaded() {
        waitForElement(2000);
        return backToHomeButton.isDisplayed()
                && boardTitle.isDisplayed();
    }

    @Step("Create card: {cardName} within the list {listName}")
    public BoardPage createCard(String listName, String cardName) {
        String pathToCreateCardButton = String.format(PATH_TO_CREATE_CARD, listName);
        driver.findElement(By.xpath(pathToCreateCardButton)).click();
        String pathToCardNameField = String.format(PATH_TO_CARD_NAME_FIELD, listName);
        driver.findElement(By.xpath(pathToCardNameField)).sendKeys(cardName);
        String pathToSubmitCard = String.format(PATH_TO_SUBMIT_CARD, listName);
        driver.findElement(By.xpath(pathToSubmitCard)).click();
        return this;
    }

    @Step("Validate card: {cardName} is present within the list: {listName}")
    public boolean validateCardIsPresent(String listName, String cardName) {
        String pathToCards = String.format(PATH_TO_CARDS, listName);
        List<WebElement> cards = driver.findElements(By.xpath(pathToCards));
        return cards.stream().anyMatch(card -> card.getText().equals(cardName));
    }

    @Step("Get card count within the list: {listName}")
    public int getCardCount(String listName) {
        waitForElement(1000);
        String pathToCards = String.format(PATH_TO_CARDS, listName);
        List<WebElement> cards = driver.findElements(By.xpath(pathToCards));
        return cards.size();
    }

    @Step("Get card by name: {cardName} within the list: {listName}")
    public WebElement getCardByName(String listName, String cardName) {
        String pathToCard = String.format(PATH_TO_CARD, listName, cardName);
        return driver.findElement(By.xpath(pathToCard));
    }

    @Step("Open card by name: {cardName} within the list: {listName}")
    public BoardPage openCardByName(String listName, String cardName) {
        getCardByName(listName, cardName).click();
        wait.until(ExpectedConditions.visibilityOf(descriptionField));
        return this;
    }

    @Step("Add description to the card")
    public BoardPage addDescriptionToTheCard(String description) {
        descriptionField.sendKeys(description);
        saveEditsButtons.click();
        return this;
    }

    @Step("Add checklist: {checkListName} to the card")
    public BoardPage addChecklistToTheCard(String checkListName) {
        String pathToAddingChecklist = String.format(PATH_TO_CARD_FUNCTIONS, "Checklist");
        driver.findElement(By.xpath(pathToAddingChecklist)).click();
        wait.until(ExpectedConditions.visibilityOf(checklistTitleField));
        checklistTitleField.sendKeys(checkListName);
        addChecklistButton.click();
        return this;
    }

    @Step("Validate checklist title")
    public boolean validateCheckListTitle(String checkListName) {
        waitForElement(1000);
        return checkListTitle.getText().equals(checkListName);
    }

    @Step("Validate description text is correct: {description}")
    public boolean validateDescriptionTextIsCorrect(String description) {
        return descriptionText.getText().equals(description);
    }

    @Step("Add items to the checklist")
    public BoardPage addCheckListItems(List<String> checkListItems) {
        for (int i = 0; i < checkListItems.size(); i++) {
            wait.until(ExpectedConditions.visibilityOf(addChecklistItemField));
            addChecklistItemField.sendKeys(checkListItems.get(i));
            clickOnElementJsExecutor(addChecklistItem, driver);
        }
        return this;
    }

    @Step("Validate that checklist items are present")
    public boolean validateCheckListItemsArePresent(List<String> checkListItems) {
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < checkListItems.size(); i++) {
            result.add(checklistItems.get(i).getText().equals(checkListItems.get(i)));
        }
        return result.stream().allMatch(res -> res.equals(true));
    }

    @Step("Add comment to the card: {commentText}")
    public BoardPage addCommentsToTheCard(String commentText) {
        addCommentField.sendKeys(commentText);
        addCommentButton.click();
        waitForElement(1000);
        return this;
    }

    @Step("Validate comment is present")
    public boolean validateCommentIsPresent(String commentText) {
        return comments.stream().anyMatch(comment -> comment.getText().equals(commentText));
    }

    @Step("Add label to the card: {labelColor}")
    public BoardPage addLabelToTheCard(String labelColor) {
        String pathToAddingLabel = String.format(PATH_TO_CARD_FUNCTIONS, "Labels");
        driver.findElement(By.xpath(pathToAddingLabel)).click();
        waitForElement(2000);
        wait.until(ExpectedConditions.visibilityOf(labelsInputSearch));
        labelsInputSearch.sendKeys(labelColor);
        waitForElement(1000);
        Actions action = new Actions(driver);
        action.doubleClick(labelsToPickUp.get(0)).perform();
        closePopUp.click();
        return this;
    }

    @Step("Validate {labelColor} label is present")
    public boolean validateLabelIsPresent(String labelColor) {
        return labels.stream().anyMatch(label -> label.getAttribute("data-color").equals(labelColor));
    }

    @Step("Close card dialog")
    public BoardPage closeCardDialog() {
        closeCardDialog.click();
        return this;
    }
}
