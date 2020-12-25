package me.nertzhul.playerinterfacecore.api;

import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.skill.Skill;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class PlayerInterfaceSkills {
    public static Collection<Skill.SkillInfo> getPlayerSkills(Player player) {
        return PlayerData.get((OfflinePlayer) player).getProfess().getSkills();
    }

    public static List<Skill.SkillInfo> getPlayerBoundSkills(Player player) {
        return PlayerData.get((OfflinePlayer) player).getBoundSkills();
    }

    public static Skill.SkillInfo getSkill(Player player, String skillName) {
        skillName = skillName.replace("-", "_").toUpperCase();
        return PlayerData.get((OfflinePlayer) player).getProfess().getSkill(skillName);
    }
}
