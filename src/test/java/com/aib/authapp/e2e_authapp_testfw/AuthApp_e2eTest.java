package com.aib.authapp.e2e_authapp_testfw;

import org.testng.annotations.Test;

import com.aib.authapp.e2e_authapp_testfw.utils.VideoRecorderUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileBy.ByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.AndroidKey;



public class AuthApp_e2eTest extends Base {

	KeyEvent key = new KeyEvent(AndroidKey.NUMPAD_ENTER);
	AppiumDriver appiumDriver;
	TouchAction touch, touchAction;
	Dimension dimension;
	
	
	@Test
	public void authApp_e2e_iPhoneTest() throws Exception {
		
		//service = startAppiumServer();		
		Thread.sleep(5000);
		
		IOSDriver<IOSElement> iosDriver = capabilities();
		
		// Alert - Allow
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeButton[`name == \"Allow\"`]").click();
		System.out.println("IOS Test : Alert - accepted");
		
		// onboarding
		iosOnBoarding(iosDriver);
		
		iosPopup(iosDriver);
		
		// Show/Hide T&Cs
		iosTermsAndConditions(iosDriver);
		
		   
		// add card
		iosAddCardNumber(iosDriver);		
		
		// Add business mobile number
		iosAddBusinessMobNumber_sameAsPersonal(iosDriver);		
		iosAddBusinessMobNumber(iosDriver);
		
		// sms-otc card
		ios6DigitSmsOtc_Negative(iosDriver);
		// 6digit sms code - happy path	
		ios6DigitSmsOtc_Happy(iosDriver);
	} 

	@Test
	public void authApp_e2e_androidTest() throws Exception {
		
		//service = startAppiumServer();
		
		AndroidDriver<AndroidElement> androidDriver=capabilities("app-debug.apk");
		androidDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		System.out.println("Android Test - AuthAPP started");
			
		
		// on boarding
		androidOnBoarding(androidDriver);
		
		//androidDriver.findElement(By.xpath("//*[contains(@text, 'Use a Mock User')]")).isDisplayed();
		androidPopup(androidDriver);		
		
		// Show/Hide T&Cs
		androidTermsAndConditions(androidDriver);				
		
		// Add card
		androidAddCard(androidDriver);
		
		// Add business mobile number
		androidBusinessNumberSameAsPersonalNumber(androidDriver);
		androidAddBusinessMobileNumber(androidDriver);
	
		// enter incorrect sms
		androidEnterIncorrectSmsOtc(androidDriver);
		
		// enter correct sms
		androidEnterCorrectSmsOtc(androidDriver);
		
		androidDriver.pressKey(key.withKey(AndroidKey.HOME));
		
		
	}
	
	public void scrollDown(AppiumDriver appiumDriver) throws InterruptedException {
		dimension = appiumDriver.manage().window().getSize();
	    int start_x1 = (int) (dimension.width * 0.5);
	    int start_y1 = (int) (dimension.height * 0.8);
	    
	    int start_x2 = (int) (dimension.width * 0.2);
	    int start_y2 = (int) (dimension.height * 0.2);
	    	    
	    Thread.sleep(1000);
	    touch = new TouchAction(appiumDriver);
	    touch.press(PointOption.point(start_x1, start_y1))
	    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
	    .moveTo(PointOption.point(start_x2, start_y2)).release().perform();    
	    	
	    Thread.sleep(1000);	
	    
	    
	    //Map<String, Object> argsTCs = new HashMap<>();
		//argsTCs.put("direction", "down");
	    //iosDriver.executeScript("mobile: scroll", argsTCs);
	}
	
