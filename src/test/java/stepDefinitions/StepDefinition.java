package stepDefinitions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import selenium.HomePage;
import selenium.ResultsPage;
import selenium.VehicleRegPage;
import service.FileServiceImpl;
import util.ExcelFileReader;


public class StepDefinition {
	WebDriver driver;
	static ExtentReports extent;
	ExtentTest test;
	FileServiceImpl fileServiceImpl;
	ExcelFileReader excelReader;
	
	ArrayList<String> resultMakes;
	ArrayList<String> resultColours;

	
	@Before
	public void setUp() {
		fileServiceImpl = new FileServiceImpl();
		resultMakes = new ArrayList<String>();
		resultColours = new ArrayList<String>();

		System.setProperty("webdriver.chrome.driver", "/Users/nawidmujadidi/Documents/E2e/project/chromedriver");
		extent = new ExtentReports("src/main/resources/extentReport/extent.html", true);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		String url = "https://www.gov.uk/get-vehicle-information-from-dvla";
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.navigate().to(url);
		test = extent.startTest("Testing Gov vehicle enquiry service");	
	}
	
	@Given("^I press start on the mainpage$")
	public void pressStart() {
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		
		homePage.ClickStartBtn(driver);
		test.log(LogStatus.INFO, "Pressed start");
	}
	
	
	@When("^I enter car registration number$")
	public void enterVehicleRegNo() {
		VehicleRegPage vehicleRegPage = PageFactory.initElements(driver, VehicleRegPage.class);
		
		ArrayList<File> fileList = fileServiceImpl.getSupportedFiles("application/vndms-excel");
		excelReader = new ExcelFileReader();
		excelReader.setFile(fileList.get(0));
		ArrayList<String> excelRegData = excelReader.getColumnData("Vehicle Registration");
		
		for(String s: excelRegData.subList(1, excelRegData.size())) {
			vehicleRegPage.inputVehicleReg(s);
			test.log(LogStatus.INFO, "Registration number entered");
			vehicleRegPage.pressConfirm(driver);
			test.log(LogStatus.INFO, "Confirm button pressed");
			ResultsPage resultsPage = PageFactory.initElements(driver, ResultsPage.class);
			resultMakes.add(resultsPage.getMakeValue());
			resultColours.add(resultsPage.getColourValue());
			test.log(LogStatus.INFO, "Results displayed");

			driver.navigate().back();
		}

		
	}

	@Then("^Result details of car match data in file$")
	public void checkDetailsAreShown() {
		System.out.println("testing 123");
		ResultsPage resultsPage = PageFactory.initElements(driver, ResultsPage.class);
		
		ArrayList<String> excelMakeData = excelReader.getColumnData("Make");
		ArrayList<String> excelColourData = excelReader.getColumnData("Colour");
		
		for(int i=0;i<resultMakes.size();i++) {
			System.out.print(i);
			if(!resultMakes.get(i).equals(excelMakeData.get(i+1)) || !resultColours.get(i).equals(excelColourData.get(i+1))){
				fail();
				test.log(LogStatus.FAIL, "Vehicle details do not match ");
			}
		}
		
		assertTrue(true);
		test.log(LogStatus.PASS, "All vehicle details matched");
		
	}
	
	@After
	public void tearDown() {
		driver.quit();
		extent.endTest(test);
		extent.flush();
	}
}
