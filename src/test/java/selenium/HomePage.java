package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	@FindBy(className = "govuk-button--start")
	WebElement startBtn;
	
	public void ClickStartBtn(WebDriver driver) {
		Actions action = new Actions(driver);
		action.moveToElement(startBtn).click().perform();
	}
	
}
