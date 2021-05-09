import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public class GittiGidiyorTest {

    private WebDriver driver;
    JavascriptExecutor js;
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver" ,"drivers//chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void Test() {
        String mail="gittigidiyorerisim12@gmail.com";
        String sifre="gittigidiyor1234";
        String baseURL = "https://www.gittigidiyor.com/uye-girisi";



        //siteye login olunur, login kontrolü yapılır
        driver.findElement(By.id("L-UserNameField")).sendKeys(mail);
        driver.findElement(By.id("L-PasswordField")).sendKeys(sifre);
        driver.findElement(By.id("gg-login-enter")).click();
        Assert.assertEquals("Login yapılamadı,tekrar dene!", baseURL, driver.getCurrentUrl());

        //bilgisayar kesilmesi search edilir.
        driver.findElement(By.className("sc-4995aq-0 sc-14oyvky-0 itMXHg")).sendKeys("bilgisayar"); //kelimenin yazıldıgı alan
        driver.findElement(By.className("qjixn8-0 sc-1bydi5r-0 hKfdXF")).click(); //arama butonu

        //arama sonuçları sayfasından 2.sayfa açılır, 2. sayfa olup olmadığı kontrol edilir
        driver.get(baseURL +"arama/?k=bilgisayar&sf=2"); //buton href alınır.
        Assert.assertEquals("Arama sonuçlarından 2. sayfa açılamadı!" ,"2", driver.findElement(By.className("current")).getText()); //class adı alınır.

        //rastgele ürün seçilir, seçilen ürünün sayfası açılır
        //eksik kod parçacıgı
        Random random = new Random();
        int randomPro = random.nextInt(49) + 1;


        //ürün sepete eklenir, sayfadaki ürünün fiyatı bir değere atanır
        driver.findElement(By.id("add-to-basket")).click();
        String urunfiyat1= driver.findElement(By.id("sp-price-lowPrice")).getText();

        //sepete gidilir, sepetteki ürünün fiyatı bir değere atanır, ürün sayfasındaki ve sepetteki fiyat karşılaştırılır
        driver.get(baseURL +"sepetim");
        String urunfiyat2=driver.findElement(By.className("real-discounted-price")).getText();
        Assert.assertEquals("Sepetteki fiyat ürün sayfasındaki fiyata eşit değildir.", urunfiyat1,urunfiyat1);

        //ürün adedi arttırılarak 2 yapılır, ürün adedinin 2 olup olmadığı kontrol edilir
        new Select((driver.findElement(By.className("amount")))).selectByValue("2");
        Assert.assertEquals("Ürün adedi 2 değildir.", "2",driver.findElement(By.className("amount")).getText());

        //ürün sepetten silinir ve silinip silinmediği kontrol edilir
        driver.findElement(By.className("btn-delete btn-update-item hidden-m")).click();
        String sepetBosmu=driver.findElement(By.xpath("//*[@id='empty-cart-container']/div[1]/div[1]/div/div[2]/h2")).getText();
        Assert.assertEquals("Sepette ürün bulunmaktadır.!", "Sepette ürün bulunmamaktadır.", sepetBosmu);
    }

    @After
    public void tearDown(){
        driver.quit();
    }


}
