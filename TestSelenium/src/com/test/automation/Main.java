package com.test.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.sikuli.script.Screen;

public class Main
{
  public static void main(String[] args)
  {
    String sUrl = "http://www.java.com/en/download/testjava.jsp";
    
//    System.setProperty("webdriver.chrome.driver", "C://Users//Ranjani//Desktop//UdemyCourse//MyLearning_Practice//SeleniumTestingCourse//installables//chromedriver_win32//chromedriver.exe");
    System.setProperty("webdriver.ie.driver", "C://Users//Ranjani//Desktop//UdemyCourse//MyLearning_Practice//SeleniumTestingCourse//installables//IEDriverServer_x64_3.3.0//IEDriverServer.exe");
	DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
	capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
	capabilities.setCapability("ignoreZoomSetting", true);
	try {
		WebDriver ieDriver = new InternetExplorerDriver(capabilities);
		ieDriver.get(sUrl);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}           
    // Open the main webpage.
    Screen s = new Screen();
    //s.find(PSI);
    //Click on the content of Applet;
    try{
    	s.click("img/VerifyVersion.png",0);
    }
    catch(Exception e){
        e.printStackTrace();
    }    
  }
}
