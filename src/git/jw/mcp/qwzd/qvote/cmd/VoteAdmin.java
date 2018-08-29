package git.jw.mcp.qwzd.qvote.cmd;

import git.jw.mcp.qwzd.cmd.BaseCmd;
import git.jw.mcp.qwzd.qvote.VotePlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class VoteAdmin extends BaseCmd {
    @Override
    protected String getName() {
        return "qva";
    }
    public VoteAdmin(JavaPlugin plugin){
        super(plugin);
        setAdminOnly(true);
        setPlayerOnly(false);setArgLength((short) 1);
    }
    @Override
    public boolean exec(CommandSender commandSender, String[] strings) {
        if(strings[0].equalsIgnoreCase("now")){
            VotePlugin.instance.reflush();
        }
        if(strings[0].equalsIgnoreCase("clear")){
            VotePlugin.instance.resetAll();
        }
        return true;
    }
}
