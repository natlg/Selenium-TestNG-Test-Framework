package com.nat.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NotificationsPage extends Page {

	@FindBy(partialLinkText = "Unread")
	private WebElement unread;

	@FindBy(partialLinkText = "Participating")
	private WebElement participating;

	@FindBy(linkText = "All notifications")
	private WebElement allNotifications;

	public NotificationsPage(WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!"Notifications".equals(driver.getTitle())) {
			throw new IllegalStateException(
					"This is not the Notifications page, this is "
							+ driver.getTitle());
		}
	}

	public boolean isNoticicationsPresent() {
		return isElementPresents(unread) && isElementPresents(participating)
				&& isElementPresents(allNotifications);
	}
}
