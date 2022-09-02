package com.lyd.initiativeSender;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.timer.Cron;
import love.forte.simbot.timer.EnableTimeTask;
import love.forte.simbot.timer.Fixed;

import java.util.concurrent.TimeUnit;

@Beans
@EnableTimeTask
public class MySender {

    @Depend
    private BotManager botManager;

//    @Fixed(value = 30, timeUnit = TimeUnit.SECONDS)
    @Cron("0 00 23 * * ?")
    public void sleepReminder() {

//        botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(875918112,"test定时消息");

        final String target = "2320747195";
        CatCodeUtil catCodeUtil = CatCodeUtil.INSTANCE;
        String at = catCodeUtil.toCat("at", true, "code="+target);
        String nudge = catCodeUtil.toCat("nudge", true, "target="+target);

        botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(955988394,nudge+at+"姐姐该睡美容觉啦了噢");
    }

    @Cron("0 50 12 * * ?")
    public void sleepMidReminder() {

//        botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(875918112,"test定时消息");

        final String target = "2320747195";
        CatCodeUtil catCodeUtil = CatCodeUtil.INSTANCE;
        String at = catCodeUtil.toCat("at", true, "code="+target);
        String nudge = catCodeUtil.toCat("nudge", true, "target="+target);

        botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(955988394,nudge+at+"晨晨姐姐，该睡午觉了噢");
    }


}
