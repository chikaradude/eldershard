package pl.panocha.eldershard.misc;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import pl.panocha.eldershard.Eldershard;

import java.util.concurrent.ThreadLocalRandom;

public class CrystalAnimation {

    private final Particle.DustOptions olive =
            new Particle.DustOptions(Color.fromRGB(128, 170, 70), 1.2F);

    private final Particle.DustOptions mint =
            new Particle.DustOptions(Color.fromRGB(150, 255, 200), 1.2F);

    public CrystalAnimation(Eldershard plugin) {
        Location fixedLocation = new Location(
                plugin.getServer().getWorld("eldershard"),
                0.5,
                213.5,
                -48.5
        );

        new BukkitRunnable() {
            double step = 0;

            @Override
            public void run() {
                step += 0.12;

                spawnDoubleCone(fixedLocation, step);

                if (step > Math.PI * 10) step = 0;
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    private void spawnDoubleCone(Location center, double step) {
        World world = center.getWorld();
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        double height = 5.5;
        double radius = 3.5;

        for (double y = -height; y <= height; y += 0.35) {

            if (rand.nextDouble() > 0.22) continue; // slightly rarer

            double r = radius * (1 - Math.abs(y) / height);
            double angle = step + (y * 2);

            double x = r * Math.cos(angle);
            double z = r * Math.sin(angle);

            x += rand.nextDouble(-0.1, 0.1);
            z += rand.nextDouble(-0.1, 0.1);

            Location loc = center.clone().add(x, y, z);

            Particle.DustOptions color = rand.nextBoolean() ? olive : mint;

            world.spawnParticle(
                    Particle.DUST,
                    loc,
                    1,
                    0,0,0,
                    0,
                    color
            );
        }
    }
}