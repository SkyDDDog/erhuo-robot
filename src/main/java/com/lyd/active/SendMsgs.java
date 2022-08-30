package com.lyd.active;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.BotManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Slf4j
@Component
public class SendMsgs {

//    @Resource
//    private BotManager botManager;

    @Value("${msg.group}")
    private Set<String> groupSet;

    @Scheduled(cron = "30 * * * * ?")
    public void sendCronMsg() {
//        groupSet.forEach(qq-> {
//            botManager.
//        });
    }


}
