import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Addtocart {
    ChromeDriver driver;

    @Before
    public void Before() {
        System.out.println("Start");
        //Khai bao webdriver
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chrome/chromedriver.exe");
        //Khoi tao webriver
        driver = new ChromeDriver();
        //Su dung phuong thuc voi webdriver
        driver.get("https://au-webhook-adc1.onshopbase.com/");
        driver.manage().window().maximize();
    }
    @Test
    public void Test() throws InterruptedException {
        System.out.println("Start");
        //Sellect shop
        clickElement("//button[@aria-label='Open search panel']");
        inputToElement("//input[@id='search']","Shirt");
        clickElement("(//a[contains(text(),'Shirt')])[3]");
        //Add to cart
        clickElement("//button[@id='add-to-cart']");
        //verify card
        Assert.assertEquals("Shirt", getElementText("//a[contains(text(),'Shirt') and @class='no-underline']"));
        //Assert.assertEquals("1", getElementText("//input[@class='quantity__num']"));
        Assert.assertEquals("$10.00", getElementText("//span[@class='weight-700 lh24']"));
        Assert.assertEquals("$10.00", getElementText("//p[@class='lh24 m0 weight-700 cart__subtotal-price']"));
        //Checkout
        clickElement("//button[@name='checkout']");
        inputToElement("//input[@type='email']", "doanminhthai88@gmail.com");
        inputToElement("//input[@name='first-name']", "Minh");
        inputToElement("//input[@name='last-name']", "Thai");
        inputToElement("//input[@name='street-address']", "Manhattan");
        inputToElement("//input[@name='apartment-number']", "888");
        inputToElement("//input[@name='city']", "An Giang");
        clickElement("//select[@name='countries']//option[@value='VN']");
        inputToElement("//input[@name='zip-code']", "88000");
        inputToElement("//input[@name='phone-number']", "0123456");
        //Shipping method
        clickElement("//button[contains(normalize-space(),'Continue to shipping method')]");
        clickElement("//span[@class='s-check']");
        //Discount
        inputToElement("//input[@placeholder='Enter your discount code here']", "testing");
        clickElement("//button[normalize-space()='Apply']");

        //Payment method
        clickElement("//button[contains(normalize-space(),'Continue to payment method')]");
        inputToElement("//input[@placeholder='Cardholder name']", "DOAN MINH THAI");
        driver.switchTo().frame(0);
        inputToElement("//input[@name='cardnumber' and @placeholder='Card number']", "4242 4242 4242 4242");
        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        inputToElement("//input[@name='exp-date' and @placeholder='MM/YY']", "08/22");
        driver.switchTo().parentFrame();
        driver.switchTo().frame(2);
        inputToElement("//input[@name='cvc' and @placeholder='CVV']", "684");
        driver.switchTo().parentFrame();


        clickElement("//button[normalize-space()='Complete order']");
        //Verify order
        Assert.assertEquals("$11.99", getElementText("//span[@class='payment-due__price']"));
        Assert.assertEquals("TESTING", getElementText("//span[@class='reduction-code__text']"));
        Assert.assertEquals("- $5.00", getElementText("(//span[@class='order-summary__emphasis'])[2]"));
        Assert.assertEquals("Minh Thai"+"Manhattan 888 An Giang 88000"+"Vietnam"+"0123456",
                getElementText("//address[@class='address']") +
                getElementText("(//address[@class='address']/br)[1]") +
                getElementText("(//address[@class='address']/br)[2]") +
                getElementText("(//address[@class='address']/br)[3]"));
        //Dashboard
        driver.get("https://accounts.shopbase.com/sign-in");
        //Dang nhap
        inputToElement("//input[@name='email']", "shopbase2@beeketing.net");
        inputToElement("//input[@name='password']", "*esAS!z(:YeZ-5q");
        clickElement("//button[@type='submit']");
        //Select shop
        clickElement("//span[text()='au-webhook-adc1.onshopbase.com']");
        clickElement("//*[@id=\"app\"]/div/div[3]/aside/ul/li[2]/a[1]/div/div");
        inputToElement("(//input[@placeholder='Search orders'])[2]", "Minh Thai");
        clickElement("(//td[@class='cursor-default no-padding-important text-right'])[2]");
        //Check
        Assert.assertEquals("$11.99", getElementText("//td[@class='type--bold']/div"));
        Assert.assertEquals("-$5.00", getElementText("(//table[@role='table']/tbody/tr/td[3]/span)[1]"));
        Assert.assertEquals("Minh Thai", getElementText("(//div[@class='s-flex--fill'])[4]/div/p[1]"));
        Assert.assertEquals("Manhattan", getElementText("(//div[@class='s-flex--fill'])[4]/div/p[2]"));
        Assert.assertEquals("888", getElementText("(//div[@class='s-flex--fill'])[4]/div/p[3]"));
        Assert.assertEquals("An Giang", getElementText("(//div[@class='s-flex--fill'])[4]/div/p[4]"));
        Assert.assertEquals("88000", getElementText("(//div[@class='s-flex--fill'])[4]/div/p[5]"));
        Assert.assertEquals("Vietnam", getElementText("(//div[@class='s-flex--fill'])[4]/div/p[6]"));
        Assert.assertEquals("0123456", getElementText("(//div[@class='s-flex--fill'])[4]/div/p[7]"));
    }
    /*@After
    public void After(){
        driver.quit();
        System.out.println("Finish");
    }*/
    public WebElement getElement(String xpath) {
        return driver.findElement(By.xpath(xpath));

    }

    public void clickElement(String xpath) {
        waitElementVisible(xpath);
        getElement(xpath).click();
    }

    public String getElementText(String xpath) {
        waitElementVisible(xpath);
        return getElement(xpath).getText();
    }

    public void inputToElement(String xpath, String value) {
        waitElementVisible(xpath);
        WebElement e = getElement(xpath);
        e.clear();
        e.sendKeys(value);
    }
    public String getText(String xpath){
        return driver.findElement(By.xpath(xpath)).getText();
    }

    public void waitElementVisible(String xpath) {
        WebDriverWait driverWait = new WebDriverWait(driver, 50);
        driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }
}
