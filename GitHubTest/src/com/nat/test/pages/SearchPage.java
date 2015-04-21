package com.nat.test.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage  extends Page {
	
	@FindBy(xpath = "//ul[@class='repo-list js-repo-list']/li/h3/a")
	private List<WebElement> searchResults;
	
	@FindBy(name = "q")
	private WebElement search;


	@FindBy(xpath = "//*[contains(text(), \"We couldn't find any repositories matching\")]")
	private WebElement errorNoResult;

	
	public SearchPage (WebDriver driver) {
		this.driver = driver;
		 // Check that we're on the right page.
        if (!driver.getTitle().contains("Search")) {
            throw new IllegalStateException("This is not the search page, this is " + driver.getTitle());
        }
	}
	
	public List getSearchResults () {
		return searchResults;
	}
	
	public boolean isErrorNoResultPresents() {
		return isElementPresents(errorNoResult);

	}

	public SearchPage search(String query) {
		search.clear();
		search.sendKeys(query);
		search.submit();
		return this;
		
	}


}
