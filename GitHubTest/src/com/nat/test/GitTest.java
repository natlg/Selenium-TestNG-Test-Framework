package com.nat.test;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nat.test.pages.HomePage;
import com.nat.test.pages.LoginPage;
import com.nat.test.pages.NotificationsPage;
import com.nat.test.pages.SearchPage;
import com.nat.test.pages.StartPage;
import com.nat.test.utils.PageNavigator;

public class GitTest {
	private WebDriver driver;
	private SearchPage searchPage;
	private HomePage homePage;
	private StartPage startPage;
	private boolean passed = true;
	private boolean passedSearch = true;
	private LoginPage loginPage;
	private NotificationsPage notificationsPage;
	private PageNavigator pageNavigator = new PageNavigator();

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

	@Test(enabled = true)
	public void testLogin() {

		// 1 step
		loginPage = pageNavigator.getLoginPage(driver);
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
		startPage = pageNavigator.logout(driver, homePage);
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
		homePage = pageNavigator.login(driver);
		// login();
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
	@Test(dataProvider = "searchQueriesProvider", enabled = true, groups = { "search" })
	public void testSearch(String searchQuery, String hasResultStr) {
		// 1 step
		startPage = PageFactory.initElements(driver, StartPage.class);
		passed = startPage.isSearchPresents();
		TestData.saveTestResult(TestData.TEST_SEARCH, TestData.STEP_1, passed);

		// 2 step
		searchPage = pageNavigator.search(driver, searchQuery, startPage);
		boolean hasResult = Boolean.parseBoolean(hasResultStr);
		passed = false;
		if (hasResult) {
			List<WebElement> searchResults = searchPage.getSearchResults();
			for (WebElement result : searchResults) {
				if (result.getAttribute("href").toLowerCase()
						.contains(searchQuery)) {
					passed = true;
				}
			}
			if (!passed) {
				passedSearch = false;
			}
			// Save test result for certain data from DataProvider
			TestData.saveTestResultWithData(TestData.TEST_SEARCH, passed,
					searchQuery);
		} else {
			passed = searchPage.isErrorNoResultPresents();
			if (!passed) {
				passedSearch = false;
			}
			// Save test result for certain data from DataProvider
			TestData.saveTestResultWithData(TestData.TEST_SEARCH, passed,
					searchQuery);
		}
		driver.get(TestData.BASE_URL);
	}

	@AfterMethod(groups = { "search" })
	public void afterSearch() {
		// Save result as passed only if test passed with all data from
		// DataProvider
		TestData.saveTestResult(TestData.TEST_SEARCH, TestData.STEP_2,
				passedSearch);
	}

	@DataProvider(name = "searchQueriesProvider")
	public Object[][] createQuery() {
		return TestData.getSearchData("testSearch");
	}

}
