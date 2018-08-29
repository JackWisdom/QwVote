package git.jw.mcp.qwzd.qvote.papi;

import git.jw.mcp.qwzd.qvote.VotePlugin;
import git.jw.mcp.qwzd.qvote.VoteType;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PapiCount extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "vcount";
    }//count_

    @Override
    public String getAuthor() {
        return "jackwisdom";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        /*
         %count_protect_1%
          */
        String protect=VoteType.PROTECT+"_";
        String spawn=VoteType.SPAWN+"_";
        String difficulty=VoteType.DIFFICULTY+"_";
        VotePlugin.debug(identifier);
        String path=identifier.replace("_",".").toUpperCase();
        if(VotePlugin.getData().getString(path)==null){
            return 0+"";
        }
        return VotePlugin.getData().getString(path);
    }
}
