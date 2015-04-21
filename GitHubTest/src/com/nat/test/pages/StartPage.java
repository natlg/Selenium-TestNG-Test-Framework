package com.nat.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nat.test.TestData;

public class StartPage extends Page {
	
	@FindBy(xpath = "//a[@href='/login']")
	private WebElement login;
	
	@FindBy(name = "q")
	private WebElement search;
	
	public StartPage(WebDriver driver) {
		this.driver = driver;
		 // Check that we're on the right page.
        if (!"GitHub · Build software better, together.".equals(driver.getTitle())) {
             throw new IllegalStateException("This is not the start page, this is " + driver.getTitle());
        }
	}

	public LoginPage navigateToLogin() {
	login.click();
	return PageFactory.initElements(driver, LoginPage.class);
	}
	
	public boolean isLoginPresents () {
		return isElementPresents(login);
	}
	
	public boolean isSearchPresents() {
		return isElementPresents(search);

	}
	
	public SearchPage search(String query) {
		search.clear();
		search.sendKeys(query);
		search.submit();
		return PageFactory.initElements(driver, SearchPage.class);
		
	}
}
