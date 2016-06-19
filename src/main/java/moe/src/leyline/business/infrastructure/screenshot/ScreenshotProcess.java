package moe.src.leyline.business.infrastructure.screenshot;

import java.util.concurrent.Callable;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;

import org.apache.tomcat.util.ExceptionUtils;
import org.openqa.selenium.OutputType;

/**
 * Created by bytenoob on 6/19/16.
 */
public class ScreenshotProcess {
    public static byte[] execute(String url) throws Exception{
        Callable callable = () -> {
            byte[] res=null;
            try {
                JBrowserDriver driver = new JBrowserDriver(Settings.builder().
                        timezone(Timezone.AMERICA_NEWYORK).build());

                // This will block for the page load and any
                // associated AJAX requests
                driver.get(url);
                res = driver.getScreenshotAs(OutputType.BYTES);
                driver.quit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        };
        return (byte[])callable.call();
    }
}
