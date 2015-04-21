package com.nat.test.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

	public HomePage(WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!"GitHub".equals(driver.getTitle())) {
			throw new IllegalStateException(
					"This is not the home page, this is " + driver.getTitle());
		}
	}

	public boolean isSearchPresents() {
		return isElementPresents(search);
	}

	public SearchPage search(String query) {
		search.clear();
		search.sendKeys(query);
		search.submit();

		return PageFactory.initElements(driver, SearchPage.class);
	}

	public boolean isLoginSuccessed() {
		return isElementPresents(welcomeMessage)
				&& isElementPresents(userLinks)
				&& isElementPresents(repositories) && isElementPresents(logout);
	}

	public boolean isNewRepoButtonPresents() {
		return isElementPresents(newRepoButton);
	}

	public CreateRepositoryPage createNewRepository() {
		newRepoButton.click();
		return PageFactory.initElements(driver, CreateRepositoryPage.class);
	}

	public boolean isRepositoryJustDeleted(String repName) {
		boolean oldRepoDeleted = true;
		for (WebElement element : repoList) {
			if (element.getText().equals(repName)) {
				oldRepoDeleted = false;
			}
		}
		return isElementPresents(successDeletingMessage) && oldRepoDeleted;
	}

	public boolean isNotificationsIconPresents() {
		return isElementPresents(notificationsIcon);
	}

	public NotificationsPage seeNotifications() {
		notificationsIcon.click();
		return PageFactory.initElements(driver, NotificationsPage.class);
	}
}
