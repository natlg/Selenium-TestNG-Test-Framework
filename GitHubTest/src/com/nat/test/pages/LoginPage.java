package com.nat.test.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.nat.test.TestData;

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

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!"Sign in · GitHub".equals(driver.getTitle())) {
			throw new IllegalStateException(
					"This is not the login page, this is " + driver.getTitle());
		}
	}

	public HomePage loginAs(String username, String password) {
		fillForm(username, password);
		return PageFactory.initElements(driver, HomePage.class);
	}

	public LoginPage loginAsExpectingError(String username, String password) {
		fillForm(username, password);
		return this;
	}

	private void fillForm(String username, String password) {

		login.clear();
		login.sendKeys(username);
		pass.clear();
		pass.sendKeys(password);
		pass.submit();
	}

	public boolean isErrorMessagePresents() {
		return isElementPresents(error);
	}

	public boolean isLoginFormPresents() {
		return isElementPresents(login) && isElementPresents(pass)
				&& isElementPresents(submit)
				&& isElementPresents(singInMessage)
				&& isElementPresents(resetPassword);
	}

}
