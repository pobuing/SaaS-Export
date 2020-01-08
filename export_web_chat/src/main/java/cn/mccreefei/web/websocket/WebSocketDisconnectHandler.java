package cn.mccreefei.web.websocket;

import cn.mccreefei.enums.LoginTypeEnum;
import cn.mccreefei.model.LoginInfoDo;
import cn.mccreefei.model.ParticipantRepository;
import cn.mccreefei.model.ChatUser;
import cn.mccreefei.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Date;
import java.util.Map;

/**
 * websocket断开连接处理，监听SessionDisconnectEvent事件，当有人下线就通知其他用户
 */
@Component
public class WebSocketDisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserService userService;

    private final static String SUBSCRIBE_LOGOUT_URI = "/topic/logout";

    /**
     * 当sessionDisconnectEvent发布时，此方法将被调用，从事件中的message取出websocket sessionAttributes
     * 从中取出离开的User，将在线用户map中删除该用户，通知其他用户
     * @param sessionDisconnectEvent
     */
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        Map<String, ChatUser> activeSessions = participantRepository.getActiveSessions();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        ChatUser disconnectSession = (ChatUser) sessionAttributes.get("user");
        String disconnectUserName = disconnectSession.getName();
        if (participantRepository.containsUserName(disconnectUserName)){
            ChatUser removeUser = participantRepository.remove(disconnectUserName);
            removeUser.setLogoutDate(new Date());
            //保存登出信息
            ChatUser userByName = userService.getUserByName(removeUser.getName());
            LoginInfoDo loginInfo = LoginInfoDo.builder().userId(userByName == null ? null : userByName.getId())
                    .userName(removeUser.getName()).
                    status(LoginTypeEnum.LOGOUT.getCode()).createTime(new Date()).build();
            userService.addUserLoginInfo(loginInfo);
            logger.info(removeUser.getLogoutDate() + ", " + removeUser.getName() + " logout.");
            messagingTemplate.convertAndSend(SUBSCRIBE_LOGOUT_URI, removeUser);
        }
    }
}