	public void iosAddBusinessMobNumber_sameAsPersonal(IOSDriver iosDriver) {
		// Add mobile ph number
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeButton[`label == \"Preferences, tab, 3 of 4\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"Add Mobile Number Screen\"`][2]").click();
					
				
				System.out.println("Add mobile number - Start");
				iosDriver.findElementByAccessibilityId("Mobile Number").isDisplayed();
				// **/XCUIElementTypeOther[`label == "Business Mobile Business Mobile Business Mobile"`][5]/XCUIElementTypeOther[2]/XCUIElementTypeTextField
				
				
				//iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"?\"`]").click();
				
				//iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Business Mobile Business Mobile Business Mobile\"`][5]/XCUIElementTypeOther[2]/XCUIElementTypeTextField").click();
				
				// business mobile same as personal mobile
				
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeSwitch[`value == \"0\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Next\"`][4]").click();
				System.out.println("Business mobile same as personal mobile - End");
	}
	
	public void iosAddBusinessMobNumber(IOSDriver iosDriver) {
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"Add Mobile Number Screen\"`][2]").click();
				
		iosDriver.findElementByAccessibilityId("Mobile Number").isDisplayed();
		
		selectCountryCode(iosDriver);
		
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Business Mobile Business Mobile Business Mobile\"`][5]/XCUIElementTypeOther[2]/XCUIElementTypeTextField").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"1\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"2\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"0\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"Delete\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"4\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"6\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"3\"`]").click();		
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"0\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"Delete\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"5\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"8\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"7\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"9\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeButton[`name == \"Done\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Next\"`][4]").click();
		
		System.out.println("Add mobile number - End");
	}
	
	public void selectCountryCode(IOSDriver iosDriver) {
		System.out.println("Country select - start");
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"\"`][2]").click();
		
		TouchAction touch = new TouchAction(iosDriver);
		touch.tap(PointOption.point(75, 75)).perform();		
		
		//iosDriver.findElementByIosClassChain("**/XCUIElementTypeTextField[`name == \"searchInput\"`]").click();
		
		// clear - test done
		//iosClearContryCodeTest(iosDriver);
		
		
		// close
		// **/XCUIElementTypeOther[`label == "Close"`]
		
		//country to sleect
		//**/XCUIElementTypeOther[`label == "  Clear Close "`]/XCUIElementTypeOther[2]/XCUIElementTypeOther
		
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeTextField[`name == \"searchInput\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"I\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"r\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"l\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"delete\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"e\"`]").click();
		//iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"l\"`]").click();
		//iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"a\"`]").click();
		//iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"n\"`]").click();
		//iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"d\"`]").click();
		
		
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"  Clear Close \"`]/XCUIElementTypeOther[2]/XCUIElementTypeOther").click();
		
		
//		Map<String, Object> argsContry = new HashMap<>();
//		argsContry.put("direction", "down");
//		iosDriver.executeScript("mobile: scroll", argsContry);		
//		iosDriver.executeScript("mobile: scroll", argsContry);
//		iosDriver.executeScript("mobile: scroll", argsContry);
//		
//		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"Angola\"`]").click();
		System.out.println("Country select - end");
	}
	
	public void iosClearContryCodeTest(IOSDriver iosDriver) {
		// clear
				// **/XCUIElementTypeButton[`label == "Clear"`]
				System.out.println("Country select clear - start");
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeTextField[`name == \"searchInput\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"U\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"n\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"i\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"t\"`]").click();		
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"e\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"d\"`]").click();
				
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"space\"`]").click();
				
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"k\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"i\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"n\"`]").click();
				
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeButton[`label == \"Clear\"`]").click();
				System.out.println("Country select clear - end");
	}
	
	public void iosAddCardNumber(IOSDriver iosDriver) throws InterruptedException {
		// add card
	    
	    System.out.println("Add first card - Start");
	    iosDriver.findElementByIosClassChain("**/XCUIElementTypeButton[`label == \"Cards, tab, 1 of 4\"`]").click();
	   
	    scrollDown(iosDriver);
	    
	    
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"TAP TO ADD CARD\"`][1]").click();
		
		// enter card number
		iosDriver.findElementByAccessibilityId("Enter Card Number").isDisplayed();	    
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"1\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"3\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"5\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"7\"`]").click();
		
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"9\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"2\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"4\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"6\"`]").click();
				
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"8\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"6\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"0\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"0\"`]").click();
				
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"1\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"2\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"3\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"4\"`]").click();
				
	    scrollDown(iosDriver);
	    Thread.sleep(500);
	    
		// iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"Next\"`]").click();
	    iosDriver.navigate().back();
	    Thread.sleep(1000);
	    System.out.println("Add first card - end");
		
		
	}
	
	public void iosOnBoarding(IOSDriver iosDriver) throws InterruptedException {
		System.out.println("Onboarding - Start");
		
		iosDriver.findElementByAccessibilityId("Your identity is safe with AIB Authenticator").isDisplayed();
		Thread.sleep(1000);
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Next\"`][3]").click();
		
		iosDriver.findElementByAccessibilityId("Two Factor Authentication").isDisplayed();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Next\"`][3]").click();
				
		iosDriver.findElementByAccessibilityId("Correct phone number is very important").isDisplayed();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Get started\"`][3]").click();
		System.out.println("Onboarding - End");
		
	}
	
	public void iosPopup(IOSDriver iosDriver) {
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Use a Mock User\"`]").click();
		System.out.println("Use a mock user - clicked");
		
	}
	
	public void iosTermsAndConditions(IOSDriver iosDriver) throws InterruptedException {
		System.out.println("Terms & Conditions - Start");
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeButton[`label == \"Profile, tab, 2 of 4\"`]").click();
	    iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Show/Hide T&Cs\"`]").click();
			    
	    scrollDown(iosDriver);
	    
	    iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"I accept\"`]").click();
	    System.out.println("Terms & Conditiuons - End");
	}
	
	public void ios6DigitSmsOtc_Negative(IOSDriver iosDriver) {
		// sms-otc card
				System.out.println("6 digit SMS otc - negative test Start");
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeButton[`label == \"Preferences, tab, 3 of 4\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"SMS OTC Screen\"`][3]").click();
				
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`name == \"6\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`name == \"5\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"7\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"Delete\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"Delete\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"Delete\"`]").click();
				
				//sms - negative
				
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`name == \"1\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`name == \"1\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"1\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"1\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"0\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"Delete\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"1\"`]").click();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"1\"`]").click();
				//**/XCUIElementTypeKey[`label == "Delete"`]
				
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeStaticText[`label == \"Oops... something went wrong\"`]").isDisplayed();
				iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"I'll do that later\"`]").click();
				System.out.println("6 digit sms OTC - negative test end");
	}
	
	public void ios6DigitSmsOtc_Happy(IOSDriver iosDriver) {
		System.out.println("6digit sms OTC - happy path Start");
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`name == \"SMS OTC Screen\"`][3]").click();
				
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"0\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"0\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"0\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"1\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"Delete\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"0\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"0\"`]").click();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeKey[`label == \"0\"`]").click();
			
		// **/XCUIElementTypeOther[`label == "Resend code"`][3]	
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeStaticText[`label == \"Card Successfully added\"`]").isDisplayed();
		iosDriver.findElementByIosClassChain("**/XCUIElementTypeOther[`label == \"Finish\"`]").click();
		
		System.out.println("6digit sms OTC - happy path end");
	}
	
	public void androidOnBoarding(AndroidDriver androidDriver) {
		System.out.println("Onboarding - start");
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Your identity is safe with AIB Authenticator')]")).isDisplayed();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Next')]")).click();		
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Two Factor Authentication')]")).isDisplayed();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Next')]")).click();		
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Correct phone number is very important')]")).isDisplayed();
		//androidDriver.findElement(By.xpath("android.view.ViewGroup[@index=‘2’]")).click();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Get started')]")).click();
		System.out.println("Onboarding - End");
	}
	
	public void androidPopup(AndroidDriver androidDriver) {
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text='Use a Mock User']")).click();
		
	}
	
	public void androidTermsAndConditions(AndroidDriver androidDriver) throws InterruptedException {
		System.out.println("Terms & Conditions - Start");
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Profile')]")).click();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Show/Hide T&Cs')]")).click();
		Thread.sleep(1500);
		
		/*
		dimension = androidDriver.manage().window().getSize();
		int start_x1 = (int) (dimension.width * 0.5);
		int start_y1 = (int) (dimension.height * 0.8);
			    
		int start_x2 = (int) (dimension.width * 0.2);
		int start_y2 = (int) (dimension.height * 0.2);
		Thread.sleep(1000);	    	    
			    
		touch = new TouchAction(androidDriver);
		touch.press(PointOption.point(start_x1, start_y1))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			.moveTo(PointOption.point(start_x2, start_y2)).release().perform(); 
		*/
		
		scrollDown(androidDriver);
		androidDriver.findElement(By.xpath("//*[contains(@text, 'I accept')]")).click();
		System.out.println("Terms & Conditiuons - End");
	}
	
	public void androidAddCard(AndroidDriver androidDriver) throws InterruptedException {
		System.out.println("Add 1st Card - start");
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Cards')]")).click();
		scrollDown(androidDriver);
		androidDriver.findElement(By.xpath("//*[contains(@text, 'TAP TO ADD CARD')]/following-sibling::android.view.ViewGroup")).click();
				
		System.out.println("Enter card number - start");
		androidDriver.findElement(By.xpath("//*[contains(@text, '4')]")).click();
		androidDriver.findElement(By.xpath("//*[contains(@text, '2')]")).click();
		Thread.sleep(500);
		androidDriver.findElement(By.xpath("*//android.view.ViewGroup[@index=19]")).click();
		Thread.sleep(500);
		androidDriver.findElement(By.xpath("//*[contains(@text, '8')]")).click();		
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=3]")).click();
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=0]")).click();
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=2]")).click();
		
		
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=1]")).click();	
		androidDriver.findElement(By.xpath("//*[contains(@text, '5')]")).click();
		androidDriver.findElement(By.xpath("//*[contains(@text, '6')]")).click();
		androidDriver.findElement(By.xpath("//*[contains(@text, '7')]")).click();
		
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=8]")).click();
		//androidDriver.findElement(By.xpath("//*[contains(@text, '8')]")).click();		
		androidDriver.findElement(By.xpath("//*[contains(@text, '9')]")).click();
		//androidDriver.findElement(By.xpath("//*[contains(@text, '0')]")).click();
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=0]")).click();
		
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=1]")).click();
		
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=4]")).click();
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=5]")).click();
		Thread.sleep(500);
		androidDriver.findElement(By.xpath("*//android.view.ViewGroup[@index=19]")).click();
		Thread.sleep(500);
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=6]")).click();
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=7]")).click();				
		
		/*
		try {
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Next\"))");
			
		}catch(Exception ex) {
			System.out.println(ex +"exception occured for scroll to next");
		} */
		/*
		dimension = androidDriver.manage().window().getSize();
		int start_xx1 = (int) (dimension.width * 0.5);
		int start_yy1 = (int) (dimension.height * 0.8);
			    
		int start_xx2 = (int) (dimension.width * 0.2);
		int start_yy2 = (int) (dimension.height * 0.2);
		Thread.sleep(1000); 	
		
		
			    
		touch = new TouchAction(androidDriver);
		touch.press(PointOption.point(start_xx1, start_yy1))
			.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
			.moveTo(PointOption.point(start_xx2, start_yy2)).release().perform(); 
		
		Thread.sleep(500);
		*/
		
		scrollDown(androidDriver);
		
		androidDriver.navigate().back();		
		// androidDriver.findElement(By.xpath("//*[contains(@text, 'Next')]")).click();
		
		System.out.println("Enter card number - End");
		
	}
	
	public void androidEnterIncorrectSmsOtc(AndroidDriver androidDriver) {
		System.out.println("6 digit SMS OTC Code - Negative path start");
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Preferences')]")).click();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'SMS OTC Screen')]")).click();
				
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text='6-digit SMS code']")).isDisplayed();
		//androidDriver.findElement(By.xpath("*//android.widget.TextView[contains(@text='send you the code to xxx xx xxx 6533'])")).isDisplayed();
		androidDriver.pressKey(key.withKey(AndroidKey.DIGIT_1));		
		androidDriver.pressKey(key.withKey(AndroidKey.DIGIT_1));
		androidDriver.pressKey(key.withKey(AndroidKey.DIGIT_1));
		androidDriver.pressKey(key.withKey(AndroidKey.DIGIT_1));
		androidDriver.pressKey(key.withKey(AndroidKey.DIGIT_1));		
		androidDriver.pressKey(key.withKey(AndroidKey.DIGIT_1));
		androidDriver.pressKey(key.withKey(AndroidKey.DIGIT_1));

		androidDriver.navigate().back();
		
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Oops... something went wrong')]")).click();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'do that later')]")).click();
		System.out.println("6 digit SMS OTC Code - Negative path end");
	}
	
	public void androidEnterCorrectSmsOtc(AndroidDriver androidDriver) {
		System.out.println("6 digit SMS OTC Code - Happy path start");
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Preferences')]")).click();
		
		androidDriver.findElement(By.xpath("//*[contains(@text, 'SMS OTC Screen')]")).click();
		
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text='6-digit SMS code']")).isDisplayed();
		//androidDriver.findElement(By.xpath("*//android.widget.TextView[contains(@text='send you the code to xxx xx xxx 6533'])")).isDisplayed();
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
		
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
		
		androidDriver.navigate().back();
		System.out.println("6 digit SMS OTC Code - Happy path end");
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Finish')]")).click();
		System.out.println("Sucessfully added 1st card - end");
	}
	
	public void androidAddBusinessMobileNumber(AndroidDriver androidDriver) throws InterruptedException {
		System.out.println("Add mobile number - start");
		
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Preferences')]")).click();
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Add Mobile Number Screen')]")).click();
		
		// country = 
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text='']")).click();		
		androidDriver.findElement(By.xpath("*//android.widget.EditText[@text='Country']")).click();
		Thread.sleep(2000);
		androidDriver.findElement(By.xpath("*//android.widget.EditText[@text='Country']")).click();
		Thread.sleep(1000);
		androidDriver.pressKey(new KeyEvent(AndroidKey.U));
		androidDriver.pressKey(new KeyEvent(AndroidKey.N));
		androidDriver.pressKey(new KeyEvent(AndroidKey.I));
		androidDriver.pressKey(new KeyEvent(AndroidKey.T));
		androidDriver.pressKey(new KeyEvent(AndroidKey.E));
		androidDriver.pressKey(new KeyEvent(AndroidKey.D));
		
		androidDriver.pressKey(new KeyEvent(AndroidKey.SPACE));
		
		androidDriver.pressKey(new KeyEvent(AndroidKey.K));
		androidDriver.pressKey(new KeyEvent(AndroidKey.I));
		androidDriver.pressKey(new KeyEvent(AndroidKey.N));
		androidDriver.pressKey(new KeyEvent(AndroidKey.G));
		Thread.sleep(1000);
		androidDriver.findElement(By.xpath("//*[contains(@text, 'United Kingdom')]")).click();
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text='Business Mobile']")).click();
		Thread.sleep(1000);
		
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_7));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_5));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));	
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_6));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_9));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_4));	
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_8));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DEL));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
		androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
		Thread.sleep(1000);
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text='Next']")).click();
		System.out.println("Add mobile number - End");
		Thread.sleep(1500);
		
		// clear
		//androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=Clear]")).click();		
		//androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=Close]")).click();		
				
	}
	
	public void androidBusinessNumberSameAsPersonalNumber(AndroidDriver androidDriver) {
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Preferences')]")).click();
		
		androidDriver.findElement(By.xpath("//*[contains(@text, 'Add Mobile Number Screen')]")).click();
		
		
		//androidDriver.findElement(By.xpath("*//android.widget.TextView[@text=Mobile Number]")).isDisplayed();
		System.out.println("Business moile same as personal mobile - start");
		androidDriver.findElement(By.xpath("*//android.widget.Switch[@index=4]")).click();
		androidDriver.findElement(By.xpath("*//android.widget.TextView[@text='Next']")).click();
		System.out.println("Business moile same as personal mobile - end");
		
	}
	
	
	
}
	

