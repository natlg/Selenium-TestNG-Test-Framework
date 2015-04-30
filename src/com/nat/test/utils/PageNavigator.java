package com.nat.test.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.nat.test.TestData;
import com.nat.test.pages.CreateRepositoryPage;
import com.nat.test.pages.HomePage;
import com.nat.test.pages.LoginPage;
import com.nat.test.pages.OptionsPage;
import com.nat.test.pages.Page;
import com.nat.test.pages.RepositoryAbstractPage;
import com.nat.test.pages.RepositoryPage;
import com.nat.test.pages.SearchPage;
import com.nat.test.pages.StartPage;

/**
 * Class for navigation on the site
 */
public class PageNavigator {

	private StartPage startPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private CreateRepositoryPage createRepositoryPage;
	private RepositoryPage repositoryPage;
	private OptionsPage optionsPage;

	/**
	 * Navigate to the page with the login form. Need to sign out before
	 * calling. Returns null if it's not possible to get the Login page
	 *
	 * @param driver
	 *            The driver that will be used for navigation
	 * @return An instance of {@link LoginPage} class or null if it's not
	 *         possible to get the Login page
	 */
	public LoginPage getLoginPage(WebDriver driver) {
		loginPage = null;
		driver.get(TestData.BASE_URL);
		try {
			startPage = PageFactory.initElements(driver, StartPage.class);
			loginPage = startPage.navigateToLogin();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't get the Login page");
		}
		return loginPage;
	}

	/**
	 * Login with correct data (takes username and password from the
	 * config.properties file) and navigate to the Home page. Need to sign out
	 * before calling. Returns null if it's not possible to login or get the
	 * Home page
	 *
	 * @param driver
	 *            The driver that will be used for navigation
	 * @return An instance of {@link HomePage} class or null if it's not
	 *         possible to login or get the Home page
	 */
	public HomePage login(WebDriver driver) {
		homePage = null;
		try {
			LoginPage loginPage = getLoginPage(driver);
			homePage = loginPage.loginAs(TestData.LOGIN, TestData.PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't get the Home page");
		}
		return homePage;
	}

	/**
	 * Method to get the page with just created repository. Need to sign out
	 * before calling. Returns null if it's not possible to get the Repository
	 * page
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @param repDescription
	 *            Repository description
	 * @param addReadme
	 *            If true, initialize repository with a README
	 * @param gitignore
	 *            Select gitignore from the dropdown
	 * @param license
	 *            Select license type from the dropdown
	 *
	 * @return An instance of {@link RepositoryPage} class or null if it's not
	 *         possible to login or get the Repository page
	 */
	public RepositoryPage getNewRepositoryPage(WebDriver driver,
			String repDescription, boolean addReadme, String gitignore,
			String license) {
		repositoryPage = null;
		try {
			driver.get(TestData.BASE_URL);
			homePage = login(driver);
			createRepositoryPage = homePage.createNewRepository();
			String currentRepName = getUniqueRepName();
			repositoryPage = createRepositoryPage.createRepository(
					currentRepName, repDescription, addReadme, gitignore,
					license);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't get RepositoryPage");
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
	 * Method to log out and get Start page from any page. Ren null if it's not
	 * possible to log out or get the Start page
	 *
	 * @param driver
	 *            The driver that will be used for navigation
	 * @param page
	 *            Current page
	 * @return An instance of {@link StartPage} class or null if it's not
	 *         possible to log out or get the Start page
	 */
	public StartPage logout(WebDriver driver, Page page) {
		try {
			page.logout();
			driver.get(TestData.BASE_URL);
			startPage = PageFactory.initElements(driver, StartPage.class);
			return startPage;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't get Start page");
			return null;
		}

	}

	/**
	 * Method to search
	 *
	 * @param driver
	 *            The driver that will be used for navigation
	 * @param query
	 *            Search query
	 * @param page
	 *            Current page
	 * @return An instance of {@link SearchPage} class with search result
	 */
	public SearchPage search(WebDriver driver, String query, Page page) {
		page.search(query);
		return PageFactory.initElements(driver, SearchPage.class);
	}

	/**
	 * Method to perform click using {@link Actions} class. Call if simple click
	 * doesn't work for {@link ChromeDriver}
	 *
	 * @param driver
	 *            The driver that will be used for navigation
	 * @param element
	 *            Element to click
	 */
	public static void click(WebDriver driver, WebElement element) {
		try {
			element.click();
		} catch (org.openqa.selenium.WebDriverException e) {
			Actions action = new Actions(driver);
			action.moveToElement(element).perform();
			action.click().perform();
		}
	}

	/**
	 * Method to delete repository
	 *
	 * @param driver
	 *            The driver that will be used for navigation
	 * @param page
	 *            Page with repository to delete
	 * @return An instance of {@link HomePage} class
	 */
	public HomePage deleteRepository(WebDriver driver,
			RepositoryAbstractPage page) {
		optionsPage = page.goToSettings();
		homePage = optionsPage.deleteRepository();
		return homePage;
	}

	/**
	 * Method to delete repository and then logout
	 *
	 * @param driver
	 *            The driver that will be used for navigation
	 * @param page
	 *            Page with repository to delete
	 * @return An instance of {@link HomePage} class
	 */
	public StartPage deleteRepositiryAndLogout(WebDriver driver,
			String repName, RepositoryPage repositoryPage) {
		optionsPage = repositoryPage.goToSettings();
		homePage = optionsPage.deleteRepository(repName);
		return logout(driver, repositoryPage);
	}
}
