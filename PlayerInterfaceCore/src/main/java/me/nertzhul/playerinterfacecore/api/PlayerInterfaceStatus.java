package me.nertzhul.playerinterfacecore.api;

import me.nertzhul.playerinterfacecore.PlayerInterfaceCore;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.experience.Profession;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.PlayerClass;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerInterfaceStatus {
    private static final FileConfiguration config = PlayerInterfaceCore.getInstance().getConfig();
    private static final LuckPerms luckPermsApi = LuckPermsProvider.get();

    // Player Profile
    public int getPlayerLevel(Player player) {
        return PlayerData.get((OfflinePlayer) player).getLevel();
    }

    public double getPlayerExperience(Player player) {
        return PlayerData.get((OfflinePlayer) player).getExperience();
    }

    public double getPlayerLevelUpExperience(Player player) {
        return PlayerData.get((OfflinePlayer) player).getLevelUpExperience();
    }

    public int getPlayerSkillPoints(Player player) {
        return PlayerData.get((OfflinePlayer) player).getSkillPoints();
    }

    public PlayerClass getPlayerClass(Player player) {
        return PlayerData.get((OfflinePlayer) player).getProfess();
    }

    public int getPlayerClassPoints(Player player) {
        return PlayerData.get((OfflinePlayer) player).getClassPoints();
    }

    // Player Profession
    public Profession getPlayerProfession(Player player) {
        try {
            for (String groupName : config.getStringList("player_professions")) {
                if (luckPermsApi.getUserManager().getUser(player.getName()).getInheritedGroups(QueryOptions.defaultContextualOptions()).contains(luckPermsApi.getGroupManager().getGroup(groupName.split(":")[0]))) {
                    return MMOCore.plugin.professionManager.get(groupName.split(":")[1]);
                }
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public int getPlayerProfessionLevel(Player player) {
        if (getPlayerProfession(player) != null) {
            return PlayerData.get((OfflinePlayer) player).getCollectionSkills().getLevel(getPlayerProfession(player));
        } else {
            return 0;
        }
    }

    public double getProfessionExperience(Player player) {
        if (getPlayerProfession(player) != null) {
            return PlayerData.get((OfflinePlayer) player).getCollectionSkills().getExperience(getPlayerProfession(player));
        } else {
            return 0;
        }
    }

    public double getProfessionLevelUpExperience(Player player) {
        if (getPlayerProfession(player) != null) {
            return PlayerData.get((OfflinePlayer) player).getCollectionSkills().getLevelUpExperience(getPlayerProfession(player));
        } else {
            return 0;
        }
    }
}
