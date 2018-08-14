package com.automationpractice.contact;

import com.automationpractice.utilities.TestSetup;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.openqa.selenium.support.PageFactory;

public class ContactUs extends TestSetup {
    //Initialize elements from the pageObjects
    ContactUsPageObjects poContactUs;


    @Test
    public void contactUsMain(){
        //Initialize the elements from the Page Objects.
        poContactUs = PageFactory.initElements(driver, ContactUsPageObjects.class);

        //Checking all elements were displayed correctly
        checkFormElements();

        quit();
    }

    public void checkFormElements(){
        nodeLog = test.createNode("Checking all form elements are displayed.");

        //Once the "Customer Service" value is selected, a label should be displayed
        poContactUs.selectSubjectHeading("Customer service");
        if(poContactUs.checkElementIsDisplayed(poContactUs.customerServiceMessage)){
            nodeLog.pass("\"For any question about a product, an order \" message was displayed correctly.");
        }else{
            nodeLog.fail("\"For any question about a product, an order \" message was not displayed correctly.");
        }
        takeScreenshotNode("subjectHeadingCustomerService.png",
                "Image of the element after selecting Customer Service", nodeLog);

        //Once the "Webmaster" value is selected, another label should be displayed.
        poContactUs.selectSubjectHeading("Webmaster");
        if(poContactUs.checkElementIsDisplayed(poContactUs.webmasterMessage)){
            nodeLog.pass("\"If a technical problem occurs on this website \" message was displayed correctly.");

        }else{
            nodeLog.fail("\"If a technical problem occurs on this website \" message was not displayed correctly.");
        }
        takeScreenshotNode("subjectHeadingWebmaster.png",
                "Image of the element after selecting Webmaster", nodeLog);

        //Checking the rest of mandatory fields are being displayed on the page
        if(poContactUs.checkElementIsDisplayed(poContactUs.emailTxtBox)){
            nodeLog.pass("Email text box was displayed correctly.");
        }else{
            nodeLog.fail("Email text box was not displayed correctly.");
        }

        if(poContactUs.checkElementIsDisplayed(poContactUs.orderReferenceTxtBox)){
            nodeLog.pass("Order Reference Text Box was displayed correctly.");
        }else{
            nodeLog.fail("Order Reference Text Box was not displayed correctly.");
        }


        if(poContactUs.checkElementIsDisplayed(poContactUs.messageTxtArea)){
            nodeLog.pass("Message Text Area was displayed correctly");
        }else{
            nodeLog.fail("Message Text Area was not displayed correctly");
        }

        if(poContactUs.checkElementIsDisplayed(poContactUs.sendButton)){
            nodeLog.pass("Send Button was displayed correctly.");
        }else{
            nodeLog.fail("Send Button was not displayed correctly.");
        }

    }

    @BeforeClass
    public void beforeMethod(){
        if(driver == null){
            createDriver();
        }

        test = logger.createTest("Contact US");
        driver.get("http://automationpractice.com/index.php?controller=contact");
        driver.manage().timeouts().pageLoadTimeout(180, java.util.concurrent.TimeUnit.SECONDS);
    }

}
