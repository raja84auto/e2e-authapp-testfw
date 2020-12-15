package com.aib.authapp.e2e_authapp_testfw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class Base {
	
	//public static IOSDriver<IOSElement> iosDriver = null;
	public static AndroidDriver<AndroidElement> androidDriver = null;
	public static IOSDriver<IOSElement> iosDriver = null;
	
	public static IOSDriver<IOSElement>  capabilities() throws MalformedURLException {
		
		File appDir = new File("srcApk");
		// File apkFile = new File(appDir, getApp);
		File appFile = new File(appDir, "authenticator.app");
		
		DesiredCapabilities caps = new DesiredCapabilities();
		
		/* 
		 * simulator
		 
		//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone SE (2nd generation)");
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.6");
		// ios and iphone version 10.2+
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest"); // 
		// caps.setCapability(MobileCapabilityType.APP, "//Users//macbookpro//Desktop//authenticator.app"); 
		caps.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
		
		URL url = new URL("http://0.0.0.0:4723/wd/hub");
		//iosDriver = new IOSDriver<IOSElement>(url, caps);
		IOSDriver<IOSElement>  iosDriver= new IOSDriver<IOSElement>(url, caps);
		iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS );
		*/
		
		
		
		
		
		/*
		 *  real ios device
		 
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.0.1");
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Raja iPhone 7");
		caps.setCapability(MobileCapabilityType.UDID, "3984287363573182789c4802d9ddf7491ca48b13");  // DNPVQ0U3HG7G
		
		
		// ios and iphone version 10.2+
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest"); // 
		// caps.setCapability(MobileCapabilityType.APP, "//Users//macbookpro//Desktop//authenticator.app"); 
		caps.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
		
		URL url = new URL("http://0.0.0.0:4723/wd/hub");
		//iosDriver = new IOSDriver<IOSElement>(url, caps);
		IOSDriver<IOSElement>  iosDriver= new IOSDriver<IOSElement>(url, caps);
		iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS );
		*/
		
		
		// simulator
		iosDriver = iosSimulator(caps, appFile);
			
		// ios real device
		//iosDriver = iosRealDevice(caps, appFile);
		
		return iosDriver;
		
	} 
	
	public static IOSDriver<IOSElement> iosSimulator(DesiredCapabilities caps, File appFile) throws MalformedURLException {
		
		/* 
		 * simulator
		 */
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");
		//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone SE (2nd generation)");
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.6");
		// ios and iphone version 10.2+
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest"); // 
		// caps.setCapability(MobileCapabilityType.APP, "//Users//macbookpro//Desktop//authenticator.app"); 
		caps.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());
		
		URL url = new URL("http://0.0.0.0:4723/wd/hub");
		//iosDriver = new IOSDriver<IOSElement>(url, caps);
		IOSDriver<IOSElement>  iosDriver= new IOSDriver<IOSElement>(url, caps);
		iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS );
		
		return iosDriver;
		
	}
	
	
	public static IOSDriver<IOSElement> iosRealDevice(DesiredCapabilities caps, File appFile) throws MalformedURLException{
		/*
		 *  real ios device
		 */
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.2");
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Raja iPhone 7");
		caps.setCapability(MobileCapabilityType.UDID, "3984287363573182789c4802d9ddf7491ca48b13");  // DNPVQ0U3HG7G
		caps.setCapability("xcodeOrgId","6CF223BJ9A"); // TeamId ? 6CF223BJ9A
		caps.setCapability("xcodeSigningId", "iPhone Developer");
		caps.setCapability("updateWDABundleId", "ie.aib.authenticator.dev"); // bundle id?
		
		
		// ios and iphone version 10.2+
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest"); // 
		// caps.setCapability(MobileCapabilityType.APP, "//Users//macbookpro//Desktop//authenticator.app"); 
		caps.setCapability(MobileCapabilityType.APP, appFile.getAbsolutePath());  // Xcode > Products > authenticator.app path
		
		URL url = new URL("http://0.0.0.0:4723/wd/hub");
		//iosDriver = new IOSDriver<IOSElement>(url, caps);
		IOSDriver<IOSElement>  iosDriver= new IOSDriver<IOSElement>(url, caps);
		iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS );
		
		return iosDriver;
		
		//Build target authenticator of project authenticator with configuration Debug
		//error: No account for team "6CF223BJ9A". Add a new account in the Accounts preference pane or verify that your accounts have valid credentials. (in target 'authenticator' from project 'authenticator')
		//error: No profiles for 'ie.aib.authenticator.dev' were found: Xcode couldn't find any iOS App Development provisioning profiles matching 'ie.aib.authenticator.dev'. (in target 'authenticator' from project 'authenticator')
		
		
	}
	
	
	public static AndroidDriver<AndroidElement> capabilities(String appName) throws IOException, InterruptedException {
		
		/*
		Properties props = new Properties();
		// /Users/rajamac/eclipse-workspace/AppiumFramework/src/main/java/Properties/global.properties
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/Properties/global.properties");
		props.load(fis);
		String getApp = (String) props.get(appName);
		String getDevice = (String) props.getProperty("androidEmulator");
		
		if(getDevice.contains("AVD")) {
			startEmulator();
			
		} */
		
		
		
		File appDir = new File("srcApk");
		// File apkFile = new File(appDir, getApp);
		File apkFile = new File(appDir, appName);
		
		DesiredCapabilities caps = new DesiredCapabilities();		
		
		// Emulator android
		// androidDriver =  androidEmulator(caps, apkFile);
		
		// Real android 
		androidDriver = androidRealDevice(caps, apkFile);
		
		return androidDriver;
	}
	
	public static AndroidDriver<AndroidElement> androidEmulator(DesiredCapabilities caps, File apkFile) throws MalformedURLException {
		/** 
		 * emulator test
		 */
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2_API_30");
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
		caps.setCapability(MobileCapabilityType.APP, apkFile.getAbsolutePath());
		androidDriver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps); // debug
		
		androidDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return androidDriver;
					
		
	}
	
	
	public static AndroidDriver<AndroidElement> androidRealDevice(DesiredCapabilities caps, File apkFile) throws MalformedURLException {
		
		/* 
		 * Android - Real device
		 */ 		
		 
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
		//caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9");
		//caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Mi A1");
		//caps.setCapability(MobileCapabilityType.UDID, "35c00axxxxxx"); // 12 chars
		
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9");  // 4.4.2
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "moto e6 play"); // Raja M (GT-N7100)
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		caps.setCapability(MobileCapabilityType.UDID, "ZE22259QTF"); // 12 chars // 4df138b7190b7f99
		// imie = , "354666050482553"
		caps.setCapability(MobileCapabilityType.APP, apkFile.getAbsolutePath());
		caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
		//caps.setCapability(MobileCapabilityType.BROWSER_NAME,"Chrome");
			
		URL url = new URL("http://0.0.0.0:4723/wd/hub");			
		AndroidDriver<AndroidElement> androidDriver = new AndroidDriver<>(url, caps);
		androidDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		return androidDriver;
	}

}
