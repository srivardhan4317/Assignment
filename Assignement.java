package newpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Assignement {
	
	static WebDriver driver;
	
	 String Data = "[{\"name\": \"Bob\", \"age\": 20, \"gender\": \"male\"}, " +
             "{\"name\": \"George\", \"age\": 42, \"gender\": \"male\"}, " +
             "{\"name\": \"Sara\", \"age\": 42, \"gender\": \"female\"}, " +
             "{\"name\": \"Conor\", \"age\": 40, \"gender\": \"male\"}, " +
             "{\"name\": \"Jennifer\", \"age\": 42, \"gender\": \"female\"}]";
	 
	@BeforeClass
	public void setup() throws Exception
	{
		
		System.getProperty("webdriber.chrome.driver", "D:\\eclipse\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");

		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		Thread.sleep(3000);
		
	}
	

  @Test
  public void textbox() throws Exception {
	  
	  
	  WebElement tablebutton = driver.findElement(By.xpath("//*[text()='Table Data']"));
	  tablebutton.click();
	  Thread.sleep(5000);
	    
	  WebElement tabletextbox = driver.findElement(By.xpath("//*[@id='jsondata']"));
	  tabletextbox.clear();
	  Thread.sleep(5000);
	  
	  tabletextbox.sendKeys(Data);
	  Thread.sleep(3000);
	  
	  WebElement Refresh = driver.findElement(By.xpath("//*[@id='refreshtable']"));
	  Refresh.click();
	  Thread.sleep(6000);
	    
  }
  
  @Test(dependsOnMethods = "textbox")
  public void assertDataInTable() {
      WebElement table = driver.findElement(By.id("dynamictable")); 
      
      String tableData = table.getText();

      List<String> lines = new ArrayList<>();
      for (String line : tableData.split("\n")) {
          lines.add(line.replaceAll("\t", " "));
      }

      String tableJsonData = "[";
      for (int i = 2; i < lines.size(); i++) {
          String[] values = lines.get(i).split(" ");
          tableJsonData += "{\"name\": \"" + values[0] + "\", \"age\": " + values[1] + ", \"gender\": \"" + values[2] + "\"}";
          if (i < lines.size() - 1) {
              tableJsonData += ", ";
          }
      }
      tableJsonData += "]";

      Assert.assertEquals(tableJsonData,Data );
      
      
 
  }
  
  @AfterClass
  public void teardown()
  {
	  driver.close();
	  driver.quit();
	  
  }
}
