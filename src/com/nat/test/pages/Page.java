package com.nat.test.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Base class for all pages
 */
public abstract class Page {
	protected WebDriver driver;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public Page(WebDriver driver) {
		this.driver = driver;
		String title = driver.getTitle().trim();
		String expTitle = (String) getExpectedTitle();
		System.out.println("title: " + title + " exp title: " + expTitle);
		if (null != expTitle && !title.contains(expTitle)) {
			throw new IllegalStateException("This is not the " + expTitle
					+ ", this is " + title);
		}
	}

	public abstract CharSequence getExpectedTitle();

	/**
	 * presents in new design
	 */
	@FindBy(xpath = "//button[contains(@class, 'sign-out-button')]")
	protected WebElement logoutNewDesign;

	@FindBy(name = "q")
	protected WebElement search;

	/**
	 * presents in old design
	 */
	@FindBy(xpath = ".//*[@id='user-links']/li[3]/a/img")
	protected WebElement headerUserOldDesign;

	/**
	 * presents in old design after clicking on {@link headerUserOldDesign}
	 */
	@FindBy(xpath = "//button[@class = 'sign-out-button']")
	protected WebElement logoutOldDesign;

	/**
	 * Checks if the element presents on the page and visible
	 * 
	 * @param element
	 *            element to check
	 *
	 * @return true if the element presents on the page and visible
	 */
	protected boolean isElementPresents(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * The method to logout
	 *
	 * @return An instance of {@link Page} class after logout
	 */
	public Page logout() {
		if (isElementPresents(logoutNewDesign)) {
			logoutNewDesign.click();
			return this;
		} else {
			System.out.println("headerUserOldDesign"
					+ headerUserOldDesign.isDisplayed());
			headerUserOldDesign.click();
			System.out.println("logoutOldDesign"
					+ logoutOldDesign.isDisplayed());
			logoutOldDesign.click();
			return this;
		}
	}

	/**
	 * Method to search
	 *
	 * @param query
	 *            Search query
	 * @return An instance of {@link Page} class with search result
	 */
	public Page search(String query) {
		search.clear();
		search.sendKeys(query);
		search.submit();
		return this;
	}

}
