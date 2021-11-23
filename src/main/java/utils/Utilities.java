package utils;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import utils.driver.Driver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Utilities extends Driver {

    public Utilities(){PageFactory.initElements(driver,this);}

    public void clickElement(WebElement element){centerElement(element).click();}

    public WebElement loopNMatch(List<WebElement> elementList, String itemText){
        for (WebElement item:elementList) {
            if (item.getText().contains(itemText))
                return item;
        }
        Assert.fail("Item could not be located!");
        return null;
    }

    public WebElement centerElement(WebElement element){
        String script =
                "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) driver).executeScript(script, element);

        return element;
    }

    public void fillInput(String input, WebElement inputElement){inputElement.sendKeys(input);}

    public void dragAndDropTo(WebElement targetElement, WebElement destinationElement){
        centerElement(targetElement);
        Actions actions = new Actions(driver);
        actions.moveToElement(targetElement)
                .clickAndHold(targetElement)
                .moveToElement(destinationElement)
                .release()
                .build()
                .perform();
        waitFor(0.3);
    }

    public void dragAndDropByOffset(WebElement targetElement, Integer xOffset, Integer yOffset){
        centerElement(targetElement);
        Actions actions = new Actions(driver);
        actions.moveToElement(targetElement)
                .clickAndHold(targetElement)
                .moveToElement(targetElement,xOffset,yOffset)
                .release()
                .build()
                .perform();
        waitFor(0.3);
    }

    public void navigate(String url){driver.get(url);}

    public void predeterminedNavigate(String pageName){
        Properties properties = new Properties();
        String url;
        try{
            properties.load(new FileReader("src/main/resources/test.properties"));
            url = properties.getProperty("url."+pageName);
        }
        catch (IOException exception) {return;}
        driver.get(url);
    }

    public void waitFor(double seconds) {
        try {Thread.sleep((long) (seconds*1000));}
        catch (InterruptedException ignored){}
    }

}
