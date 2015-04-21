package com.nat.test.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private String login = "";
	private String password = "";
	private boolean useAssert = true;
	private String firefoxPath = "";

	public ConfigReader() {
		FileInputStream fis;
		try {
			fis = new FileInputStream("data/config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;

		}
		Properties p = new Properties();
		try {
			p.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			return;

		}
		login = p.getProperty("LOGIN");
		password = p.getProperty("PASSWORD");
		firefoxPath = p.getProperty("FIREFOX_PATH");
		
		try {
			useAssert = Boolean
					.parseBoolean(p.getProperty("USE_ASSERT"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("didnt read useAssert");
		}
		
	}

	public String getFirefoxPath() {
		return firefoxPath;
	}

	public String getLogin() {
		return login;
	}
	
	public boolean isUseAssert() {
		return useAssert;
	}
	
	public String getPassword() {
		return password;
	}

	
	// switch (property) {
	// case "LOGIN":
	// LOGIN = p.getProperty("LOGIN");
	// case "PASSWORD":
	// PASSWORD = p.getProperty("PASSWORD");
	// case "FIREFOX_PATH":
	// FIREFOX_PATH = p.getProperty("FIREFOX_PATH");
	// default:
	// ;
}
