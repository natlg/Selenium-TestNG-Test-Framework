package com.nat.test.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OptionsPage extends Page {
	
	@FindBy(linkText = "Delete this repository")
	private WebElement delete;
	
	
	@FindBy(xpath = "//*[@id='facebox']/div/div/form/p/input")
	private WebElement deleteField;
	
	public OptionsPage(WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
//		if (!"Options".trim().equalsIgnoreCase(driver.getTitle())) {
//			throw new IllegalStateException("This is not the options page, this is zzz" + driver.getTitle()+ "zzz");
//		}
	}

	public boolean isDeletePresents() {// TODO Auto-generated method stub
		return isElementPresents(delete);
	}

	public HomePage deleteRepository(String repName) {
	delete.click();
	deleteField.sendKeys(repName);
	deleteField.submit();
	return PageFactory.initElements(driver, HomePage.class);
	}

}
