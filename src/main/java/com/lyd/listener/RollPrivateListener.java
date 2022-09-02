package com.lyd.listener;

import com.lyd.utils.RollUtil;
import lombok.extern.slf4j.Slf4j;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnPrivate;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import java.util.Arrays;

@Slf4j
@Beans
public class RollPrivateListener {

    /**
     * @desc 定义参数
     */
    // 鉴定数组下标
    private final int bigSuccess = 0,ultimateSuccess = 1,hardSuccess = 2,success = 3,failure = 4,bigFailure = 5;
    // 骰子结果上界
    private final int topRange = 100;
    // 骰子次数上界
    private final int topTimes = 20;
    // 复读概率
    private final int repeatChance = 10;


    
    @OnPrivate
    public void privateMsg(PrivateMsg privateMsg, MsgSender sender) {
        if (RollUtil.getRandomRoll(100,1)>repeatChance) {
            return;
        }
        sender.SENDER.sendPrivateMsg(privateMsg,privateMsg.getMsgContent());
    }

    /**
     * @desc    .r
     * @param privateMsg
     * @param sender
     */
    @OnPrivate
    @Filter(value = "\\s*[.。]\\s*[rR]\\s*\\d*",matchType = MatchType.REGEX_MATCHES)
    public void roll(PrivateMsg privateMsg,MsgSender sender) {
        String username = privateMsg.getAccountInfo().getAccountNickname();
        String oriMsg = privateMsg.getMsgContent().getMsg();
        String sNum = oriMsg.replaceAll("\\s*[.。]\\s*[rR]\\s*", "");
        int max = 100;
        String reply;
        if (!sNum.isEmpty()) {
            max = Integer.parseInt(sNum);
        }
        String res = RollUtil.r(max);
        if (res==null) {
            reply = username+"真的太多了,大毛受不了了啦";
        } else {
            reply = "["+username+"]你骰出了！"+res;
        }

        sender.SENDER.sendPrivateMsg(privateMsg,reply);
    }

    /**
     * @desc .r 多重骰
     * @param privateMsg
     * @param sender
     */
    @OnPrivate
    @Filter(value = "\\s*[.。]\\s*[rR]\\s*\\d+#\\s*\\d*",matchType = MatchType.REGEX_MATCHES)
    public void multiRoll(PrivateMsg privateMsg,MsgSender sender) {
        String username = privateMsg.getAccountInfo().getAccountNickname();
        String oriMsg = privateMsg.getMsgContent().getMsg();
        String s = oriMsg.replaceAll("\\s*[.。]\\s*[rR]\\s*", "");
        String[] split = s.split("#");
        int rollRange=100;
        int rollNum = Integer.parseInt(split[0]);;
        if (split.length>=2 && !split[1].isEmpty()) {
            rollRange = Integer.parseInt(split[1]);
        }
        String reply = null;
        String res = RollUtil.r(rollRange, rollNum);
        if (res==null) {
            reply = "这...这里不行啦....实在太多了";
        } else {
            reply = "哼哼！让我看看"+username+"骰出了什么。。。啊啊啊啊可恶竟然是,"+res;
        }

        sender.SENDER.sendPrivateMsg(privateMsg,reply);
    }


    /**
     * @desc    .ra
     * @param privateMsg
     * @param sender
     */
    @OnPrivate
    @Filter(value = "\\s*[.。]\\s*[rR][aA]\\s*\\d+",matchType = MatchType.REGEX_MATCHES)
    public void rollAppraisal(PrivateMsg privateMsg,MsgSender sender) {
        String username = privateMsg.getAccountInfo().getAccountNickname();
        String oriMsg = privateMsg.getMsgContent().getMsg();
        String s = oriMsg.replaceAll("\\s*[.。]\\s*[rR][aA]\\s*", "");
        int appraisal = Integer.parseInt(s);
        String reply = RollUtil.ra(appraisal);
        if (reply==null) {
            reply = "纳尼?";
        } else {
            reply = "让我们看看"+username+"鉴定的结果罢！"+reply;
        }
        sender.SENDER.sendPrivateMsg(privateMsg,reply);
    }

    /**
     * @desc    .ra 多重鉴定
     * @param privateMsg
     * @param sender
     */
    @OnPrivate
    @Filter(value = "\\s*[.。]\\s*[rR][aA]\\s*\\d+#\\d+",matchType = MatchType.REGEX_MATCHES)
    public void multiRollAppraisal(PrivateMsg privateMsg,MsgSender sender) {
        String username = privateMsg.getAccountInfo().getAccountNickname();
        String oriMsg = privateMsg.getMsgContent().getMsg();
        String s = oriMsg.replaceAll("\\s*[.。]\\s*[rR][aA]\\s*", "");
        String[] split = s.split("#");
        int times = Integer.parseInt(split[0]);
        int appraisal = Integer.parseInt(split[1]);
        String reply = RollUtil.ra(appraisal,times);
        if (reply==null) {
            reply = "拜托，这个大毛就是逊诶，鉴定不了这么多";
        } else {
            reply = username + "进行了多多多多多多ddddd多次鉴定,结果是:\n"+reply;
        }

        sender.SENDER.sendPrivateMsg(privateMsg,reply);
    }


    @OnPrivate
    @Filter(value = "[.。]rb\\s*\\d*",matchType = MatchType.REGEX_MATCHES)
    public void rollBonus(PrivateMsg privateMsg,MsgSender sender) {
        String username = privateMsg.getAccountInfo().getAccountNickname();
        String oriMsg = privateMsg.getMsg();
        String num = oriMsg.replaceAll("[.。]rb\\s*", "");
        String reply = null;
        if (num.isEmpty()) {
            reply = RollUtil.rb(1);
        } else {
            reply = RollUtil.rb(Integer.parseInt(num));
        }


        reply = username + "想要丢个骰子吗？我帮你！"+reply;
        sender.SENDER.sendPrivateMsg(privateMsg,reply);
    }


    @OnPrivate
    @Filter(value = "[.。]rp\\s*\\d*",matchType = MatchType.REGEX_MATCHES)
    public void rollpublish(PrivateMsg privateMsg,MsgSender sender) {
        String username = privateMsg.getAccountInfo().getAccountNickname();
        String oriMsg = privateMsg.getMsg();
        String num = oriMsg.replaceAll("[.。]rp\\s*", "");
        int times,a=0;
        String reply;
        if (num.isEmpty()) {
            reply = RollUtil.rp(1);
        } else {
            reply = RollUtil.rp(Integer.parseInt(num));
        }

        reply = username + "想要丢个骰子吗？我帮你！"+reply;
        sender.SENDER.sendPrivateMsg(privateMsg,reply);
    }


}
