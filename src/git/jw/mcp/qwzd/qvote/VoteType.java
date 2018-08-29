package git.jw.mcp.qwzd.qvote;

public enum  VoteType {
PROTECT,SPAWN,DIFFICULTY,DEBUG ;
    public static VoteType loadByString(String s){
    if(s.equalsIgnoreCase("protect")){
        return PROTECT;
    }else if (s.equalsIgnoreCase("spawn")){
        return SPAWN;
    }else if (s.equalsIgnoreCase("difficulty")){
        return DIFFICULTY;
    }
    return DEBUG;
    }
}
