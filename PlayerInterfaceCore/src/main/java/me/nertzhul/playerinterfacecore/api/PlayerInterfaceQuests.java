package me.nertzhul.playerinterfacecore.api;

import fr.skytasul.quests.api.QuestsAPI;
import fr.skytasul.quests.players.PlayersManager;
import fr.skytasul.quests.structure.Quest;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerInterfaceQuests {
    public List<Quest> getAvailableQuests(Player player) {
        return QuestsAPI.getQuestsUnstarted(PlayersManager.getPlayerAccount(player), false, true);
    }

    public List<Quest> getStartedQuests(Player player) {
        return QuestsAPI.getQuestsStarteds(PlayersManager.getPlayerAccount(player), true);
    }

    public List<Quest> getFinishedQuests(Player player) {
        return QuestsAPI.getQuestsFinished(PlayersManager.getPlayerAccount(player));
    }
}
