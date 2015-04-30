package com.nat.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Base class for pages that contain functions for working with repository
 */
public abstract class RepositoryAbstractPage extends Page {

	@FindBy(xpath = "//a[contains(@class, 'current-repository')]")
	protected WebElement repNameElement;

	@FindBy(xpath = "//*[text()='Code']")
	protected WebElement code;

	@FindBy(xpath = "//span[text()='Issues']/..")
	protected WebElement issues;

	@FindBy(xpath = "//*[text()='Pull requests']")
	protected WebElement pullRequests;

	@FindBy(xpath = "//*[text()='Wiki']")
	protected WebElement wiki;

	@FindBy(xpath = "//*[text()='Pulse']")
	protected WebElement pulse;

	@FindBy(xpath = "//*[text()='Graphs']")
	protected WebElement graphs;

	@FindBy(xpath = "//a[contains (@data-selected-links, 'repo_settings')]")
	protected WebElement settings;

	protected String repName;

	public RepositoryAbstractPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Check if all sections present on the page
	 * 
	 * @return True if all sections present on the page
	 */
	public boolean areRepSectionsPresent() {
		return isElementPresents(repNameElement) && isElementPresents(code)
				&& isElementPresents(issues) && isElementPresents(pullRequests)
				&& isElementPresents(wiki) && isElementPresents(pulse)
				&& isElementPresents(graphs) && isElementPresents(settings);
	}

	/**
	 * Navigate to the page with repository settings
	 * 
	 * @return An instance of {@link OptionsPage} class with repository settings
	 */
	public OptionsPage goToSettings() {
		settings.click();
		return PageFactory.initElements(driver, OptionsPage.class);
	}

	/**
	 * Navigate to the page with issues
	 * 
	 * @return An instance of {@link IssuesSectionPage} class
	 */
	public IssuesSectionPage goToIssues() {
		issues.click();
		System.out.println("Click issues " + issues.getText());
		return PageFactory.initElements(driver, IssuesSectionPage.class);
	}

	/**
	 * The method to get repository name
	 * 
	 * @return repository name
	 */
	public String getRepositoryName() {
		return repNameElement.getText();
	}

	/**
	 * The method to set repository name
	 * 
	 * @param repName
	 *            Repository name to set
	 */
	public void setRepName(String repName) {
		this.repName = repName;
	}

}
