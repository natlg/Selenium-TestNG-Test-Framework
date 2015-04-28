package com.nat.test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;

import com.nat.test.utils.ConfigReader;
import com.nat.test.utils.XLSWorker;

/**
 * Class provides data and settings for tests and saves test results
 */
public class TestData {
	private static final ConfigReader configReader = new ConfigReader();
	public static String BROWSER = configReader.getBrowser();
	public static String FIREFOX_PATH = configReader.getFirefoxPath();
	public static final String BASE_URL = "https://github.com/";
	private static WebDriver driver;
	public static final String LOGIN = configReader.getLogin();
	public static final String PASSWORD = configReader.getPassword();
	public static final boolean USE_ASSERT = configReader.isUseAssert();
	public static final int WAIT_TIME = 10;
	public static final int TEST_LOGIN = 1;
	public static final int TEST_SEARCH = 2;
	public static final int TEST_NOTIFICATIONS = 3;
	public static final int TEST_ADD_REPOSITORY = 4;
	public static final int TEST_DELETE_REPOSITORY = 5;
	public static final int TEST_ADD_ISSUE = 6;
	public static final int STEP_1 = 1;
	public static final int STEP_2 = 2;
	public static final int STEP_3 = 3;
	public static final int STEP_4 = 4;
	public static final int STEP_5 = 5;
	public static final int STEP_6 = 6;
	public static final int STEP_7 = 7;
	public static final String SHEET_NAME_TEST_CASE = "TestCases";
	public static final String SHEET_NAME_TEST_DATA = "TestData";
	private final static String xlsPath = "xls\\TestCases.xlsx";
	private static XLSWorker xlsWorker = new XLSWorker(xlsPath);
	private static final int startRowNumTest1 = 1;
	private static final int startRowNumTest2 = 6;
	private static final int startRowNumTest3 = 8;
	private static final int startRowNumTest4 = 10;
	private static final int startRowNumTest5 = 15;
	private static final int startRowNumTest6 = 18;

	/**
	 * Saves test results to the excel file (passed or fail)
	 *
	 * @param testCase
	 *            Test case
	 * @param step
	 *            Step of the test case
	 * @param passed
	 *            Test result
	 */
	public static void saveTestResult(int testCase, int step, boolean passed) {
		switch (testCase) {
		case TEST_LOGIN:
			xlsWorker.setCellData(TestData.SHEET_NAME_TEST_CASE, "Result", step
					+ startRowNumTest1, passed ? "Passed" : "Fail");
			break;
		case TEST_SEARCH:
			xlsWorker.setCellData(TestData.SHEET_NAME_TEST_CASE, "Result", step
					+ startRowNumTest2, passed ? "Passed" : "Fail");
			break;
		case TEST_NOTIFICATIONS:
			xlsWorker.setCellData(TestData.SHEET_NAME_TEST_CASE, "Result", step
					+ startRowNumTest3, passed ? "Passed" : "Fail");
			break;
		case TEST_ADD_REPOSITORY:
			xlsWorker.setCellData(TestData.SHEET_NAME_TEST_CASE, "Result", step
					+ startRowNumTest4, passed ? "Passed" : "Fail");
			break;
		case TEST_DELETE_REPOSITORY:
			xlsWorker.setCellData(TestData.SHEET_NAME_TEST_CASE, "Result", step
					+ startRowNumTest5, passed ? "Passed" : "Fail");
			break;
		case TEST_ADD_ISSUE:
			xlsWorker.setCellData(TestData.SHEET_NAME_TEST_CASE, "Result", step
					+ startRowNumTest6, passed ? "Passed" : "Fail");

		default:
			System.out.println("No Test Case with number " + testCase
					+ " is found in the file");
			break;
		}
		if (USE_ASSERT) {
			Assert.assertTrue(passed);
		}
	}

	/**
	 * Saves test results from certain test data to the excel file (passed or
	 * fail)
	 *
	 * @param testCase
	 *            Test case
	 * @param passed
	 *            Test result
	 * @param data
	 *            Test data
	 */
	public static void saveTestResultWithData(int testCase, boolean passed,
			String data) {
		switch (testCase) {
		case TEST_LOGIN:
			break;
		case TEST_SEARCH:
			xlsWorker.setCellData(TestData.SHEET_NAME_TEST_DATA, "testSearch",
					data, passed ? "Passed" : "Fail");
			break;
		case TEST_NOTIFICATIONS:
			break;
		case TEST_ADD_REPOSITORY:
			break;
		case TEST_DELETE_REPOSITORY:
			break;
		case TEST_ADD_ISSUE:
			break;

		default:
			System.out.println("No Test Case with number " + testCase
					+ " is found in the file");
			break;
		}
	}

	/**
	 * Method to get the data for dataProvider from the file
	 * 
	 ** @param testCase
	 *            Test case
	 *
	 * @return array of data
	 */
	public static Object[][] getSearchData(String testCase) {
		try {
			return xlsWorker.getDataForTest(testCase);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't read the test data");
			return null;
		}

	}

	/**
	 * Method to get the driver. WebDriver type is determined in
	 * config.properties. By the default returns an instance of
	 * {@link FirefoxDriver}
	 *
	 * @return driver
	 */
	public static synchronized WebDriver getDriver() {
		if (null == driver) {
			switch (BROWSER) {
			case "FIREFOX":
				if (!FIREFOX_PATH.equals("")) {
					try {
						File pathToProfile = new File(FIREFOX_PATH);
						FirefoxProfile profile = new FirefoxProfile(
								pathToProfile);
						driver = new FirefoxDriver(profile);
					} catch (Exception e) {
						e.printStackTrace();
						driver = new FirefoxDriver();
					}
				} else {
					driver = new FirefoxDriver();
				}
				break;
			case "CHROME":
				String chromepath = "libs\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromepath);
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				break;

			default:
				driver = new FirefoxDriver();
				break;
			}
			driver.manage().timeouts()
					.implicitlyWait(WAIT_TIME, TimeUnit.SECONDS);
		}
		return driver;
	}
}
