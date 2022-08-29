package com.lyd.listener;

import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.event.GroupMessageEvent;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GroupListener {

    /**
     * 复读群成员消息 你说什么我复读什么
     * @param event 群事件
     */
    @Listener
    public void repeat(GroupMessageEvent event){

        // 获取群成员的消息
        // getPlainText()为文本消息
        final String message = event.getMessageContent().getPlainText();
        log.info(message);

        //发送消息
        event.getGroup().sendBlocking(message);

    }

}
