package com.qa.util;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import com.qa.baseclass.Base;

public class TestUtil extends Base{
public void FailTestScreenshot(String Failed_Method_Name) {
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File(".\\screenshot\\"+Failed_Method_Name+".png");
		try {
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		}
}
