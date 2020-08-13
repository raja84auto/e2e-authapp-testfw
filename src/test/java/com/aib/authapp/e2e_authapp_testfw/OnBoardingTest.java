package com.aib.authapp.e2e_authapp_testfw;

import org.testng.annotations.Test;

import com.aib.authapp.e2e_authapp_testfw.utils.VideoRecorderUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileBy.ByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;


public class OnBoardingTest extends Base {

	
	@Test
	public void onBoarding_iPhoneTest() throws Exception {
		
		//service = startAppiumServer();		
		Thread.sleep(5000);
		
		IOSDriver<IOSElement> iosDriver = capabilities();
		
		// Alert - Allow
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeButton[`name == \"Allow\"`]").click();
			
		iosDriver.findElementByAccessibilityId("Your identity is safe with AIB Authenticator").isDisplayed();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Next\"`][3]").click();
		
		iosDriver.findElementByAccessibilityId("Two Factor Authentication").isDisplayed();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Next\"`][3]").click();
				
		iosDriver.findElementByAccessibilityId("Correct phone number is very important").isDisplayed();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Get started\"`][3]").click();
				
		//iosDriver.quit();		
	} 

	@Test
	public void onBoarding_androidTest() throws Exception {
		
		//service = startAppiumServer();
		
		AndroidDriver<AndroidElement> androidDriver=capabilities("app-debug.apk");
		androidDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
				
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Your identity is safe with AIB Authenticator')]")).isDisplayed();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Next')]")).click();		
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Two Factor Authentication')]")).isDisplayed();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Next')]")).click();		
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Correct phone number is very important')]")).isDisplayed();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Get started')]")).click();
						
	}
	
}
	
