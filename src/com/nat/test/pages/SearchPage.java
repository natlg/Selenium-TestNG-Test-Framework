package com.nat.test.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Class represents page with search results
 */
public class SearchPage extends Page {

	@FindBy(xpath = "//ul[@class='repo-list js-repo-list']/li/h3/a")
	private List<WebElement> searchResults;

	@FindBy(xpath = "//*[contains(text(), \"We couldn't find any repositories matching\")]")
	private WebElement errorNoResult;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!driver.getTitle().contains("Search")) {
			throw new IllegalStateException(
					"This is not the search page, this is " + driver.getTitle());
		}
	}

	/**
	 * Method to get the list of links with search results
	 * 
	 * @return List of links with search results
	 */
	public List<WebElement> getSearchResults() {
		return searchResults;
	}

	/**
	 * Check if error message presents
	 *
	 * @return True if error message presents
	 */
	public boolean isErrorNoResultPresents() {
		return isElementPresents(errorNoResult);
	}

	

}
