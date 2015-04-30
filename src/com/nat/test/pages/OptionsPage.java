package com.nat.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Class represents page with Repository settings
 */
public class OptionsPage extends RepositoryAbstractPage {

	@FindBy(xpath = "//a[contains(@href, 'delete_repo_confirm')]")
	private WebElement delete;

	@FindBy(xpath = "//*[@id='facebox']/div/div/form/p/input")
	private WebElement deleteField;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public OptionsPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Checks if delete option presents
	 *
	 * @return True if delete option presents
	 */
	public boolean isDeletePresents() {
		return isElementPresents(delete);
	}

	/**
	 * Method to delete repository
	 *
	 * @param repName
	 *            repository name
	 * 
	 * @return An instance of {@link HomePage} class
	 */
	public HomePage deleteRepository(String repName) {
		delete.click();
		System.out.println(repName + " visible "
				+ isElementPresents(deleteField));
		deleteField.sendKeys(repName);
		deleteField.submit();
		return PageFactory.initElements(driver, HomePage.class);
	}

	/**
	 * Method to delete current repository
	 * 
	 * @return An instance of {@link HomePage} class
	 */
	public HomePage deleteRepository() {
		delete.click();
		deleteField.sendKeys(repNameElement.getText());
		deleteField.submit();
		return PageFactory.initElements(driver, HomePage.class);
	}

	/**
	 * Method to get expected page title
	 *
	 * @return expected page title
	 */
	@Override
	public String getExpectedTitle() {
		// Here is a bug
		return null;
	}
}
