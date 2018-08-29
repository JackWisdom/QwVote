package git.jw.mcp.qwzd.qvote;

import git.jw.mcp.qwzd.QwLib;
import git.jw.mcp.qwzd.configuration.YamlConfig;
import git.jw.mcp.qwzd.qvote.cmd.Spawn;
import git.jw.mcp.qwzd.qvote.cmd.Vote;
import git.jw.mcp.qwzd.qvote.cmd.VoteAdmin;
import git.jw.mcp.qwzd.qvote.listener.Protect;
import git.jw.mcp.qwzd.qvote.papi.PapiCount;
import git.jw.mcp.qwzd.qvote.papi.PapiStatus;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class VotePlugin extends JavaPlugin {
    public List<String> spawnFactions=new ArrayList<>();
    public static VotePlugin instance;
   // public int maxShowPlayer=15;
    public int maxVote=100;
    public static YamlConfig cfg;
    public int day;
    public int currentProtect=0;
    public int currentDifficulty=0;
    public String currentFaction=null;
    private int remain;
    public static YamlConfiguration getData(){
      return  cfg.getCfg();
    }
    public static void saveCfg(){
        cfg.save();
    }
    public void reflush(){
        //开始更换
        String pro=VoteType.PROTECT+".";
        String spawn=VoteType.SPAWN.toString();
        String difficulty=VoteType.DIFFICULTY+".";
        int protect=select(getData().getInt(pro+"0"),getData().getInt(pro+"1"),getData().getInt(pro+"2"));
        int diff=select(getData().getInt(difficulty+"0"),getData().getInt(difficulty+"1"),getData().getInt(difficulty+"2"));

        String faction=null;
        int max=0;
        for(String fac:spawnFactions){
            int i=getData().getInt(spawn+"."+fac);
            if(i>max){
                max=i;
            }
            faction=fac;
        }
        currentFaction=faction;
        broadcast("困难投票结果"+diff+"保护"+protect+"faction"+faction);
        resetAll();
        if(currentDifficulty!=0){
            for(World w:Bukkit.getWorlds()){
                w.setDifficulty(Difficulty.HARD);
                w.setGameRule(GameRule.NATURAL_REGENERATION,false);
            }
        }
        if(currentProtect!=0){
            for(World w:Bukkit.getWorlds()){
                w.setDifficulty(Difficulty.HARD);
                w.setGameRule(GameRule.MOB_GRIEFING,false);
            }
        }

    }
    public static void debug(String s){
       // System.out.println(s);
    }
    public void resetAll(){
        reset(VoteType.SPAWN);
        reset(VoteType.DIFFICULTY);
        reset(VoteType.PROTECT);
        remain=day*24*2;
    }
    private void reset(VoteType type){
        getData().set(type+".voted",null);
        if(type==VoteType.SPAWN){
            for(String s:spawnFactions){
                getData().set(type+".s",null);
            }
            return;
        }
        getData().set(type+".1",null);
        getData().set(type+".2",null);
        getData().set(type+".0",null);
    }
    @Override
    public void onEnable(){
        instance=this;
        saveDefaultConfig();
        currentDifficulty=getConfig().getInt("currDifficulty",0);
        currentProtect=getConfig().getInt("currProtect",0);
        currentFaction=getConfig().getString("currentFaction");
        spawnFactions=getConfig().getStringList("spawn-faction");
      //  maxShowPlayer=getConfig().getInt("max-show-player");
        maxVote=getConfig().getInt("max-vote");
        cfg=new YamlConfig(this,"data");
        day=getConfig().getInt("days");
        remain=getConfig().getInt("remain",day*24*2);
        getCommand("qvote").setExecutor(new Vote(this));
        getCommand("qva").setExecutor(new VoteAdmin(this));
        new Spawn(this);
        new PapiCount().register();
        new PapiStatus().register();
        //一天24个小时48个半小时
        QwLib.startTimer(this, new BukkitRunnable() {
            @Override
            public void run() {
                if(remain==0){
                reflush();
                }
                remain=remain-1;
                //倒计时
                if(remain==1){
                    broadcast("半个小时后投票结束");
                    return;
                }
                if(remain==4){
                    broadcast("2个小时后投票结束");
                    return;
                }
                if(remain==10){
                    broadcast("5个小时后投票结束");
                    return;
                }
            }
        },60*30);
        if(currentDifficulty!=0){
            for(World w:Bukkit.getWorlds()){
                w.setDifficulty(Difficulty.HARD);
                w.setGameRule(GameRule.NATURAL_REGENERATION,false);
            }
        }
        if(currentProtect!=0){
            for(World w:Bukkit.getWorlds()){
                w.setDifficulty(Difficulty.HARD);
                w.setGameRule(GameRule.MOB_GRIEFING,false);
            }
        }
        Bukkit.getPluginManager().registerEvents(new Protect(),this);
        Bukkit.getPluginManager().registerEvents(new git.jw.mcp.qwzd.qvote.listener.Difficulty(),this);
    }
    private int select(int v0,int v1,int v2){
        if(v0>v1){
            if(v0>v2){
                return 0;
            }
            return 2;
        }
        if(v1>v2){
            return 1;
        }
        return 2;

    }
    public String getHour(){
        return remain*0.5+"小时";
    }
    @Override
    public void onDisable(){
        saveCfg();
         getConfig().set("remain",remain);
         getConfig().set("currDifficulty",currentDifficulty);
         getConfig().set("currProtect",currentProtect);
         getConfig().set("currentFaction",currentFaction);
         saveConfig();
    }
    public static boolean voted(Player p,VoteType type){
        return getData().getStringList(type+".voted").contains(p.getName());
    }
    public void broadcast(String s){
        Bukkit.getServer().broadcastMessage(s);
    }
    public static boolean canVote(Player p,VoteType type){
        if(getData().getStringList(type+".voted").size()>=100){
            p.sendMessage("§c每一个项目只支持100个投票");
            return false;
        }
        if(voted(p,type)){

            p.sendMessage("§c对不起,您已经投过票了");
            return false;
        }
        return true;
    }
}
