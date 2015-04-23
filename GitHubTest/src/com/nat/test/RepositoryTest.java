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
	 * choose owner, gitignore and license work correct <br>
	 * 3. Fill form with correct data and submit <br>
	 * 4. Check that name of the just created repository is the same as from
	 * creating and check that code, issues, pullRequests, wiki, pulse, graphs
	 * and settings sections are presented <br>
	 * 5. Delete this repository
	 */
	@Test(enabled = true)
	public void testRepositoryCreating() {
		// 1 step
		homePage = pageNavigator.login(driver);
		TestData.saveTestResult(TestData.TEST_REPOSITORY_CREATING, TestData.STEP_1,
				homePage.isNewRepoButtonPresents());
		createRepositoryPage = homePage.createNewRepository();

		// 2 step
		TestData.saveTestResult(TestData.TEST_REPOSITORY_CREATING, TestData.STEP_2,
				createRepositoryPage.isNewRepFormExists()
						&& createRepositoryPage.isNewRepFormJSWorks());

		// 3 step
		String repName = pageNavigator.getUniqueRepName();
		repositoryPage = createRepositoryPage.createRepository(repName,
				repDescription, addReadme, gitignore, license);
		TestData.saveTestResult(TestData.TEST_REPOSITORY_CREATING, TestData.STEP_3,
				null != repositoryPage);

		// 4 step
		TestData.saveTestResult(TestData.TEST_REPOSITORY_CREATING, TestData.STEP_4,
				repositoryPage.getRepositoryName().equals(repName)
						&& repositoryPage.areSectionsPresent());

		// 5 step
		optionsPage = repositoryPage.goToSettings();
		homePage = optionsPage.deleteRepository(repName);
		TestData.saveTestResult(TestData.TEST_REPOSITORY_CREATING, TestData.STEP_5,
				null != homePage);
		repositoryPage.logout();
	}

	/**
	 * Tests correct repository deleting
	 * <p>
	 * 1. Create new repository, go to settings <br>
	 * 2. Check that option to delete exists <br>
	 * 3. Delete repository, check that message about successful deleting
	 * appeared and check that name of deleted repository is not presented in
	 * the list of existing repositories
	 */
	@Test(enabled = true)
	public void testRepositoryDeleting() {
		// 1 step
		repositoryPage = pageNavigator.getNewRepositoryPage(driver,
				repDescription, addReadme, gitignore, license);
		currentRepName = repositoryPage.getRepositoryName();
		OptionsPage optionsPage = repositoryPage.goToSettings();
		TestData.saveTestResult(TestData.TEST_REPOSITORY_DELETING, TestData.STEP_1,
				null != optionsPage);

		// 2 step
		TestData.saveTestResult(TestData.TEST_REPOSITORY_DELETING, TestData.STEP_2,
				optionsPage.isDeletePresents());

		// 3 step
		homePage = optionsPage.deleteRepository(currentRepName);
		TestData.saveTestResult(TestData.TEST_REPOSITORY_DELETING, TestData.STEP_3,
				homePage.isRepositoryJustDeleted(currentRepName));
		homePage.logout();
	}
}
