package me.nertzhul.playerinterfacecore;

import com.ramonrpa.customversion.api.Attribute;
import com.ramonrpa.customversion.api.PlayerStats;
import com.ramonrpa.customversion.events.AttributeLevelUpRequestEvent;
import com.ramonrpa.customversion.events.PlayerStatsEvent;
import com.ramonrpa.customversion.packets.CustomPacketManager;
import com.ramonrpa.customversion.packets.server.SPacketPlayerStats;
import me.nertzhul.playerinterfacecore.api.PlayerInterfaceAttributes;
import me.nertzhul.playerinterfacecore.api.PlayerInterfaceStatus;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public final class PlayerInterfaceCore extends JavaPlugin implements Listener {

    private static PlayerInterfaceCore instance;
    public static PlayerInterfaceCore getInstance() {
        return instance;
    }

    private static Economy economy = null;
    public static PlayerInterfaceStatus playerInterfaceStatus;
    public static PlayerInterfaceAttributes playerInterfaceAttributes;
    private final DecimalFormat format = new DecimalFormat("0.00");

    @Override
    public void onEnable() {
        if (!hasMmoCore()) {
            Bukkit.getLogger().severe("§4[PIC] Dependência §e\"MMOCore\" §4não encontrada, o plugin será desativado!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        instance = this;
        setupEconomy();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static boolean hasMmoCore() {
        return (Bukkit.getPluginManager().getPlugin("MMOCore") != null && Bukkit.getPluginManager().getPlugin("MMOCore").isEnabled());
    }

    public static Economy getEconomy() {return economy;}
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }

    @EventHandler
    public void onPlayerStats(PlayerStatsEvent e) {
        PlayerStats playerStats = new PlayerStats(playerInterfaceStatus.getPlayerLevel(e.getPlayer()),
                playerInterfaceStatus.getPlayerExperience(e.getPlayer()),
                playerInterfaceStatus.getPlayerLevelUpExperience(e.getPlayer()),
                playerInterfaceStatus.getPlayerClass(e.getPlayer()).getName(),
                (playerInterfaceStatus.getPlayerProfession(e.getPlayer()) == null ? "Nenhuma" : playerInterfaceStatus.getPlayerProfession(e.getPlayer()).getName()),
                playerInterfaceStatus.getPlayerProfessionLevel(e.getPlayer()),
                Double.parseDouble(format.format(getEconomy().getBalance(e.getPlayer()))),
                playerInterfaceAttributes.getPlayerAttributesPoints(e.getPlayer()));
        for (String attribute : getConfig().getStringList("player_attributes")) {
            Attribute playerAttribute = new Attribute(playerInterfaceAttributes.getPlayerAttributeID(attribute), playerInterfaceAttributes.getPlayerAttributeName(attribute), playerInterfaceAttributes.getPlayerAttributeByID(e.getPlayer(), attribute));
            playerStats.AddAttribute(playerAttribute);
        }
        SPacketPlayerStats playerStatsPacket = new SPacketPlayerStats(playerStats);
        CustomPacketManager.sendCustomPacket(e.getPlayer(), playerStatsPacket);
    }

    @EventHandler
    public void onAttributeUpRequest(AttributeLevelUpRequestEvent e) {
        playerInterfaceAttributes.playerLevelUpAttribute(e.getPlayer(), e.getId());
        PlayerStats playerStats = new PlayerStats(playerInterfaceStatus.getPlayerLevel(e.getPlayer()),
                playerInterfaceStatus.getPlayerExperience(e.getPlayer()),
                playerInterfaceStatus.getPlayerLevelUpExperience(e.getPlayer()),
                playerInterfaceStatus.getPlayerClass(e.getPlayer()).getName(),
                (playerInterfaceStatus.getPlayerProfession(e.getPlayer()) == null ? "Nenhuma" : playerInterfaceStatus.getPlayerProfession(e.getPlayer()).getName()),
                playerInterfaceStatus.getPlayerProfessionLevel(e.getPlayer()),
                Double.parseDouble(format.format(getEconomy().getBalance(e.getPlayer()))),
                playerInterfaceAttributes.getPlayerAttributesPoints(e.getPlayer()));
        for (String attribute : getConfig().getStringList("player_attributes")) {
            Attribute playerAttribute = new Attribute(playerInterfaceAttributes.getPlayerAttributeID(attribute), playerInterfaceAttributes.getPlayerAttributeName(attribute), playerInterfaceAttributes.getPlayerAttributeByID(e.getPlayer(), attribute));
            playerStats.AddAttribute(playerAttribute);
        }
        SPacketPlayerStats playerStatsPacket = new SPacketPlayerStats(playerStats);
        CustomPacketManager.sendCustomPacket(e.getPlayer(), playerStatsPacket);
    }

}
