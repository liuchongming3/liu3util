package org.liu3.brower;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @Author: liutianshuo
 * @Date: 2021/2/22
 */
public class SeleniumTest {

    public static void main(String[] args) throws Exception {

        //调用chrome driver
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/Chrome/Application/chromedriver.exe");

        //调用chrome
        ChromeDriver driver = new ChromeDriver();

        driver.get("https://www.baidu.com");

        //调整高度
        driver.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);


        //获取文本框对象
        By txtBy = By.id("kw");
        WebElement txt = driver.findElement(txtBy);
        //赋值
        txt.sendKeys("java");

        String jsText = "var txt = document.getElementById('kw'); txt.value='mysql';";
        driver.executeScript(jsText);


        By subBy = By.id("su");
        WebElement sub = driver.findElement(subBy);
        sub.click();
        //分割符
        System.out.println("-----------------------");

        //定位iframe
        //WebElement iframe = driver.findElement(By.id("iframeResult"));
        //也可直接这样写
        // driver.switchTo().frame("id=iframeResult");

        //线程休眠
        //Thread.sleep(30000);

    }

    public void test(ChromeDriver driver){
        //4. 触发event，Dom event 事件的封装

        JavascriptExecutor jse=((JavascriptExecutor)driver);
        //String js="var event;if (document.createEvent){event = document.createEvent(\"HTMLEvents\");event.initEvent(\""+event+"\", true, false);arguments[0].dispatchEvent(event);} else {arguments[0].fireEvent(\"on"+event+"\")}";
        //jse.executeScript(js, findElement(driver,locator)) ;
    }

    private void aa(ChromeDriver driver) throws InterruptedException {
        //获取网址
        driver.get("http://epub.cnki.net/KNS/brief/result.aspx?dbprefix=CMFD");
        //调整高度
        driver.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        //获取网址
        driver.get("http://epub.cnki.net/KNS/brief/result.aspx?dbprefix=CMFD");

        //高级搜索
        By by = By.xpath("//*[@id=\"1_3\"]/a");
        WebElement high = driver.findElement(by);
        high.click();

        //等搜索有结果
        Thread.sleep(1000);

        //定位元素
        WebElement in = driver.findElementByName("txt_1_value1");

        //定义搜索内容
        String searchWord = "基因芯片";
        //发送搜索内容
        in.sendKeys(searchWord);

        driver.findElementByXPath("//*[@id='ddSubmit']/span").click();
        driver.findElementByXPath("//*[@id='btnSearch']").click();
        Thread.sleep(2000);

        //清除分类获得所有
        driver.findElementByXPath("//*[@id='XuekeNavi_Div']/div[1]/input[1]").click();
        driver.findElementByXPath("//*[@id='B']/span/img[1]").click();
        driver.findElement(By.xpath(""));
        Thread.sleep(2000);

    }
}
