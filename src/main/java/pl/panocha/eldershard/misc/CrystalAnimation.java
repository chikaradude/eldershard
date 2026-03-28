package pl.panocha.eldershard.misc;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import pl.panocha.eldershard.Eldershard;

public class CrystalAnimation {

    public CrystalAnimation(Eldershard plugin) {
        Location fixedLocation = new Location(
                plugin.getServer().getWorld("eldershard"),
                0.5, // X
                214.5,    // Y
                -48.5  // Z
        );

        new BukkitRunnable() {
            double step = 0;

            @Override
            public void run() {
                step += 0.15;

                spawnDoubleCone(fixedLocation, step);

                if (step > Math.PI * 10) step = 0;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private void spawnDoubleCone(Location center, double step) {
        World world = center.getWorld();

        double height = 5.5;
        double radius = 3.5;

        for (double y = -height; y <= height; y += 0.15) {

            double r = radius * (1 - Math.abs(y) / height);
            double angle = step + (y * 2);

            double x = r * Math.cos(angle);
            double z = r * Math.sin(angle);

            Location loc = center.clone().add(x, y, z);

            world.spawnParticle(Particle.END_ROD, loc, 0);
        }
    }
}