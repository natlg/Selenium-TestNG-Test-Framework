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

	@BeforeClass
	public void beforeClass() {
		driver = TestData.getDriver();
	}
	
	@BeforeMethod
	public void beforeTest() {
		homePage = getHomePage();
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	/**
	 * Tests correct repository creating.
	 * <p>
	 * 1. Check that button to create repository presents and click it <br>
	 * 2. Check that form for creating repository is presented and options to
	 * choose owner, gitignore and license works correct <br>
	 * 3. Fill form with correct data and submit <br>
	 * 4. Check that name of the just created repository is the same as from
	 * creating and check that code, issues, pullRequests, wiki, pulse, graphs
	 * ans settings sections are presented <br>
	 * 5. Delete this repository
	 */
	@Test
	public void testRepositoryCreating() {
		Assert.assertTrue(homePage.isNewRepoButtonPresents());
		createRepositoryPage = homePage.createNewRepository();
		Assert.assertTrue(createRepositoryPage.isNewRepFormExists());
		Assert.assertTrue(createRepositoryPage.isNewRepFormJSWorks());
		String repName = getUniqueRepName();
		repositoryPage = createRepositoryPage.createRepository(repName,
				repDescription, addReadme, gitignore, license);
		Assert.assertEquals(repositoryPage.getRepositoryName(), repName);
		Assert.assertTrue(repositoryPage.areSectionsPresent());

		repositoryPage.goToSettings().deleteRepository(repName);
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
	@Test
	public void testRepositoryDeleting() {
		repositoryPage = getNewRepositoryPage();
		OptionsPage optionsPage = repositoryPage.goToSettings();
		Assert.assertTrue(optionsPage.isDeletePresents());
		homePage = optionsPage.deleteRepository(currentRepName);
		Assert.assertTrue(homePage.isRepositoryJustDeleted(currentRepName));
		homePage.logout();

	}

	/**
	 * The method to get the Repository page from any page. Returns null if it's
	 * not possible to get the Repository page
	 *
	 * @return Repository page
	 */
	public RepositoryPage getNewRepositoryPage() {
		try {
			createRepositoryPage = homePage.createNewRepository();
			currentRepName = getUniqueRepName();
			repositoryPage = createRepositoryPage.createRepository(
					currentRepName, repDescription, addReadme, gitignore,
					license);

		} catch (IllegalStateException e) {

			driver.get(TestData.BASE_URL);
			getHomePage();
			getNewRepositoryPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repositoryPage;
	}

	/**
	 * The method to get the unique name for new repository
	 *
	 * @return unique name
	 */
	public String getUniqueRepName() {
		return "NewRepository" + System.currentTimeMillis();
	}

	/**
	 * The method to get the Home page from any page. Returns null if it's not
	 * possible to get the Home page
	 *
	 * @return Home page
	 */
	private HomePage getHomePage() {
		homePage = null;
		try {
			driver.get(TestData.BASE_URL);
			StartPage startPage = PageFactory.initElements(driver,
					StartPage.class);
			LoginPage loginPage = startPage.navigateToLogin();
			homePage = loginPage.loginAs(TestData.LOGIN, TestData.PASSWORD);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return homePage;
	}

}
