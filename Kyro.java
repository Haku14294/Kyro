package practice;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class Kyro {

    @Test(dataProvider = "Credentials")
    public void kyroWorkflow(String scenario, String username, String password) {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\harish.kumar\\Downloads\\chromedriver.exe");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //load URL
        driver.get("https://kyro.pages.dev/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String pageTitle = driver.getTitle();
        System.out.println(pageTitle);

        //Logging in
        driver.findElement(By.className("LoginButton_login_button__ehTMa")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath(".//input[@id='1-email']")).sendKeys(username);
        driver.findElement(By.xpath(".//input[@id='1-password']")).sendKeys(password);
        driver.findElement(By.xpath(".//span[@class='auth0-label-submit']")).click();


        if (scenario.equals("correctLogin")) {
            WebElement element = driver.findElement(By.xpath(".//div[@id='__next']//span[1][@class='MuiTypography-root" +
                    " MuiTypography-body1 MuiListItemText-primary css-yb0lig'" +
                    " and contains(text(),'Dashboard')]"));
            String message = element.getText();
            Assert.assertEquals(message, "Dashboard");
            element.click();
            Assert.assertTrue(driver.findElement(By.xpath(".//a[@class='viewproject_newproj__j_AAK']")).isDisplayed());

            //selecting the project

            WebElement project = driver.findElement(By.xpath(".//div[@id='__next']/div[1]/div[2]/div[2]/div[1]" +
                    "//div[@class='MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 css-1uxn5ca']"));
            project.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(".//button[@class='MuiButton-root MuiButton-outlined MuiButton-outlinedPrimary " +
                    "MuiButton-sizeMedium MuiButton-outlinedSizeMedium MuiButtonBase-root  css-79xub']"))));

            //Creating a new task
            driver.findElement(By.xpath(".//button[@class='MuiButton-root MuiButton-outlined MuiButton-outlinedPrimary MuiButton-sizeMedium " +
                    "MuiButton-outlinedSizeMedium MuiButtonBase-root  css-79xub']")).click();

            WebElement summary = driver.findElement(By.xpath(".//div/textarea[@name='summary']"));
            summary.sendKeys("Test1");
            String val = summary.getAttribute("value");
            System.out.println("The Value is " + val);
            driver.findElement(By.xpath(".//div/textarea[@name='task_type']")).sendKeys("Manage");
            driver.findElement(By.xpath(".//div/textarea[@name='description']")).sendKeys("Adding a new task");
            driver.findElement(By.xpath(".//div[@class='MuiSelect-select MuiSelect-outlined MuiOutlinedInput-input MuiInputBase-input css-qiwgdb']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.findElement(By.xpath(".//ul[@class='MuiList-root MuiList-padding MuiMenu-list css-r8u8y9']")).click();
            driver.findElement(By.xpath(".//li[@class='MuiMenuItem-root MuiMenuItem-gutters MuiButtonBase-root css-1km1ehz' and @data-value='1']")).click();
            driver.findElement(By.xpath(".//div/textarea[@name='location']")).sendKeys("Chennai");

            //Passing date in date picker
            driver.findElement(By.xpath(".//input[@name='start_date']")).click();
            Date date = new Date();
            String date1 = dateFormat.format(date);
            driver.findElement(By.xpath(".//input[@name='start_date']")).sendKeys(date1);

            driver.findElement(By.xpath(".//input[@name='due_date']")).click();
            String date2 = dateFormat.format(date);
            driver.findElement(By.xpath(".//input[@name='due_date']")).sendKeys(date2);
            driver.findElement(By.xpath(".//button[@class='MuiButton-root MuiButton-contained MuiButton-containedPrimary MuiButton-sizeMedium " +
                    "MuiButton-containedSizeMedium " +
                    "MuiButton-fullWidth MuiButtonBase-root  css-1qelgoy']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            //Getting the added task name from table
            String listVal = driver.findElement(By.xpath(".//div[@class='MuiDataGrid-row MuiDataGrid-row--lastVisible']//div[@data-field='summary']" +
                    "//div[@class='MuiDataGrid-cellContent']")).getAttribute("innerHTML");
            System.out.println(listVal);

            //Validating the value by Asserting
            Assert.assertEquals(val, listVal);
            driver.close();
        }

        if (scenario.equals("incorrectLogin")) {
            WebElement element = driver.findElement(By.xpath(".//input[@id='1-email']"));
            Assert.assertTrue(element.isDisplayed(), "Login has failed");
            driver.close();
        }


    }


    @DataProvider(name = "Credentials")
    public Object[][] getData() {

        return new Object[][]{
                {"correctLogin", "harishkumar.harishkumar@gmail.com", "Hakuma@1415"},
                {"incorrectLogin", "harish@abc.com", "Abc@123"},
        };
    }


}
