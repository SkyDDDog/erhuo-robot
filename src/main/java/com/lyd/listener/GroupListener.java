package com.lyd.listener;

import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.Bot;
import love.forte.simbot.Identifies;
import love.forte.simbot.action.ReplySupport;
import love.forte.simbot.component.mirai.extra.catcode.CatCodeMessageUtil;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.*;
import love.forte.simbot.resources.PathResource;
import love.forte.simbot.resources.Resource;
import org.springframework.stereotype.Component;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class GroupListener {

    @Listener
    @Filter(".help")
    public void help(GroupMessageEvent event) {
        String msg = "help消息还没做\n但是你可以v我50";
        String img = "[CAT:image,url=https://gitee.com/sky-dog/note/raw/master/img/202208300051841.png,flash=false]";
        Message message = CatCodeMessageUtil.catCodeToMessage(img);
        event.getGroup().sendBlocking(message);
    }

    @Listener
    @Filter(".help")
    public void help(FriendMessageEvent event) {
        String msg = "help消息还没做\n但是你可以v我50";
        String img = "[CAT:image,url=https://gitee.com/sky-dog/note/raw/master/img/202208300051841.png,flash=false]";
        Message message = CatCodeMessageUtil.catCodeToMessage(img);
        List<Message.Element<?>> messageList = new ArrayList<>(2);
        messageList.add(Text.of("simbot"));
        messageList.add((Message.Element<?>) CatCodeMessageUtil.catCodeToMessage(img));


//        Path path = Paths.get("imgs/vme50.jpg");
//        PathResource imgResource = Resource.of(path);
//        Bot bot = event.getBot();
//        Image<?> image = bot.uploadImageBlocking(imgResource);
        for (Message.Element<?> element : messageList) {
            event.getFriend().sendBlocking(element);
        }



//        if (event instanceof ReplySupport) {
//            ((ReplySupport) event).replyBlocking(message);
//        }
    }

}
