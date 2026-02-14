package pl.panocha.eldershard.misc;

import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatServerApi;

import static org.bukkit.Bukkit.getLogger;

public class VoiceIntegrationPlugin implements VoicechatPlugin {

    private static VoicechatServerApi serverApi;

    public static VoicechatServerApi getServerApi() {
        return serverApi;
    }

    @Override
    public String getPluginId() {
        return "ElderShardVoiceIntegrationPlugin";
    }

    @Override
    public void initialize(VoicechatApi api) {
        serverApi = (VoicechatServerApi) api;
        getLogger().info(getPluginId() + " initalized");
    }

    /*
    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(PlayerConnectedEvent.class, this::onPlayerVoiceConnected, 0);
    }

    private void onPlayerVoiceConnected(PlayerConnectedEvent event) {
        Player player = Bukkit.getPlayer(event.getConnection().getPlayer().getUuid());
        if (player == null) return;

        player.sendMessage(ChatColor.GREEN + "Welcome! You have Simple Voice Chat installed.");
        getLogger().info("Welcome to Simple Voice Chat installed.");
    }
     */
}
