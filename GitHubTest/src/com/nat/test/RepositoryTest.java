package com.nat.test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.nat.test.pages.CreateRepositoryPage;
import com.nat.test.pages.HomePage;
import com.nat.test.pages.LoginPage;
import com.nat.test.pages.OptionsPage;
import com.nat.test.pages.RepositoryPage;
import com.nat.test.pages.StartPage;
import com.nat.test.utils.PageNavigator;

public class RepositoryTest {

	private WebDriver driver;
	private HomePage homePage;
	private CreateRepositoryPage createRepositoryPage;
	private String currentRepName;
	private String repDescription = "NewDescription";
	private boolean addReadme = true;
	private String gitignore = "Java";
	private String license = "MIT";
	private RepositoryPage repositoryPage;
	private PageNavigator pageNavigator = new PageNavigator();
	private OptionsPage optionsPage;

	@BeforeClass
	public void beforeClass() {
		driver = TestData.getDriver();
	}

	@BeforeMethod
	public void beforeTest() {
		// homePage = pageNavigator.getHomePage(driver);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	/**
	 * Tests correct repository creating.
	 * <p>
	 * 1. Log in, check that button to create repository presents and click it <br>
	 * 2. Check that form for creating repository is presented and options to
	 * choose owner, gitignore and license works correct <br>
	 * 3. Fill form with correct data and submit <br>
	 * 4. Check that name of the just created repository is the same as from
	 * creating and check that code, issues, pullRequests, wiki, pulse, graphs
	 * and settings sections are presented <br>
	 * 5. Delete this repository
	 */
	@Test(enabled = true)
	public void testRepositoryCreating() {
		homePage = pageNavigator.getHomePage(driver);
		Assert.assertTrue(homePage.isNewRepoButtonPresents());
		createRepositoryPage = homePage.createNewRepository();
		Assert.assertTrue(createRepositoryPage.isNewRepFormExists());
		Assert.assertTrue(createRepositoryPage.isNewRepFormJSWorks());
		String repName = pageNavigator.getUniqueRepName();
		repositoryPage = createRepositoryPage.createRepository(repName,
				repDescription, addReadme, gitignore, license);
		Assert.assertEquals(repositoryPage.getRepositoryName(), repName);
		Assert.assertTrue(repositoryPage.areSectionsPresent());
		optionsPage = repositoryPage.goToSettings();
		homePage = optionsPage.deleteRepository(repName);
		repositoryPage.logout();
	}

	/**
	 * Tests correct repository deleting
	 * <p>
	 * 1. Create new repository, go to settings <br>
	 * 2. Check that option to delete exists <br>
	 * 3. Delete repository <br>
	 * 4. Check that message about successful deleting appeared and check that
	 * name of deleted repository is not presented in the list of existing
	 * repositories
	 */
	@Test(enabled = true)
	public void testRepositoryDeleting() {
		repositoryPage = pageNavigator.getNewRepositoryPage(driver,
				repDescription, addReadme, gitignore, license);
		currentRepName = repositoryPage.getRepositoryName();
		OptionsPage optionsPage = repositoryPage.goToSettings();
		Assert.assertTrue(optionsPage.isDeletePresents());
		homePage = optionsPage.deleteRepository(currentRepName);
		Assert.assertTrue(homePage.isRepositoryJustDeleted(currentRepName));
		homePage.logout();
	}
}
