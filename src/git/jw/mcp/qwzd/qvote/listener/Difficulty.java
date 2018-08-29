package git.jw.mcp.qwzd.qvote.listener;

import git.jw.mcp.qwzd.qvote.VotePlugin;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class Difficulty implements Listener {
    public int getLevel(){
        return VotePlugin.instance.currentDifficulty;
    }
    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        if(getLevel()==0){
            if(event.getEntity() instanceof Animals||event.getEntity() instanceof Monster){
                event.getLocation().getWorld().setDifficulty(org.bukkit.Difficulty.PEACEFUL);
                event.setCancelled(true);
            }
            return;
        }
        // 难度设置由qva now更换
        if(getLevel()==1){
            if(event.getEntity() instanceof Animals){
                event.setCancelled(true);
            }
        }
    }

}
