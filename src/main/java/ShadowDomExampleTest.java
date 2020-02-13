import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.System.setProperty;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.support.PageFactory.initElements;

public class ShadowDomExampleTest {

    private static final String CHROMEDRIVER_PATH = "PATH_TO_YOUR_CHROMEDRIVER.EXE";

    private WebDriver driver;
    private JavascriptExecutor js;

    public ShadowDomExampleTest() {
        initElements(driver, this);
    }

    @Before
    public void setUpChromeBrowser() {
        setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH + "chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void verifySettingsHeader() {
        driver.get("chrome://settings/");

        WebElement root1 = driver.findElement(tagName("settings-ui"));
        WebElement shadowRoot1 = getShadowRoot(root1);

        WebElement root2 = shadowRoot1.findElement(tagName("cr-toolbar"));
        WebElement shadowRoot2 = getShadowRoot(root2);

        WebElement root3 = shadowRoot2.findElement(id("leftSpacer"));

        assertEquals("Settings", root3.getText());
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }

    private WebElement getShadowRoot(WebElement element) {
        js = (JavascriptExecutor) driver;
        return (WebElement) js.executeScript("return arguments[0].shadowRoot", element);
    }

}
