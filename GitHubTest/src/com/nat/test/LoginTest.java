package com.nat.test;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nat.test.pages.HomePage;
import com.nat.test.pages.LoginPage;
import com.nat.test.pages.NotificationsPage;
import com.nat.test.pages.SearchPage;
import com.nat.test.pages.StartPage;

public class LoginTest {
	private WebDriver driver;
	private SearchPage searchPage;
	private HomePage homePage;
	private StartPage startPage;
	private boolean passed = true;
	private LoginPage loginPage;
	private NotificationsPage notificationsPage;
	

	@BeforeClass
	public void beforeClass() {
		driver = TestData.getDriver();
	}
	

	@BeforeMethod
	public void beforeTest() {
		driver.get(TestData.BASE_URL);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void login() {
		LoginPage loginPage = getLoginPage();
		homePage = loginPage.loginAs(TestData.LOGIN, TestData.PASSWORD);

	}

	/**
	 * Tests correct login.
	 * <p>
	 * 1. Go to the login page <br>
	 * 2. Check login form is presented <br>
	 * 3. Try to login with incorrect data and check that error message appears
	 * <br>
	 * 4. Login with correct data, check that login was successful <br>
	 * 5. Logout, check that logout was successful
	 */

	@Test
	public void testLogin() {

		// 1 step
		loginPage = getLoginPage();
		if (null == loginPage) {
			passed = false;
		}
		TestData.saveTestResult(TestData.TEST_LOGIN, TestData.STEP_1, passed);

		// 2 step
		passed = loginPage.isLoginFormPresents();
		TestData.saveTestResult(TestData.TEST_LOGIN, TestData.STEP_2, passed);

		// 3 step
		loginPage.loginAsExpectingError("qqq", "qqq");
		passed = loginPage.isErrorMessagePresents();
		TestData.saveTestResult(TestData.TEST_LOGIN, TestData.STEP_3, passed);

		// 4 step
		HomePage homePage = loginPage
				.loginAs(TestData.LOGIN, TestData.PASSWORD);
		passed = homePage.isLoginSuccessed();
		TestData.saveTestResult(TestData.TEST_LOGIN, TestData.STEP_4, passed);

		// 5 step
		startPage = homePage.logout();
		passed = startPage.isLoginPresents();
		TestData.saveTestResult(TestData.TEST_LOGIN, TestData.STEP_5, passed);

	}

	/**
	 * Tests notification.
	 * <p>
	 * 1. Log in, check that notifications icon presents on the home page <br>
	 * 2. Click the notifications icon, check that all sections present <br>
	 */

	@Test(enabled = true)
	public void testNotifications() {

		// 1 step
		login();
		TestData.saveTestResult(TestData.TEST_NOTIFICATIONS, TestData.STEP_1,
				homePage.isNotificationsIconPresents());

		// 2 step
		notificationsPage = homePage.seeNotifications();
		passed = notificationsPage.isNoticicationsPresent();
		TestData.saveTestResult(TestData.TEST_NOTIFICATIONS, TestData.STEP_2,
				passed);
		notificationsPage.logout();
	}

	/**
	 * Tests correct search.
	 * <p>
	 * 1. Go to the login page and check that it contains search field <br>
	 * 2. Fill the search field with the query and check that search result
	 * appears (if no search result, then message appears)<br>
	 */
	@Test(dataProvider = "searchQueriesProvider", enabled = true)
	public void testSearch(String searchQuery, String hasResultStr) {
		// 1 step
		startPage = PageFactory.initElements(driver, StartPage.class);
		passed = startPage.isSearchPresents();
		TestData.saveTestResult(TestData.TEST_SEARCH, TestData.STEP_1, passed);

		// 2 step
		searchPage = startPage.search(searchQuery);
		boolean hasResult = Boolean.parseBoolean(hasResultStr);
		if (hasResult) {
			List<WebElement> searchResults = searchPage.getSearchResults();
			for (WebElement result : searchResults) {
				passed = result.getAttribute("href").toLowerCase()
						.contains(searchQuery);
				TestData.saveTestResult(TestData.TEST_SEARCH, TestData.STEP_2,
						passed);

			}
		} else {
			passed = searchPage.isErrorNoResultPresents();
			TestData.saveTestResult(TestData.TEST_SEARCH, TestData.STEP_2,
					passed);

		}
		// need for correct work with next data
		driver.get(TestData.BASE_URL);
	}

	@DataProvider(name = "searchQueriesProvider")
	public Object[][] createQuery() {
		return TestData.getSearchData("testSearch");
	}

	/**
	 * The method to get the login page from any page. Returns null if it's not
	 * possible to get the login page
	 *
	 * @return the page with form to log in, null if it's not possible to get
	 *         the login page
	 */
	private LoginPage getLoginPage() {
		LoginPage loginPage = null;
		driver.get(TestData.BASE_URL);
		try {
			startPage = PageFactory.initElements(driver, StartPage.class);
			loginPage = startPage.navigateToLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginPage;
	}

}
