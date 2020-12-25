package me.nertzhul.playerinterfacecore.api;

import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.attribute.PlayerAttribute;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerInterfaceAttributes {
    public static int getPlayerAttributeByID(Player player, String attributeID) {
        return PlayerData.get((OfflinePlayer) player).getAttributes().getAttribute(MMOCore.plugin.attributeManager.get(attributeID));
    }

    public static String getPlayerAttributeID(String attribute) {
        return MMOCore.plugin.attributeManager.get(attribute).getId();
    }

    public static String getPlayerAttributeName(String attribute) {
        return MMOCore.plugin.attributeManager.get(attribute).getName();
    }

    public static int getPlayerAttributesPoints(Player player) {
        return PlayerData.get((OfflinePlayer) player).getAttributePoints();
    }

    public static void playerLevelUpAttribute(Player player, String attributeID) {
        PlayerAttribute playerAttribute = MMOCore.plugin.attributeManager.get(attributeID);

        if (playerAttribute.hasMax() && getPlayerAttributeByID(player, attributeID) != playerAttribute.getMax()) {
            PlayerData.get((OfflinePlayer) player).giveAttributePoints(-1);
            PlayerData.get((OfflinePlayer) player).getAttributes().getInstance(playerAttribute).addBase(1);
            MMOCore.plugin.configManager.getSimpleMessage("attribute-level-up", "attribute", playerAttribute.getName(),
                    "level", "" + PlayerData.get((OfflinePlayer) player).getAttributes().getInstance(playerAttribute).getBase()).send(player);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        } else {
            MMOCore.plugin.configManager.getSimpleMessage("attribute-max-points-hit").send(player);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
        }
    }
}

