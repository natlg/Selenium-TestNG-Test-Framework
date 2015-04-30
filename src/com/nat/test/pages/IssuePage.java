package com.nat.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IssuePage extends RepositoryAbstractPage {

	@FindBy(id = "issue_title")
	private WebElement title;

	@FindBy(id = "issue_body")
	private WebElement comment;

	@FindBy(xpath = "//button[contains(text(), 'Submit new issue')]")
	private WebElement submitNewIssueBtn;

	@FindBy(xpath = "//time[contains(text(), 'just now')]")
	private WebElement timeJustAdded;

	@FindBy(xpath = "//div[contains(@class, 'state-open')]")
	private WebElement statusOpen;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public IssuePage(WebDriver driver) {
		super(driver);
	}

	public boolean areNewIssueElementsPresent() {
		return isElementPresents(title) && isElementPresents(comment)
				&& isElementPresents(submitNewIssueBtn);
	}

	public IssuePage addIssue(String titleStr, String commentStr) {
		title.clear();
		title.sendKeys(titleStr);
		comment.clear();
		comment.sendKeys(commentStr);
		submitNewIssueBtn.click();
		return this;

	}

	public boolean IsIssueJustAdded() {
		System.out.println("status " + statusOpen.getText());
		return isElementPresents(timeJustAdded)
				&& isElementPresents(statusOpen);
	}

	/**
	 * Method to get expected page title
	 *
	 * @return expected page title
	 */
	@Override
	public String getExpectedTitle() {
		return "Issue";
	}
}
