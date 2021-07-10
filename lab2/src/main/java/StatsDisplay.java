public class StatsDisplay implements IObserver<WeatherInfo>{

    private double minTemperature = Double.MAX_VALUE;
    private double maxTemperature = Double.MIN_VALUE;
    private double accTemperature = 0;
    private int countAcc = 0;

    public void update(WeatherInfo info) {
        if (minTemperature > info.temperature) {
            minTemperature = info.temperature;
        }
        if (maxTemperature < info.temperature) {
            maxTemperature = info.temperature;
        }
        accTemperature += info.temperature;
        ++countAcc;

        System.out.println("Max temperature: " + maxTemperature);
        System.out.println("Min temperature: " + minTemperature);
        System.out.println("Average temperature: " + accTemperature / countAcc);
        System.out.println("--------------------");
    }
}
