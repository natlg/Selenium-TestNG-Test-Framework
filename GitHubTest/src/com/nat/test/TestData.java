package com.nat.test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;

import com.nat.test.pages.LoginPage;
import com.nat.test.utils.ConfigReader;
import com.nat.test.utils.XLSWorker;

/**
 * Class provides data and settings for tests and saves test results
 */
public class TestData {
	private static final ConfigReader configReader = new ConfigReader();
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
	public static final int TEST_REPOSITORY_CREATING = 4;
	public static final int TEST_REPOSITORY_DELETING = 5;
	public static final int STEP_1 = 1;
	public static final int STEP_2 = 2;
	public static final int STEP_3 = 3;
	public static final int STEP_4 = 4;
	public static final int STEP_5 = 5;
	public static final int STEP_6 = 6;
	public static final int STEP_7 = 7;
	public static final String SHEET_NAME = "Test Cases";
	private final static String xlsPath = "xls\\TestCases.xlsx";
	private static XLSWorker xlsWorker = new XLSWorker(xlsPath);

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
		if (USE_ASSERT) {
			Assert.assertTrue(passed);
		}
		switch (testCase) {
		case TestData.TEST_LOGIN:
			xlsWorker.setCellData(TestData.SHEET_NAME, "Result", step + 1,
					passed ? "Passed" : "Fail");
			break;
		case TestData.TEST_SEARCH:
			xlsWorker.setCellData(TestData.SHEET_NAME, "Result", step + 6,
					passed ? "Passed" : "Fail");
			break;
		case TestData.TEST_NOTIFICATIONS:
			xlsWorker.setCellData(TestData.SHEET_NAME, "Result", step + 8,
					passed ? "Passed" : "Fail");
			break;
		case TestData.TEST_REPOSITORY_CREATING:
			xlsWorker.setCellData(TestData.SHEET_NAME, "Result", step + 10,
					passed ? "Passed" : "Fail");
			break;
		case TestData.TEST_REPOSITORY_DELETING:
			xlsWorker.setCellData(TestData.SHEET_NAME, "Result", step + 15,
					passed ? "Passed" : "Fail");
			break;

		default:
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

	// public static void readPropertiesFile() throws IOException {
	// FileInputStream fis = new FileInputStream("data/config.properties");
	// Properties p = new Properties();
	// p.load(fis);
	// System.out.println("LOGIN = " + p.getProperty("LOGIN"));
	// System.out.println("PASSWORD = " + p.getProperty("PASSWORD"));
	// System.out.println("FIREFOX_PATH = " + p.getProperty("FIREFOX_PATH"));
	// }

	/**
	 * Method to get the driver. WebDriver type is determined in
	 * config.properties. By the default returns an instance of
	 * {@link FirefoxDriver}
	 *
	 * @return driver
	 */
	public static synchronized WebDriver getDriver() {
		if (null == driver) {
			if (!FIREFOX_PATH.equals("")) {
				try {
					File pathToProfile = new File(FIREFOX_PATH);
					FirefoxProfile profile = new FirefoxProfile(pathToProfile);
					driver = new FirefoxDriver(profile);
				} catch (Exception e) {
					e.printStackTrace();
					driver = new FirefoxDriver();
				}
			} else {
				driver = new FirefoxDriver();

				// File file = new
				// File("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
				// System.setProperty("webdriver.chrome.driver",
				// file.getAbsolutePath());
				// driver = new ChromeDriver();
			}
			driver.manage().timeouts()
					.implicitlyWait(TestData.WAIT_TIME, TimeUnit.SECONDS);
		}
		return driver;
	}
}
