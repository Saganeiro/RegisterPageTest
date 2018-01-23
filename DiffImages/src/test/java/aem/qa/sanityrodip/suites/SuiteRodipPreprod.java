package aem.qa.sanityrodip.suites;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.Cookie;

import aem.qa.sanityrodip.tests.TestRodipPreprod;

import com.cognifide.qa.commons.CognifideWebDriverBackedSelenium;
import com.cognifide.qa.commons.proxy.BrowsermobWrapper;
import com.cognifide.qa.commons.proxy.FirebugWrapper;
import com.cognifide.qa.commons.template.TestTemplate;

@RunWith(Suite.class)

@Suite.SuiteClasses({
		TestRodipPreprod.class
	})
public class SuiteRodipPreprod {
	
	@BeforeClass
	public static void startSelenium() throws Exception {
		TestTemplate.start();
		TestTemplate.getPropertiesFromPom();
		TestTemplate.prepareDirectoriesForSuite();
					
		if (TestTemplate.browser == null || TestTemplate.basePath == null || TestTemplate.capability == null || TestTemplate.resourcePath == null)
		{
			System.err.println("***********************ERROR***********************");
			System.err.println("${browser} property value is:" + TestTemplate.browser);
			System.err.println("${basePath} property value is:" + TestTemplate.basePath);
			System.err.println("${capability} property value is:" + TestTemplate.capability);
			System.err.println("${resourcePath} property value is:" + TestTemplate.resourcePath);
			System.err.println("***********************ERROR***********************");
		}
		else
		{
			System.out.println("Properties sucessfully loaded from POM.");
		}

		TestTemplate.browser.toUpperCase();

		if (TestTemplate.browser.equals("FF"))
		{
			TestTemplate.driver = TestTemplate.creator.getFirefoxDriver(TestTemplate.capability, TestTemplate.resourcePath, TestTemplate.firebugExportPath);			
		}
		else if (TestTemplate.browser.equals("IE"))
		{
			TestTemplate.driver = TestTemplate.creator.getInternetExplorerDriver(TestTemplate.capability);
		}
		else
		{
			throw new Exception("Undefined browser");
		}
		
		// set page load timeout to 3 minutes to make sure all pages are opened in suite and configured properly
		TestTemplate.driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		TestTemplate.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		TestTemplate.selenium = new CognifideWebDriverBackedSelenium(TestTemplate.driver, "");

		if (TestTemplate.capability.equals("browsermob"))
		{
			TestTemplate.proxy = new BrowsermobWrapper(TestTemplate.driver, 9090);			
		}
		else if (TestTemplate.capability.contains("firebug"))
		{
			TestTemplate.proxy = new FirebugWrapper(TestTemplate.driver, TestTemplate.firebugExportPath);
		}
		
		if (TestTemplate.capability.contains("jserrors"))
			TestTemplate.jsChecker = TestTemplate.creator.getJsDetector();
		
		// maximize browser window
		TestTemplate.driver.manage().window().maximize();
		
		// Set up cookies for AT and CH verification bypass to access registration page
		TestTemplate.driver.get("https://preprod-rocheidentity.cs86.force.com/RDP_Registration?country=CH&lang=de_CH");
		TestTemplate.driver.manage().addCookie(new Cookie("apex__edpId", "valueNotImportant"));
		
		// set up known JS issues for domains in this suite	
		TestTemplate.knownJSErrors = new LinkedList<String>();
	}
	
	@AfterClass
	public static void stopSelenium() throws IOException{		
		TestTemplate.shutdown();
	}
}
