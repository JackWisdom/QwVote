package git.jw.mcp.qwzd.qvote.cmd;

import git.jw.mcp.qfaction.QFaction;
import git.jw.mcp.qfaction.data.FactionData;
import git.jw.mcp.qwzd.cmd.BaseCmd;
import git.jw.mcp.qwzd.qvote.VotePlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Spawn extends BaseCmd {
    public Spawn(JavaPlugin plugin){
        super(plugin);
        setAdminOnly(false);
        setPlayerOnly(true);
        setArgLength((short) 0);
    }
    @Override
    protected String getName() {
        return "spawn";
    }

    @Override
    public boolean exec(CommandSender commandSender, String[] strings) {
        Player p= (Player) commandSender;
        if(VotePlugin.instance.currentFaction==null){
            commandSender.sendMessage("§c暂时没有主城");
            return true;
        }
        FactionData data=QFaction.instance.getFaction(VotePlugin.instance.currentFaction);
        if(data==null){
            commandSender.sendMessage("§c暂时没有主城");
            return true;
        }
        if(data.getSpawn()!=null){
            p.teleport(data.getSpawn());
        }
        commandSender.sendMessage("§c"+data.getLeader()+"暂时没有主城");
        return true;
    }
}
