import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ObservableTest<T> {
    Display display = new Display();
    StatsDisplay statsDisplay = new StatsDisplay();
    WeatherData weatherData = new WeatherData();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(stream);
    PrintStream originalOutputStream = System.out;

    @Before
    public void changeOutputStream() {
        System.setOut(out);
    }

    @After
    public void returnStandardOutputStream() {
        System.setOut(originalOutputStream);
    }

    @Test
    public void notifyObservers() {
        weatherData.registerObserver(display, 1);
        weatherData.registerObserver(statsDisplay, 2);

        weatherData.setMeasurements(10, 30, 750);

        assertEquals("Max temperature: 10.0\r\n" +
                "Min temperature: 10.0\r\n" +
                "Average temperature: 10.0\r\n" +
                "--------------------\r\n" +
                "Current temperature: 10.0\r\n" +
                "Current humidity: 30.0\r\n" +
                "Current pressure: 750.0\r\n" +
                "--------------------\r\n", stream.toString());
    }
}