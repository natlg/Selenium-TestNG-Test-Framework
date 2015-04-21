package com.nat.test.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class Page {
	protected WebDriver driver;

	@FindBy(xpath = "//button[contains(@class, 'sign-out-button')]")
	protected WebElement logout;

	protected boolean isElementPresents(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public StartPage logout() {
		if (isElementPresents(logout)) {
			logout.click();
			return PageFactory.initElements(driver, StartPage.class);
		}
		return null;
	}

}
