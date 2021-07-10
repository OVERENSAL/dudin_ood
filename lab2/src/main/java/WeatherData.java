public class WeatherData extends Observable<WeatherInfo>{
    private double temperature = 0.0;
    private double humidity = 0.0;
    private double pressure = 760.0;

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void measurementChanged() {
        notifyObservers();
    }

    public void setMeasurements(double temperature, double humidity, double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;

        measurementChanged();
    }

    protected WeatherInfo getChangedData() {
        WeatherInfo info = new WeatherInfo();
        info.temperature = getTemperature();
        info.humidity = getHumidity();
        info.pressure = getPressure();

        return info;
    }
}
