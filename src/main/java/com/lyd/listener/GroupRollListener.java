package com.lyd.listener;

import com.lyd.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.Bot;
import love.forte.simbot.Timestamp;
import love.forte.simbot.action.ReplySupport;
import love.forte.simbot.definition.Friend;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class GroupRollListener {

    private final int bigSuccess = 0,ultimateSuccess = 1,hardSuccess = 2,success = 3,failure = 4,bigFailure = 5;
    private final int topRange = 100;
    private final int topTimes = 20;
    private final int repeatChance = 10;

    @Listener
    public void repeat(GroupMessageEvent event){
        if (RandomUtil.getRandomRoll(100,1)>repeatChance) {
            return;
        }
        // 获取群成员的消息
        // getPlainText()为文本消息
        String nickname = event.getAuthor().getNickname();
        final String message = event.getMessageContent().getPlainText();
        log.info(nickname+":"+message);
        try {
            Thread.sleep(RandomUtil.getRandomRoll(3,6)* 600L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发送消息
        event.getGroup().sendBlocking(message);

    }

    @Listener
    @Filter("\\s*[.。]\\s*[rR]\\s*\\d*")
    public void roll(GroupMessageEvent event) {
        String username = event.getAuthor().getNickname();
        String oriMsg = event.getMessageContent().getPlainText();
        String sNum = oriMsg.replaceAll("\\s*[.。]\\s*[rR]\\s*", "");
        int max = 100;
        String reply;
        if (!sNum.isEmpty()) {
            max = Integer.parseInt(sNum);
        }
        if (max>topRange) {
            reply = username+"真的太多了,大毛受不了了啦";
        } else {
            int randomRoll = RandomUtil.getRandomRoll(max, 1);
            reply = "["+username+"]你骰出了！1d"+max+"="+randomRoll+"?";
        }

        if (event instanceof ReplySupport) {
            ((ReplySupport) event).replyBlocking(reply);
        }

//        log.info(event.getUser()+":"+event.getMessageContent());
    }

    @Listener
    @Filter("\\s*[.。]\\s*[rR]\\s*\\d+#\\s*\\d*")
    public void multiRoll(GroupMessageEvent event) {
        String username = event.getAuthor().getNickname();
        String oriMsg = event.getMessageContent().getPlainText();
        String s = oriMsg.replaceAll("\\s*[.。]\\s*[rR]\\s*", "");
        String[] split = s.split("#");
        int rollRange=100,total=0;
        int rollNum = rollNum = Integer.parseInt(split[0]);;
        if (split.length>=2 && !split[1].isEmpty()) {
            rollRange = Integer.parseInt(split[1]);
        }
        String reply = null;
        if (rollNum>topTimes || rollRange > topRange) {
            reply = "这...这里不行啦....实在太多了";
        } else {
            int[] rollResults = new int[rollNum];
            for (int i = 0; i < rollNum; i++) {
                rollResults[i] = RandomUtil.getRandomRoll(rollRange,1);
                total += rollResults[i];
            }
            String resultsMsg = Arrays.toString(rollResults)+"("+total+")";
            reply = "哼哼！让我看看"+username+"骰出了什么。。。啊啊啊啊可恶竟然是,"+s+resultsMsg;
        }

        if (event instanceof ReplySupport) {
            ((ReplySupport) event).replyBlocking(reply);
        }
    }

    @Listener
    @Filter("\\s*[.。]\\s*[rR][aA]\\s*\\d*")
    public void rollAppraisal (GroupMessageEvent event) {
        String username = event.getAuthor().getNickname();
        String oriMsg = event.getMessageContent().getPlainText();
        String s = oriMsg.replaceAll("\\s*[.。]\\s*[rR][aA]\\s*", "");
        int appraisal = Integer.parseInt(s);
        int randomRoll = RandomUtil.getRandomRoll(100, 1);
        String reply;
        if (s.isEmpty() || appraisal>topRange) {
            reply = "你想鉴定个啥子？？";
        } else {
            String result = null;
            int[] res = RandomUtil.getAppraisal(appraisal);
            if (randomRoll<=res[bigSuccess]) {
                result = "[大成功]";
            } else if (randomRoll <= res[ultimateSuccess]) {
                result = "[极难成功]";
            } else if (randomRoll <= res[hardSuccess]) {
                result = "[困难成功]";
            } else if (randomRoll <= res[success]) {
                result = "[成功]";
            } else if (randomRoll <= res[failure]) {
                result = "[失败]";
            } else {
                result = "[大失败]";
            }
            reply = "让我们看看"+username+"鉴定的结果罢！1d100="+randomRoll+"/"+appraisal + result;
        }


        if (event instanceof ReplySupport) {
            ((ReplySupport) event).replyBlocking(reply);
        }
    }

    @Listener
    @Filter("\\s*[.。]\\s*[rR][aA]\\s*\\d+#\\d+")
    public void multiRollAppraisal(GroupMessageEvent event) {
        String username = event.getAuthor().getNickname();
        String oriMsg = event.getMessageContent().getPlainText();
        String s = oriMsg.replaceAll("\\s*[.。]\\s*[rR][aA]\\s*", "");
        String[] split = s.split("#");
        int times = Integer.parseInt(split[0]);
        int appraisal = Integer.parseInt(split[1]);
        String reply = null;
        if (times>topTimes || appraisal>topRange) {
            reply = "拜托，这个大毛就是逊诶，鉴定不了这么多";
        } else {
            String[] results = new String[times];
            for (int i = 0; i < times; i++) {
                int randomRoll = RandomUtil.getRandomRoll(100, 1);
                results[i] = "1d100="+randomRoll+"/"+appraisal+" ";
                int[] res = RandomUtil.getAppraisal(appraisal);
                if (randomRoll<=res[bigSuccess]) {
                    results[i] += "[大成功]\n";
                } else if (randomRoll <= res[ultimateSuccess]) {
                    results[i] += "[极难成功]\n";
                } else if (randomRoll <= res[hardSuccess]) {
                    results[i] += "[困难成功]\n";
                } else if (randomRoll <= res[success]) {
                    results[i] += "[成功]\n";
                } else if (randomRoll <= res[failure]) {
                    results[i] += "[失败]\n";
                } else {
                    results[i] += "[大失败]\n";
                }
            }
            StringBuilder replyBuilder = new StringBuilder(username + "进行了多多多多多多ddddd多次鉴定,结果是:\n");
            for (int i = 0; i < results.length; i++) {
                replyBuilder.append(results[i]);
            }
            reply = replyBuilder.toString();
        }



        if (event instanceof ReplySupport) {
            ((ReplySupport) event).replyBlocking(reply);
        }
    }

}
