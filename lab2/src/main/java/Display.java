public class Display implements IObserver<WeatherInfo> {

    @Override
    public void update(WeatherInfo info) {
        System.out.println("Current temperature: " + info.temperature);
        System.out.println("Current humidity: " + info.humidity);
        System.out.println("Current pressure: " + info.pressure);
        System.out.println("--------------------");
    }
}
