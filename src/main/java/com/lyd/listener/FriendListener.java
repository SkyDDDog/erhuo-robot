package com.lyd.listener;

import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.Timestamp;
import love.forte.simbot.action.ReplySupport;
import love.forte.simbot.definition.Friend;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.message.ReceivedMessageContent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FriendListener {

//    @Listener
    public void repeat(FriendMessageEvent event) {
        if (event instanceof ReplySupport) {
            ((ReplySupport) event).replyBlocking("Hello Simbot");
        }
        Friend user = event.getUser();
        Timestamp timestamp = event.getTimestamp();
        log.info(user.getId().toString());
    }

    @Listener
    @Filter(".r\\s*\\d*")
    public void roll(FriendMessageEvent event) {
        String s = event.getMessageContent().getPlainText();
        if (event instanceof ReplySupport) {
            ((ReplySupport) event).replyBlocking(event.getMessageContent());
        }
        log.info(s);
//        log.info(event.getUser()+":"+event.getMessageContent());


    }


}
