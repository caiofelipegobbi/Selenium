package com.automationpractice.contact;

import com.automationpractice.utilities.TestSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class ContactUsPageObjects extends TestSetup {

    WebDriver driver;

    @FindBy(xpath = "//div[@class='form-group selector1']/parent::div")
    WebElement formGroupSelector;

    @FindBy (id = "id_contact") WebElement subjectHeading;
    @FindBy (xpath = "//p[normalize-space()='For any question about a product, an order']") WebElement customerServiceMessage;
    @FindBy (xpath = "//p[normalize-space()='If a technical problem occurs on this website']") WebElement webmasterMessage;
    @FindBy (id = "email") WebElement emailTxtBox;
    @FindBy (id = "id_order") WebElement orderReferenceTxtBox;
    @FindBy (id = "fileUpload") WebElement attachFileInput;
    @FindBy (id = "message") WebElement messageTxtArea;
    @FindBy (id = "submitMessage") WebElement sendButton;


    //Constructor for the driver
    public ContactUsPageObjects(WebDriver driverFromController){
        driver = driverFromController;

    }

    protected String selectSubjectHeading(String itemToSelect){
        Select heading = new Select(subjectHeading);
        heading.selectByVisibleText(itemToSelect);
        return heading.getFirstSelectedOption().getText();
    }


    protected boolean checkElementIsDisplayed(WebElement elementToCheck){
        try {
            //Setting a small timeout for which the elements will be searched
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

            if(elementToCheck.isDisplayed()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e ){
            System.out.println("Unknown error trying to check whether an element was being displayed or not.");
            return false;
        }finally {
            //Setting default timeout to 180 seconds again.
            driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
        }

    }

}
