package com.nat.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Class represents Login page that contains login form
 */
public class LoginPage extends Page {

	@FindBy(name = "login")
	private WebElement login;

	@FindBy(name = "password")
	private WebElement pass;

	@FindBy(name = "commit")
	private WebElement submit;

	@FindBy(xpath = "//*[text()=' Incorrect username or password.']")
	private WebElement error;

	@FindBy(xpath = "//h1[text()='Sign in']")
	private WebElement singInMessage;

	@FindBy(xpath = "//a[@href='/password_reset']")
	private WebElement resetPassword;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Method to login with correct data
	 *
	 * @param username
	 *            Correct username
	 * @param password
	 *            Correct password
	 * @return An instance of {@link HomePage} class
	 */
	public HomePage loginAs(String username, String password) {
		fillForm(username, password);
		return PageFactory.initElements(driver, HomePage.class);
	}

	/**
	 * Method to login with incorrect data
	 *
	 * @param username
	 *            user name
	 * @param password
	 *            password
	 * @return An instance of {@link LoginPage} class with error message
	 */
	public LoginPage loginAsExpectingError(String username, String password) {
		fillForm(username, password);
		return this;
	}

	/**
	 * Method to fill and submit login form
	 *
	 * @param username
	 *            user name
	 * @param password
	 *            password
	 */
	private void fillForm(String username, String password) {

		login.clear();
		login.sendKeys(username);
		pass.clear();
		pass.sendKeys(password);
		pass.submit();
	}

	/**
	 * Method to check if message about incorrect user name or password appeared
	 * 
	 * @return True if error message presents
	 */
	public boolean isErrorMessagePresents() {
		return isElementPresents(error);
	}

	/**
	 * Method to check if all elements of the login form present
	 * 
	 * @return True if all elements of the login form present
	 */
	public boolean isLoginFormPresents() {
		return isElementPresents(login) && isElementPresents(pass)
				&& isElementPresents(submit)
				&& isElementPresents(singInMessage)
				&& isElementPresents(resetPassword);
	}

	/**
	 * Method to get expected page title
	 *
	 * @return expected page title
	 */
	@Override
	public String getExpectedTitle() {
		return "Sign in · GitHub";
	}

}
