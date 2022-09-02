package com.lyd.listener;

import catcode.CatCodeUtil;
import kotlinx.coroutines.channels.Send;
import lombok.extern.slf4j.Slf4j;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import sun.management.Sensor;

@Slf4j
@Beans
public class MyListener {

    @OnGroup
    @Filter(value = ".*v.*50.*",matchType = MatchType.REGEX_MATCHES)
    public void vme50(GroupMsg groupMsg, MsgSender sender) {
        CatCodeUtil catcodeUtil = CatCodeUtil.INSTANCE;
        String userId = groupMsg.getAccountInfo().getAccountCode();
        String nudge = catcodeUtil.toCat("nudge", true, "target=" + userId);
        String image = catcodeUtil.toCat("image", true, "file=classpath:imgs/vme50.jpg");
        sender.SENDER.sendGroupMsg(groupMsg,"为什么不v天狗50呢"+nudge+image);
    }


    @OnGroup
    @Filter(value = ".dice")
    public void test(GroupMsg groupMsg,MsgSender sender) {
        CatCodeUtil catcodeUtil = CatCodeUtil.INSTANCE;
        String dice = catcodeUtil.toCat("dice", true, "random=true");
        sender.SENDER.sendGroupMsg(groupMsg,dice);
    }


}
