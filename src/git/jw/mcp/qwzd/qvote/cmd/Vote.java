package git.jw.mcp.qwzd.qvote.cmd;

import com.mysql.fabric.xmlrpc.base.Array;
import git.jw.mcp.qwzd.cmd.BaseCmd;
import git.jw.mcp.qwzd.qvote.VotePlugin;
import git.jw.mcp.qwzd.qvote.VoteType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static git.jw.mcp.qwzd.qvote.VotePlugin.debug;
public class Vote extends BaseCmd {
    public Vote(JavaPlugin plugin){
        super(plugin);
        setAdminOnly(false);
        setPlayerOnly(true);
        setArgLength((short) 2);
    }
    @Override
    protected String getName() {
        return "qvote";
    }

    @Override
    public boolean exec(org.bukkit.command.CommandSender commandSender, String[] strings) {
        VoteType type=VoteType.loadByString(strings[0]);
        Player player= (Player) commandSender;
        if(type==VoteType.DEBUG){
            debug("cmd|debug");
            return true ;

        }
        if(type!=VoteType.SPAWN){
            try{
                int i=Integer.parseInt(strings[1]);
                if(i<0||i>2){
                    debug("cmd|arg");
                    return true;
                }
            }catch (Exception e){
                return true;
            }
        }else {
            if(!VotePlugin.instance.spawnFactions.contains(strings[1])){
                debug("cmd|faction not contained");
                return true;
            }
        }

       if(VotePlugin.canVote(player,type)){
            debug(VotePlugin.getData().getStringList(type+".voted").toString());
            debug("cmd|voting"+player.getName()+"|"+type);
            List<String> list=VotePlugin.getData().getStringList(type+".voted");
           list.add(player.getName());
           VotePlugin.getData().set(type+".voted",list);
            if(VotePlugin.getData().get(type.toString()+"."+strings[1])==null){
                VotePlugin.getData().set(type.toString()+"."+strings[1],1);
                return true;
            }
            int a=VotePlugin.getData().getInt(type.toString()+"."+strings[1])+1;
           VotePlugin.getData().set(type.toString()+"."+strings[1],a);
            return true;
       }


        return true;
    }
}
