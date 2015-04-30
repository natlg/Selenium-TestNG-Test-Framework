package com.nat.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.nat.test.pages.CreateRepositoryPage;
import com.nat.test.pages.HomePage;
import com.nat.test.pages.IssuePage;
import com.nat.test.pages.IssuesSectionPage;
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
	private IssuesSectionPage issuesSectionPage;
	private StartPage startPage;

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
	public void testAddRepository() {
		// 1 step
		homePage = pageNavigator.login(driver);
		TestData.saveTestResult(TestData.TEST_ADD_REPOSITORY, TestData.STEP_1,
				homePage.isNewRepoButtonPresents());
		createRepositoryPage = homePage.createNewRepository();

		// 2 step
		TestData.saveTestResult(TestData.TEST_ADD_REPOSITORY, TestData.STEP_2,
				createRepositoryPage.isNewRepFormExists()
						&& createRepositoryPage.isNewRepFormJSWorks());

		// 3 step
		String repName = pageNavigator.getUniqueRepName();
		repositoryPage = createRepositoryPage.createRepository(repName,
				repDescription, addReadme, gitignore, license);
		TestData.saveTestResult(TestData.TEST_ADD_REPOSITORY, TestData.STEP_3,
				null != repositoryPage);

		// 4 step
		TestData.saveTestResult(TestData.TEST_ADD_REPOSITORY, TestData.STEP_4,
				repositoryPage.getRepositoryName().equals(repName)
						&& repositoryPage.areRepSectionsPresent());

		// 5 step
		startPage = pageNavigator.deleteRepositiryAndLogout(driver, repName,
				repositoryPage);
		TestData.saveTestResult(TestData.TEST_ADD_REPOSITORY, TestData.STEP_5,
				null != startPage);
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
	public void testDeleteRepository() {
		// 1 step
		repositoryPage = pageNavigator.getNewRepositoryPage(driver,
				repDescription, addReadme, gitignore, license);
		currentRepName = TestData.getData().getRepositoryName();
		OptionsPage optionsPage = repositoryPage.goToSettings();
		TestData.saveTestResult(TestData.TEST_DELETE_REPOSITORY,
				TestData.STEP_1, null != optionsPage);

		// 2 step
		TestData.saveTestResult(TestData.TEST_DELETE_REPOSITORY,
				TestData.STEP_2, optionsPage.isDeletePresents());

		// 3 step
		homePage = optionsPage.deleteRepository(currentRepName);
		TestData.saveTestResult(TestData.TEST_DELETE_REPOSITORY,
				TestData.STEP_3,
				homePage.isRepositoryJustDeleted(currentRepName));
		homePage.logout();
	}

	/**
	 * Tests correct issue adding.
	 * <p>
	 * 1. Log in, add new repository <br>
	 * 2. Click on Issues link, check that all sections and welcome message
	 * present <br>
	 * 3. Click the link to create issue, check that Title, Comments fields and
	 * Labels, Milestone, Assignee links present <br>
	 * 4. Fill all fields and confirm creating, check that issue submitted <br>
	 * 5. Navigate to Issues Section page and check that new issue appeared in
	 * the list of issues <br>
	 * 6. Delete repository
	 */
	@Test(enabled = true)
	public void testAddIssue() {
		// 1 step
		repositoryPage = pageNavigator.getNewRepositoryPage(driver,
				repDescription, addReadme, gitignore, license);
		TestData.saveTestResult(TestData.TEST_ADD_ISSUE, TestData.STEP_1,
				null != repositoryPage);

		// 2 step
		issuesSectionPage = repositoryPage.goToIssues();
		TestData.saveTestResult(
				TestData.TEST_ADD_ISSUE,
				TestData.STEP_2,
				issuesSectionPage.areAllElementsPresent()
						&& issuesSectionPage.isWelcomeMessagePresent());

		// 3 step
		IssuePage issuePage = issuesSectionPage.addNewIssue();
		TestData.saveTestResult(TestData.TEST_ADD_ISSUE, TestData.STEP_3,
				issuePage.areNewIssueElementsPresent());

		// 4 step
		String title = "New issue";
		String comment = "Comment";
		issuePage.addIssue(title, comment);
		TestData.saveTestResult(TestData.TEST_ADD_ISSUE, TestData.STEP_4,
				issuePage.IsIssueJustAdded());

		// 5 step
		issuesSectionPage = issuePage.goToIssues();
		TestData.saveTestResult(TestData.TEST_ADD_ISSUE, TestData.STEP_5,
				issuesSectionPage.isIssuePresent(title));

		// 6 step
		homePage = pageNavigator.deleteRepository(driver, issuesSectionPage);
		TestData.saveTestResult(TestData.TEST_ADD_ISSUE, TestData.STEP_6,
				null != homePage);
		homePage.logout();
	}
}
