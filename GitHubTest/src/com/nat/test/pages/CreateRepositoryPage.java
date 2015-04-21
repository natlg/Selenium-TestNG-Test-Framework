package com.nat.test.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateRepositoryPage extends Page {

	@FindBy(name = "q")
	private WebElement search;

	@FindBy(id = "repository_name")
	private WebElement repName;

	@FindBy(id = "repository_description")
	private WebElement repDescription;

	@FindBy(xpath = "//*[contains (text(), 'Create repository')]")
	private WebElement repSubmit;

	@FindBy(xpath = "//div[contains(@class, 'owner-container')]/span")
	private WebElement owner;

	@FindBy(xpath = "//input[@type='radio'][@id='repository_public_true']")
	private WebElement publicRep;

	@FindBy(xpath = "//input[@type='radio'][@id='repository_public_false']")
	private WebElement privateRep;

	@FindBy(id = "repository_auto_init")
	private WebElement autoInit;

	@FindBy(xpath = "//*[contains(text(), 'Add .gitignore')]")
	private WebElement addGitignore;

	@FindBy(id = "context-ignore-filter-field")
	private WebElement chooseGitignore;

	@FindBy(xpath = "//div[@class='select-menu-item js-navigation-item']/div")
	private List<WebElement> gitignoreList;

	@FindBy(xpath = "//*[contains(text(), 'Add a license')]")
	private WebElement addLicense;

	@FindBy(xpath = "//div[@class='select-menu-item js-navigation-item']/div")
	private List<WebElement> licenseList;

	@FindBy(id = "context-license-filter-field")
	private WebElement chooseLicense;

	@FindBy(xpath = "//button[contains(@class, 'sign-out-button')]")
	private WebElement logout;

	@FindBy(xpath = "//*[contains(text(), 'Choose another owner')]")
	private WebElement chooseOwner;

	public CreateRepositoryPage(WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!"Create a New Repository".equals(driver.getTitle())) {
			throw new IllegalStateException(
					"This is not the CreateRepository page, this is "
							+ driver.getTitle());
		}
	}

	public boolean isNewRepFormExists() {
		return isElementPresents(repName) && isElementPresents(repDescription)
				&& isElementPresents(repSubmit) && isElementPresents(owner)
				&& isElementPresents(publicRep)
				&& isElementPresents(privateRep) && isElementPresents(autoInit)
				&& isElementPresents(addGitignore)
				&& isElementPresents(addLicense);
	}

	public StartPage logout() {
		logout.click();
		return PageFactory.initElements(driver, StartPage.class);
	}

	public boolean isNewRepFormJSWorks() {
		owner.click();
		boolean isChooseOwnerPresents = isElementPresents(chooseOwner);
		System.out.println(isChooseOwnerPresents + "");
		addGitignore.click();
		boolean isGitignorePresents = isElementPresents(chooseGitignore);
		System.out.println(isGitignorePresents + "");
		addLicense.click();
		boolean isLicensePresents = isElementPresents(chooseLicense);
		System.out.println(isLicensePresents + "");

		return isChooseOwnerPresents && isGitignorePresents
				&& isLicensePresents;
	}

	public RepositoryPage createRepository(String repName,
			String repDescription, boolean addReadme, String gitignore,
			String license) {

		this.repName.sendKeys(repName);
		this.repDescription.sendKeys(repDescription);
		if (autoInit.isSelected()) {
			if (!addReadme) {
				autoInit.click();
			}
		} else {
			if (addReadme) {
				autoInit.click();
			}
		}

		if (null != gitignore) {
			addGitignore.click();
			for (WebElement element : gitignoreList) {
				if (element.getText().contains(gitignore)) {
					element.click();
				}
			}
		}

		if (null != license) {
			addLicense.click();
			for (WebElement element : licenseList) {
				if (element.getText().contains(license)) {
					element.click();
				}
			}
		}
		repSubmit.click();
		return new RepositoryPage(repName, driver);
	}

}
