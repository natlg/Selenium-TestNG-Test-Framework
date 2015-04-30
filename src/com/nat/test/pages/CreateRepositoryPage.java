package com.nat.test.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nat.test.TestData;
import com.nat.test.utils.PageNavigator;

/**
 * Class represents page with the form for creating new repository
 */
public class CreateRepositoryPage extends Page {

	@FindBy(name = "q")
	private WebElement search;

	@FindBy(id = "repository_name")
	private WebElement repNameElement;

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

	private String repName;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public CreateRepositoryPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Checks if the form for creating new repository presents on the page
	 *
	 * @return true if all form elements present on the page
	 */
	public boolean isNewRepFormExists() {
		return isElementPresents(repNameElement)
				&& isElementPresents(repDescription)
				&& isElementPresents(repSubmit) && isElementPresents(owner)
				&& isElementPresents(publicRep)
				&& isElementPresents(privateRep) && isElementPresents(autoInit)
				&& isElementPresents(addGitignore)
				&& isElementPresents(addLicense);
	}

	/**
	 * Log out
	 *
	 * @return An instance of {@link StartPage} class
	 */
	public StartPage logout() {
		logout.click();
		return PageFactory.initElements(driver, StartPage.class);
	}

	/**
	 * Check if dropdowns to choose owner, gitignore and license appear if click
	 * theirs elements
	 *
	 * @return True if dropdowns to choose owner, gitignore and license appear
	 *         if click it's elements
	 */
	public boolean isNewRepFormJSWorks() {
		boolean isChooseOwnerPresents;
		boolean isGitignorePresents;
		boolean isLicensePresents;
		PageNavigator.click(driver, owner);
		if (!isElementPresents(chooseOwner)) {
			owner.click();
			isChooseOwnerPresents = isElementPresents(chooseOwner);
		} else {
			isChooseOwnerPresents = true;
		}

		PageNavigator.click(driver, addGitignore);
		if (!isElementPresents(chooseGitignore)) {
			addGitignore.click();
			isGitignorePresents = isElementPresents(addGitignore);
		} else {
			isGitignorePresents = true;
		}

		PageNavigator.click(driver, addLicense);
		if (!isElementPresents(chooseLicense)) {
			addLicense.click();
			isLicensePresents = isElementPresents(addLicense);
		} else {
			isLicensePresents = true;
		}
		return isChooseOwnerPresents && isGitignorePresents
				&& isLicensePresents;
	}

	/**
	 * Method to get the page with just created repository.
	 * 
	 * @param repName
	 *            New repository name
	 * @param repDescription
	 *            Repository description
	 * @param addReadme
	 *            If true, initialize repository with a README
	 * @param gitignore
	 *            Select gitignore from the dropdown
	 * @param license
	 *            Select license type from the dropdown
	 *
	 * @return An instance of {@link RepositoryPage} class
	 */
	public RepositoryPage createRepository(String repName,
			String repDescription, boolean addReadme, String gitignore,
			String license) {
		// GitHub replaces all spaces to "-" anyway
		this.repName = repName.trim().replaceAll(" +", "-");
		this.repNameElement.sendKeys(repName);
		this.repDescription.sendKeys(repDescription);
		if (autoInit.isSelected()) {
			if (!addReadme) {
				PageNavigator.click(driver, autoInit);
			}
		} else {
			if (addReadme) {
				PageNavigator.click(driver, autoInit);
			}
		}
		if (null != gitignore) {
			PageNavigator.click(driver, addGitignore);
			for (WebElement element : gitignoreList) {
				if (element.getText().contains(gitignore)) {
					element.click();
				}
			}
		}
		if (null != license) {
			PageNavigator.click(driver, addLicense);
			for (WebElement element : licenseList) {
				if (element.getText().contains(license)) {
					element.click();
				}
			}
		}
		repSubmit.click();
		TestData data = TestData.getData();
		data.setRepositoryName(repName);
		return PageFactory.initElements(driver, RepositoryPage.class);
	}

	/**
	 * The method to get the repository name
	 *
	 * @return repName
	 */
	public String getRepositoryName() {
		return repName;
	}

	/**
	 * Method to get expected page title
	 *
	 * @return expected page title
	 */
	@Override
	public String getExpectedTitle() {
		return "Create a New Repository";
	}
}
