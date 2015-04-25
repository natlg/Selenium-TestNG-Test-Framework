package com.nat.test.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IssuesSectionPage extends RepositoryAbstractPage {

	@FindBy(xpath = "//h3[text()='Welcome to Issues!']")
	private WebElement welcomeMessage;

	@FindBy(xpath = "//a[contains(text(), 'New issue')][contains(@class, 'btn-primary')]")
	private WebElement btnAddIssue;

	@FindBy(xpath = "//button[contains(text(), 'Filters')]")
	private WebElement filters;

	@FindBy(id = "js-issues-search")
	private WebElement searchField;

	@FindBy(xpath = "//ul[contains(@class, 'table-list-issues')]/li/div[2]/a")
	private List<WebElement> issuesList;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public IssuesSectionPage(WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!driver.getCurrentUrl().contains("issues")) {
			throw new IllegalStateException(
					"This is not the Issues page, this is " + driver.getTitle());
		}
	}

	public boolean areAllElementsPresent() {
		return isElementPresents(btnAddIssue) && isElementPresents(filters)
				&& isElementPresents(searchField);
	}

	public boolean isWelcomeMessagePresent() {
		return isElementPresents(welcomeMessage);
	}

	public IssuePage addNewIssue() {
		btnAddIssue.click();
		return PageFactory.initElements(driver, IssuePage.class);
	}

	public boolean isIssuePresent(String title) {
		for (WebElement issue : issuesList) {
			if (issue.getText().equalsIgnoreCase(title)) {
				return true;
			}
		}
		return false;
	}
}
