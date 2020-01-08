package cn.mccreefei.model;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *在线用户仓库，存储在线用户
 */
@Component
public class ParticipantRepository {
    private Map<String, ChatUser> activeSessions = new ConcurrentHashMap<String, ChatUser>(); //在线用户map，键：用户名称，值：用户对象
    public Map<String, ChatUser> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(Map<String, ChatUser> activeSessions) {
        this.activeSessions = activeSessions;
    }

    public void add(String name, ChatUser user){
        activeSessions.put(name, user);

    }

    public ChatUser remove(String name){
        return activeSessions.remove(name);
    }

    public boolean containsUserName(String name){
        return activeSessions.containsKey(name);
    }
}
