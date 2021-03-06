package org.vlad.MyTest.po;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.vlad.MyTest.utils.DriverUtils;

import java.util.ArrayList;
import java.util.List;

//abstract class - can't create instance of this class
//описіваем базовый функционал тут! (пейджобджект)
public abstract class AbstractPO {
    private final int DEFAULT_TIMEOUT = 10;
    private WebDriver driver = DriverUtils.get().getDriver();
    //^
    //Обьявляем константу
    //final - 1 раз значение навсегда

    void click(By locator){
        waitForVisibility(locator, true);
        driver.findElement(locator).click();
    }

    void sendKeys(By locator, String text){
        waitForVisibility(locator, true);
        WebElement inputElement = driver.findElement(locator);
        inputElement.clear();
        inputElement.sendKeys(text);
    }

    boolean isSelected(By locator){
        waitForVisibility(locator, true);
        return driver.findElement(locator).isSelected();
    }

    ArrayList<String> getElementsTextContentValues(By locator){
        try {
            waitForPresence(locator, false);
        }
        catch (TimeoutException e){
        }
        finally {
            ArrayList<String> textValues = new ArrayList<String>();
            //
            List<WebElement> elementsList = driver.findElements(locator);
            for (int i=0; i<elementsList.size(); i++){
                textValues.add(elementsList.get(i).getAttribute("textContent"));
            }
            return textValues;

        }
    }

    ArrayList<String> getElementsTextVisibleValues(By locator){
        ArrayList<String> textValues = new ArrayList<String>();
        //
        List<WebElement> elementsList = driver.findElements(locator);
        for (int i=0; i<elementsList.size(); i++){
            textValues.add(elementsList.get(i).getText());
        }
        return textValues;

    }

    private void waitForPresence(By locator, boolean expectedPresence){
        WebElement presentElement = DriverUtils.get().getDriver().findElement(locator);
        WebDriverWait waitForPresence = new WebDriverWait(DriverUtils.get().getDriver(), DEFAULT_TIMEOUT);
        if (expectedPresence){
            waitForPresence.until(ExpectedConditions.presenceOfElementLocated(locator));
        }
        else {
            //staleness - єлемент отсутствует в DOM
            waitForPresence.until(ExpectedConditions.stalenessOf(presentElement));

        }
    }

    private void waitForVisibility(By locator, boolean expectedVisible){
        WebDriverWait waitForVisibility = new WebDriverWait(DriverUtils.get().getDriver(), DEFAULT_TIMEOUT);
        waitForPresence(locator, true);
        if (expectedVisible){
            waitForVisibility.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
        else {
            waitForVisibility.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }
    }

}
