package io.testproject.examples.sdk.java;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.testproject.java.classes.DriverSettings;
import io.testproject.java.classes.MobileDriverSettings;
import io.testproject.java.enums.DriverType;
import io.testproject.java.sdk.generated.TestProjectRunner;
import org.openqa.selenium.By;

public class FullNameBuilderAddonRunner {
    // Setting the development token
    private final static String devToken = "YOUR_DEV_TOKEN_GOES_HERE";

    public static void main(String[] args) throws Exception {
        // Creating driver settings to be used when running the test
        DriverSettings driverSettings = getDriverSettings();

        try (TestProjectRunner runner =
                     new TestProjectRunner(devToken, driverSettings, true)) {
            // Using the *getAgent* method provided by TestProjectRunner
            // gives access to the relevant driver in order to perform a "stage preparation" sequence before running the action.
            AndroidDriver<AndroidElement> driver = runner.getAgent().getAndroidDriver(AndroidElement.class);

            // Make sure we start from pristine state
            driver.resetApp();

            // Run Action
            runFullNameBuilderAction(runner);

            // Reset app - discard state
            driver.resetApp();

            // Run Element Action
            runFullNameBuilderElementAction(runner);

        } catch (Throwable e) {
            // Handle failure...
        }
    }

    private static void runFullNameBuilderAction(TestProjectRunner runner) {
        // Create action
        FullNameBuilderAction action = new FullNameBuilderAction();

        // Set parameters
        action.setFirstName("John");
        action.setLastName("Smith");

        // Running action
        runner.run(action);
    }

    private static void runFullNameBuilderElementAction(TestProjectRunner runner) {
        // Create action
        FullNameBuilderElementAction action = new FullNameBuilderElementAction();

        // Set parameters
        action.setFirstName("Elvis");
        action.setLastName("Presley");

        // Running action
        runner.run(action, By.id("personsContainer"));
    }

    // Initializes driver settings
    private static DriverSettings getDriverSettings() {
        DriverSettings driverSettings = new DriverSettings();

        // Declare that Android driver is required
        driverSettings.setDriverType(DriverType.Appium_Android);

        // Specify target device UUID and Name
        // This information is conveniently visible at https://app.testproject.io/#/agents
        // Under your connected agent on the devices tab
        driverSettings.setDeviceUdid("DEVICE_UUID");
        driverSettings.setDeviceName("DEVICE_NAME");

        // Specify mobile app settings
        MobileDriverSettings mobileSettings = new MobileDriverSettings();
        mobileSettings.setAndroidPackage("io.testproject.demo");
        mobileSettings.setAndroidActivity(".MainActivity");
        driverSettings.setMobileSettings(mobileSettings);

        return driverSettings;
    }
}