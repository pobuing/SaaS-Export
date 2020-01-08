package cn.mccreefei.web;

import cn.mccreefei.enums.LoginTypeEnum;
import cn.mccreefei.model.*;
import cn.mccreefei.service.UserService;
import com.itheima.saas.domain.system.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 登录和注册的控制器
 */
@Controller
public class ChatLoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService; //用户service类
    @Autowired
    private SimpMessagingTemplate messagingTemplate; //消息模板
    @Autowired
    private ParticipantRepository participantRepository; //在线用户存储

    private static final String SUBSCRIBE_LOGIN_URI = "/topic/login"; //订阅的登录地址

    /**
     * 反馈前端ajax登录的消息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/reply/login", method = RequestMethod.POST)
    @ResponseBody
    public ReplyLoginMessage replayLoginMessage(@RequestBody ChatUser user) {
        if (user.getName() == null || user.getName().trim().equals("")
                || user.getPassword() == null || user.getPassword().equals("")) {
            return new ReplyLoginMessage(false, ReplyLoginMessage.USER_NAME_OR_PASSWORD_NULL);
        }
        boolean isExist = userService.isExistUser(user.getName());
        if (!isExist) {
            return new ReplyLoginMessage(false, ReplyLoginMessage.USER_NAME_NOT_EXIST);
        }
        ChatUser res = userService.validateUserPassword(user.getName(), user.getPassword());
        if (res == null) {
            return new ReplyLoginMessage(false, ReplyLoginMessage.USER_PASSWORD_WRONG);
        }
        return new ReplyLoginMessage(true);
    }

    /**
     * 反馈前端ajax注册的消息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/reply/regist", method = RequestMethod.POST)
    @ResponseBody
    public ReplyRegistMessage replyRegistMessage(@RequestBody ChatUser user) {
        boolean isExist = userService.isExistUser(user.getName());
        if (isExist) {
            return new ReplyRegistMessage(false, ReplyRegistMessage.USER_NAME_EXIST);
        }
        if (user.getPassword() != null) {
            userService.insertUser(user.getName(), user.getPassword());
        }
        return new ReplyRegistMessage(true);
    }

    /**
     * 登录进入聊天室
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/chat")
    public String loginIntoChatRoom(String name, String token, HttpServletRequest request) {
        ChatUser user = new ChatUser();
        user.setName(name);
        user.setPassword(token);
        user.setLoginDate(new Date());
        user.setPassword(null);  //设空防止泄露给其他用户
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        //保存登陆信息
        LoginInfoDo loginInfo = LoginInfoDo.builder().userId(user.getId()).userName(user.getName()).
                status(LoginTypeEnum.LOGIN.getCode()).createTime(new Date()).build();
        userService.addUserLoginInfo(loginInfo);

        messagingTemplate.convertAndSend(SUBSCRIBE_LOGIN_URI, user);
        participantRepository.add(user.getName(), user);
        logger.info(user.getLoginDate() + ", " + user.getName() + " login.");
        return "chatroom";
    }


    /**
     * 返回当前在线人数
     *
     * @return
     */
    @SubscribeMapping("/chat/participants")
    public Long getActiveUserNumber() {
        return Long.valueOf(participantRepository.getActiveSessions().values().size());
    }
}
