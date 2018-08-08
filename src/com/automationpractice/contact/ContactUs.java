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

        //Once the "Customer Service" value is selected, a label should be displayed
        poContactUs.selectSubjectHeading("Customer service");
        takeScreenshot("subjectHeadingCustomerService.png",
                "Image of the element");

        //Once the "Webmaster" value is selected, another label should be displayed.
        poContactUs.selectSubjectHeading("Webmaster");
        takeScreenshot("subjectHeadingWebmaster.png",
                "Image of the element");

        quit();
    }

    @BeforeClass
    public void beforeMethod(){
        if(driver == null){
            createDriver();
        }

        test = logger.createTest("Contact US");
        driver.get("http://automationpractice.com/index.php?controller=contact");
        driver.manage().timeouts().pageLoadTimeout(180, java.util.concurrent.TimeUnit.SECONDS);
        takeScreenshot("contactUsHome.png", "Image of the contact page loaded.");
    }

}
