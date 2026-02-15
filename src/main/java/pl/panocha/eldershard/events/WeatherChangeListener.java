package pl.panocha.eldershard.events;

import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.EventHandler;
import java.util.concurrent.ThreadLocalRandom;

public class WeatherChangeListener implements Listener {

    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.isCancelled() || !event.toWeatherState()) return;
        if (RNG.nextBoolean()) return;

        event.setCancelled(true);
    }
}
