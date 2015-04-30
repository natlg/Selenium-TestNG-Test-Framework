package com.nat.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Class represents page with notifications
 */
public class NotificationsPage extends Page {

	@FindBy(partialLinkText = "Unread")
	private WebElement unread;

	@FindBy(partialLinkText = "Participating")
	private WebElement participating;

	@FindBy(linkText = "All notifications")
	private WebElement allNotifications;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public NotificationsPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Method checks if all notification sections present
	 * 
	 * @return True if Unread, Participating and All notifications sections
	 *         present
	 */
	public boolean isNoticicationsPresent() {
		return isElementPresents(unread) && isElementPresents(participating)
				&& isElementPresents(allNotifications);
	}

	/**
	 * Method to get expected page title
	 *
	 * @return expected page title
	 */
	@Override
	public String getExpectedTitle() {
		return "Notifications";
	}
}
