package com.nat.test.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Class represents Home page that opens after successful login
 */
public class HomePage extends Page {

	@FindBy(name = "q")
	private WebElement search;

	@FindBy(xpath = "//*[contains(text(),'Welcome to GitHub!')]")
	private WebElement welcomeMessage;

	@FindBy(id = "user-links")
	private WebElement userLinks;

	@FindBy(id = "your_repos")
	private WebElement repositories;

	@FindBy(xpath = "//a[contains(@class, 'new-repo')]")
	private WebElement newRepoButton;

	@FindBy(xpath = "//div[contains(text(), 'was successfully deleted')]")
	private WebElement successDeletingMessage;

	@FindBy(xpath = "//span[@class='repo']")
	private List<WebElement> repoList;

	@FindBy(xpath = "//a[@href='/notifications']")
	private WebElement notificationsIcon;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public HomePage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Checks if the search field presents on the page
	 *
	 * @return true if search field presents on the page
	 */
	public boolean isSearchPresents() {
		return isElementPresents(search);
	}

	/**
	 * Search on the site
	 *
	 * @param query
	 *            Search query
	 * @return An instance of {@link SearchPage} class with search result
	 */
	public SearchPage search(String query) {
		search.clear();
		search.sendKeys(query);
		search.submit();
		return PageFactory.initElements(driver, SearchPage.class);
	}

	/**
	 * Checks that login was successful
	 *
	 * @return true if welcome message, user links and logout icon presents on
	 *         the page
	 */
	public boolean isLoginSuccessed() {
		return isElementPresents(welcomeMessage)
				&& isElementPresents(userLinks)
				&& isElementPresents(repositories)
				&& isElementPresents(logoutNewDesign);
	}

	/**
	 * Checks that button for creating new repository presents on the page
	 *
	 * @return true if button for creating new repository presents on the page
	 */
	public boolean isNewRepoButtonPresents() {
		return isElementPresents(newRepoButton);
	}

	/**
	 * Clicks the button for creating new repository
	 *
	 * @return An instance of {@link CreateRepositoryPage} class
	 */
	public CreateRepositoryPage createNewRepository() {
		newRepoButton.click();
		return PageFactory.initElements(driver, CreateRepositoryPage.class);
	}

	/**
	 * Check that repository was just successfully deleted
	 *
	 * @param repName
	 *            The name of deleted repository
	 * @return true is message about successful deleting presents and if deleted
	 *         repository is not presented in the list of existing repositories
	 */
	public boolean isRepositoryJustDeleted(String repName) {
		boolean oldRepoDeleted = true;
		for (WebElement element : repoList) {
			if (element.getText().equals(repName)) {
				oldRepoDeleted = false;
			}
		}
		return isElementPresents(successDeletingMessage) && oldRepoDeleted;
	}

	/**
	 * Checks if notifications icon presents on the page
	 *
	 * @return True if notifications icon presents on the page
	 */
	public boolean isNotificationsIconPresents() {
		return isElementPresents(notificationsIcon);
	}

	/**
	 * Navigates to the Notifications page
	 *
	 * @return An instance of {@link NotificationsPage} class
	 */
	public NotificationsPage seeNotifications() {
		notificationsIcon.click();
		return PageFactory.initElements(driver, NotificationsPage.class);
	}

	/**
	 * Method to get expected page title
	 *
	 * @return expected page title
	 */
	@Override
	public String getExpectedTitle() {
		return "GitHub";
	}

}
