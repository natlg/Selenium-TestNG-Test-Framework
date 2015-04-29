package com.nat.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nat.test.TestData;

/**
 * Class represents page with repository
 */
public class RepositoryPage extends RepositoryAbstractPage {

	/**
	 * Class constructor
	 * 
	 * @param repoName
	 *            Repository name
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public RepositoryPage(String repoName, WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!(TestData.LOGIN + "/" + repoName).equals(driver.getTitle())) {
			throw new IllegalStateException(
					"This is not the repository page, this is "
							+ driver.getTitle());
		}
		PageFactory.initElements(driver, this);
	}

}
