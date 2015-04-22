package com.nat.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nat.test.TestData;

public class RepositoryPage extends Page {
	
		@FindBy(xpath = "//a[contains(@class, 'current-repository')]")
		private WebElement repNameElement;
		
		@FindBy(xpath = "//*[text()='Code']")
		private WebElement code;
		
		@FindBy(xpath = "//*[text()='Issues']")
		private WebElement issues;
		
		@FindBy(xpath = "//*[text()='Pull requests']")
		private WebElement pullRequests;
		
		@FindBy(xpath = "//*[text()='Wiki']")
		private WebElement wiki;
		
		@FindBy(xpath = "//*[text()='Pulse']")
		private WebElement pulse;
		
		@FindBy(xpath = "//*[text()='Graphs']")
		private WebElement graphs;
		
		@FindBy(xpath = "//a[contains (@data-selected-links, 'repo_settings')]")
		private WebElement settings;
		
		private String repName;
	
	public RepositoryPage(String repoName, WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!(TestData.LOGIN + "/" + repoName).equals(driver.getTitle())) {
			throw new IllegalStateException("This is not the repository page, this is " + driver.getTitle());
		}
		PageFactory.initElements(driver, this);
	}

	public boolean areSectionsPresent() {
		return isElementPresents(repNameElement) && isElementPresents(code)
				&& isElementPresents(issues)
				&& isElementPresents(pullRequests)
				&& isElementPresents(wiki)
				&& isElementPresents(pulse)
				&& isElementPresents(graphs)
				&& isElementPresents(settings);
	}

	public OptionsPage goToSettings() {
		System.out.println("RepositoryPage. go to settings ");
		settings.click();
		return PageFactory.initElements(driver, OptionsPage.class);
	}

	public String getRepositoryName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}
}
