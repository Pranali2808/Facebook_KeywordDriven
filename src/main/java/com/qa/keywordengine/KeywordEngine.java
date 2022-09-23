package com.qa.keywordengine;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.baseclass.Base;



public class KeywordEngine {
	
	public static WebDriver driver;
	public static Properties prop;

	public static Workbook book;
	public static Sheet sheet;

	public Base base;
	public WebElement element;

	public final String Scenario_sheet_path = "C:\\Users\\ranep\\eclipse-workspace\\Facebook_KeywordDriven\\src\\main\\java\\com\\qa\\keywordfbscenario\\FBScenario.xlsx";

	public void startExecution(String sheetName) throws IOException {
		String locatorName = null;
		String locatorValue = null;
		FileInputStream file = null;
		try {
			file = new FileInputStream(Scenario_sheet_path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		int k = 0;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			try {
				String locatorColValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim();

				if (!locatorColValue.equalsIgnoreCase("NA")) {
					locatorName = locatorColValue.split("=")[0].trim(); // id
					locatorValue = locatorColValue.split("=")[1].trim(); // email
				}
				String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
				String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim();

				switch (action) {

				case "Open browser":
					base = new Base();
					prop = base.initializeProperties();
					if (value.isEmpty() || value.equals("NA")) {
						driver = base.initializeDriver(prop.getProperty("browser"));
					} else {
						driver = base.initializeDriver(value);
					}
					break;

				case "Enter url":
					if (value.isEmpty() || value.equals("NA")) {
						driver.get(prop.getProperty("url"));
					} else {
						driver.get(value);
					}
					break;
				
					case "quit":
					driver.quit();
					break;
				default:
					System.out.println("No Action is defined");
					break;
				}

				switch (locatorName) {
				case "id":
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					}
					locatorName = null;
					break;
				case "partialLinkText":
					element = driver.findElement(By.partialLinkText(locatorValue));
					element.click();
					locatorName = null;
					break;
				default:
					break;
				}
			} catch (Exception e) {
			}
		}
	}
}