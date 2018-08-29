package git.jw.mcp.qwzd.qvote.listener;

import git.jw.mcp.qwzd.qvote.VotePlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Protect implements Listener {
    public int getLevel(){
        return VotePlugin.instance.currentProtect;
    }
    @EventHandler
    public void onExplode(BlockExplodeEvent event){
    if(getLevel()!=0){
        event.setCancelled(true);
    }
    }
    @EventHandler
    public void onExplode(EntityExplodeEvent event){
        if(getLevel()!=0){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPhysic(BlockPhysicsEvent event){
        if(getLevel()==2){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onFlow(BlockFromToEvent event){
        if(getLevel()!=0){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onRedstone(BlockRedstoneEvent event){
        if(getLevel()==2&&event.getBlock().getType().name().contains("REDSTONE")){
            event.getBlock().setType(Material.AIR);
        }
    }

    @EventHandler
    public void onPiston(BlockPistonExtendEvent event){
        if(getLevel()==1){
            event.setCancelled(true);
        }
    }

}
