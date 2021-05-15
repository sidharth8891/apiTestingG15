package com.automation.BaseTest;


import java.io.IOException;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.utils.Email;
import com.automation.utils.ExtentReport;
import com.automation.utils.Helper;
import com.automation.utils.Logging;
import com.relevantcodes.extentreports.LogStatus;



public class BaseTest {
	
	public static String baseurl="";
	static Date now = new Date();
	public static String TimeStamp = now.toString().replace(":", "-");
	protected static Logging log;
	
	@Parameters({ "ReportName", "FlowType" })
	@BeforeSuite(alwaysRun = true)
	public void config(@Optional("Optional name Automation ") String reportname, @Optional("API Report") String flow)
			throws IOException {
		
        baseurl=Helper.propertyReader(Helper.commonFilePath, "baserurl");
        //Create the path in which we will create folder to keep html reports
		String subfolderpath=System.getProperty("user.dir")+"/htmlReports/"+TimeStamp;
		//create sub folder
		com.automation.utils.Helper.CreateFolder(subfolderpath);
		
		ExtentReport.initialize(subfolderpath+"/"+"API_Automation.html");
		
		// Log path
		com.automation.utils.Logging.setLogPath(subfolderpath+"/"+"Training_logs.log");

		// create logging instance
		log = Logging.getInstance();

	}

	@BeforeMethod(alwaysRun = true)
	public static void LogBeforeMethod() {
		final Logging log = Logging.getInstance();

		log.info("Test case", "*********************************************************************");
	}

	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {
		
		if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentReport.extentlog.log(LogStatus.PASS, "Test case : "+ result.getName()+" is passed ");

		} else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentReport.extentlog.log(LogStatus.FAIL, "Test case : "+ result.getName()+" is failed ");
			ExtentReport.extentlog.log(LogStatus.FAIL, "Test case is failed due to:  " + result.getThrowable());
			
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentReport.extentlog.log(LogStatus.SKIP, "Test case is Skiped " + result.getName());
		}
		ExtentReport.extentreport.endTest(ExtentReport.extentlog);
	}

	@AfterSuite(alwaysRun = true)
	public void endReport() {
		//ExtentReport.extentreport.flush();
		ExtentReport.extentreport.close();
		//Logging.setinstanceNull();
		Email.sendEmail();
	}

}

