package com.automationpractice.contact;

import com.automationpractice.utilities.TestSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;

public class ContactUsPageObjects extends TestSetup {

    WebDriver driver;

    @FindBy(xpath = "//div[@class='form-group selector1']/parent::div")
    WebElement formGroupSelector;

    @FindBy(id = "id_contact")
    WebElement subjectHeading;

    @FindBy(xpath = "//p[normalize-space()='For any question about a product, an order']")
    WebElement customerServiceMessage;

    @FindBy(xpath = "//p[normalize-space()='If a technical problem occurs on this website']")
    WebElement webmasterMessage;

    //Constructor for the driver
    public ContactUsPageObjects(WebDriver driverFromController){
        driver = driverFromController;

    }

    protected String selectSubjectHeading(String itemToSelect){
        Select heading = new Select(subjectHeading);
        heading.selectByVisibleText(itemToSelect);
        return heading.getFirstSelectedOption().getText();
    }

}
