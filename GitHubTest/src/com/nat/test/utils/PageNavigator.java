package com.nat.test.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.nat.test.TestData;
import com.nat.test.pages.CreateRepositoryPage;
import com.nat.test.pages.HomePage;
import com.nat.test.pages.LoginPage;
import com.nat.test.pages.Page;
import com.nat.test.pages.RepositoryPage;
import com.nat.test.pages.StartPage;

public class PageNavigator {

	private Page page;
	private StartPage startPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private CreateRepositoryPage createRepositoryPage;
	private RepositoryPage repositoryPage;

	public LoginPage getLoginPage(WebDriver driver) {
		loginPage = null;
		driver.get(TestData.BASE_URL);
		try {
			startPage = PageFactory.initElements(driver, StartPage.class);
			loginPage = startPage.navigateToLogin();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't get Login page");
		}
		return loginPage;
	}

	public HomePage login(WebDriver driver) {
		LoginPage loginPage = getLoginPage(driver);
		homePage = loginPage.loginAs(TestData.LOGIN, TestData.PASSWORD);
		return homePage;
	}

	/**
	 * The method to get the Repository page from any page. Returns null if it's
	 * not possible to get the Repository page
	 *
	 * @return Repository page
	 */
	public RepositoryPage getNewRepositoryPage(WebDriver driver,
			String repDescription, boolean addReadme, String gitignore,
			String license) {
		try {
			driver.get(TestData.BASE_URL);
			homePage = getHomePage(driver);
			createRepositoryPage = homePage.createNewRepository();
			String currentRepName = getUniqueRepName();
			repositoryPage = createRepositoryPage.createRepository(
					currentRepName, repDescription, addReadme, gitignore,
					license);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't get NewRepositoryPage");
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
	public HomePage getHomePage(WebDriver driver) {
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
			System.out.println("Can't get HomePage");
		}
		return homePage;
	}

	public StartPage logout(WebDriver driver, Page page) {
		page.logout();
		driver.get(TestData.BASE_URL);
		try {
			startPage = PageFactory.initElements(driver, StartPage.class);
			return startPage;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't get StartPage");
			return null;
		}

	}

}
