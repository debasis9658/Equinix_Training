package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ebaySearch {
	WebDriver driver;
	
	public void launchbrowser() {
//		System.setProperty("webdriver.chrome.driver", "/Users/dbiswal/Desktop/chrome-mac-x64");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.ebay.com/");
		this.driver = driver;
	}
	
	public void searchProduct() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.id("gh-ac")).sendKeys("JBL Speakers");
		driver.findElement(By.id("gh-btn")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Daily Deals")).click();
	}
	
	public void navigate() throws InterruptedException {
		Thread.sleep(2000);
		driver.navigate().to("https://www.google.com/");
		Thread.sleep(3000);
		driver.navigate().back();
		System.out.println("The title of this page is : " + driver.getTitle());
		
	}
	
	public void closeBrowser() {
		driver.quit();
	}
	
	public static void main(String args[]) throws InterruptedException {
		ebaySearch obj = new ebaySearch();
		obj.launchbrowser();
		obj.searchProduct();
		obj.navigate();
		obj.closeBrowser();
		
	}
}
