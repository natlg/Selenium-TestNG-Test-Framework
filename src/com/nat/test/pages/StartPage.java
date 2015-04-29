package com.nat.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nat.test.TestData;

/**
 * Class represents Start page
 */
public class StartPage extends Page {

	@FindBy(xpath = "//a[@href='/login']")
	private WebElement login;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public StartPage(WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!"GitHub � Build software better, together.".equals(driver
				.getTitle())) {
			throw new IllegalStateException(
					"This is not the start page, this is " + driver.getTitle());
		}
	}

	/**
	 * Method to get the page with login form
	 *
	 * @return An instance of {@link LoginPage} class
	 */
	public LoginPage navigateToLogin() {
		login.click();
		return PageFactory.initElements(driver, LoginPage.class);
	}

	/**
	 * Check if link to the Login page presents on the page
	 *
	 * @return True if link to the Login page presents
	 */
	public boolean isLoginPresents() {
		return isElementPresents(login);
	}

	/**
	 * Check if search field presents on he page
	 *
	 * @return True if search field presents on he page
	 */
	public boolean isSearchPresents() {
		return isElementPresents(search);
	}
}
