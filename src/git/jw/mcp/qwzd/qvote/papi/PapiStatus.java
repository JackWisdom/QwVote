package git.jw.mcp.qwzd.qvote.papi;

import git.jw.mcp.qwzd.qvote.VotePlugin;
import git.jw.mcp.qwzd.qvote.VoteType;
import org.bukkit.entity.Player;

public class PapiStatus extends PapiCount {
    @Override
    public String getIdentifier() {
        return "vstatus";
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        /*
         %vstatus_current_protect%
          */
        String s="current_";
        if(identifier.startsWith(s)){
            String str=identifier.substring(s.length());
            if(str.equalsIgnoreCase("remain")){
                // %vstatus_current_remain%
                return VotePlugin.instance.getHour();
            }
           VoteType type= VoteType.loadByString(str);
            if(type==VoteType.PROTECT){
                return ""+VotePlugin.instance.currentProtect;
            }
            if(type==VoteType.SPAWN){
                if(VotePlugin.instance.currentFaction==null){
                    return "无";
                }
                return VotePlugin.instance.currentFaction;
            }
            if(type==VoteType.DIFFICULTY){
                return ""+VotePlugin.instance.currentDifficulty;
            }
        }
        return null;
    }
}
