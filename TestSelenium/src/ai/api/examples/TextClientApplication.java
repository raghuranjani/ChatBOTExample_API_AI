/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package ai.api.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

/**
 * Text client reads requests line by line from stdandart input.
 */
public class TextClientApplication {

  private static final String INPUT_PROMPT = "> ";
  /**
   * Default exit code in case of error
   */
  private static final int ERROR_EXIT_CODE = 1;

  /**
   * @param args List of parameters:<br>
   *        First parameters should be valid api key<br>
   *        Second and the following args should be file names containing audio data.
   */
  public static void main(String[] args) {
	  //args[0] = "59aede151e2f4621881b4fa308fd05f3";
   /* if (args.length < 1) {
      showHelp("Please specify API key", ERROR_EXIT_CODE);
    }*/

//    AIConfiguration configuration = new AIConfiguration("59aede151e2f4621881b4fa308fd05f3");
    AIConfiguration configuration = new AIConfiguration("16ac0817a0ea4875a5973731af7cc5f7");

    AIDataService dataService = new AIDataService(configuration);

    String line;

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      System.out.print(INPUT_PROMPT);
      while (null != (line = reader.readLine())) {

        try {
          AIRequest request = new AIRequest(line);

          AIResponse response = dataService.request(request);
          System.out.println("response::"+response.toString());
          System.out.println("getResult::"+response.getResult().toString());
          System.out.println("getparameters::"+response.getResult().getParameters());
          System.out.println("getparameters::"+response.getResult().getMetadata().getIntentId());
          
          if (response.getStatus().getCode() == 200) {
            System.out.println(response.getResult().getFulfillment().getSpeech());
            String searchEngine = "";
            String searchParam = "";
            if(null != response.getResult().getParameters().get("search-engine")){
            	searchEngine = response.getResult().getParameters().get("search-engine").toString();
            	searchEngine = searchEngine.replace("\"", "");
            }else if(null != response.getResult().getParameters().get("service")){
            	searchEngine = response.getResult().getParameters().get("service").toString();
            	searchEngine = searchEngine.replace("\"", "");
            }
            
            if(null != response.getResult().getParameters().get("q")){
            	searchParam = response.getResult().getParameters().get("q").toString();
            	searchParam = searchParam.replace("\"", "");
            }
            System.out.println("searchEngine::"+searchEngine);
      	  System.out.println("searchParam::"+searchParam);
            TextClientApplication.clickscreen(searchEngine,searchParam);
          } else {
            System.err.println(response.getStatus().getErrorDetails());
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }

        System.out.print(INPUT_PROMPT);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    System.out.println("See ya!");
  }
  
  
  

  /**
   * Output application usage information to stdout and exit. No return from function.
   * 
   * @param errorMessage Extra error message. Would be printed to stderr if not null and not empty.
   * 
   */
  private static void showHelp(String errorMessage, int exitCode) {
    if (errorMessage != null && errorMessage.length() > 0) {
      System.err.println(errorMessage);
      System.err.println();
    }

    System.out.println("Usage: APIKEY");
    System.out.println();
    System.out.println("APIKEY  Your unique application key");
    System.out.println("        See https://docs.api.ai/docs/key-concepts for details");
    System.out.println();
    System.exit(exitCode);
  }
  
  public static void clickscreen(String searchEngine,String searchParam) {
	  String sUrl = "http://www.google.com";
	  if(searchEngine.equalsIgnoreCase("Google")){
		  sUrl = "http://www.google.com";
	  }else if (searchEngine.equalsIgnoreCase("bing")){
		  sUrl = "http://www.bing.com";
	  }
	  
	    
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
    	s.click("img/searchText.png",0);
    	s.type(searchParam, 0);
    	s.type(Key.ENTER, 0);
//    	s.click("img/searchButton.png",0);
    }
    catch(Exception e){
        e.printStackTrace();
    }    
  }

}
