package com.wuhobin.common.util.award;

import com.wuhobin.common.util.MathExtend;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author wuhongbin
 * @date 2023/5/8 0007
 */
@Slf4j
public class AwardUtil {

    private static int PROBABILITY_OFFSET = 100000000;

    /**
     * 判断是否中奖
     *
     * @param pbs 每个奖项的中奖概率
     * @return
     */
    public static int lottery(double... pbs) {
        double[] targetPbs = new double[pbs.length];
        for (int i = 0; i < targetPbs.length; i++) {
            targetPbs[i] = MathExtend.divide(pbs[i], 100.0);
        }

        double mixP = 0;
        for (int i = 0; i < targetPbs.length; i++) {
            mixP = MathExtend.add(targetPbs[i], mixP);
        }
        if (mixP > 1) {
            log.error("概率不合法:" + mixP);
            return -1;
        }

        int maxLuckNumber = (int) MathExtend.multiply(mixP, PROBABILITY_OFFSET);
        Random r = new Random();
        int randomNumber = r.nextInt(PROBABILITY_OFFSET);
        if (randomNumber < maxLuckNumber) {
            int range = 0;
            for (int i = 0; i < targetPbs.length; i++) {
                range += (int) MathExtend.multiply(targetPbs[i], PROBABILITY_OFFSET);
                if (randomNumber < range) {
                    return i + 0;
                }
            }
        }
        return -1;
    }

    /**  double 数组 代表 所有中奖的可能性，包含没中奖的情况，
     *   如果没中奖，需要把没中奖的概率也设置到 pbs中取，跟上面的方法不一样
     *   pbs的每项都小于100   sum(pbs) 小于等于100
     *   pbs中的值 可能为 0   不会返回-1，
     *   返回中奖的 pbs中的索引
     * @param pbs
     * @return
     */
    public static int zerolottery(double... pbs) {
        double[] targetPbs = new double[pbs.length];
        for (int i = 0; i < targetPbs.length; i++) {
            targetPbs[i] = MathExtend.divide(pbs[i], 100.0);
        }

        double mixP = 0;
        for (double targetPb : targetPbs) {
            mixP = MathExtend.add(targetPb, mixP);
        }
        if (mixP > 1) {
            throw new IllegalStateException("中奖概率之和大于1");
        }

        int maxLuckNumber = (int) MathExtend.multiply(mixP, PROBABILITY_OFFSET);
        Random r = new Random();

        /**    老代码 是这样的    int randomNumber = r.nextInt(PROBABILITY_OFFSET);
         *      老代码适合的情况是   mixP =1 的情况，即所有概率相加之和 等于1
         *      randomNumber  取值范围是 0到 PROBABILITY_OFFSET       maxLuckNumber=PROBABILITY_OFFSET
         *      现在的情况是   mixP的相加之和不等于1，小于1，因为某些产品的库存没有了，概率会设置为0，
         *      这时候  maxLuckNumber  小于  PROBABILITY_OFFSET
         *      如果依然用这行代码
         *       int randomNumber = r.nextInt(PROBABILITY_OFFSET);
         *       randomNumber的取值可能会大于 maxLuckNumber
         *       这时候 把所有的数相加而成的  range 总是小于  randomNumber   这时候会返回  -1
         */
         int randomNumber = r.nextInt(maxLuckNumber);
        if (randomNumber < maxLuckNumber) {
            int range = 0;
            for (int i = 0; i < targetPbs.length; i++) {
                range += (int) MathExtend.multiply(targetPbs[i], PROBABILITY_OFFSET);
                if (randomNumber < range) {
                    return i + 0;
                }
            }
        }
        log.error("中奖出错",pbs);
       throw new IllegalStateException("整除情况下不会运行到这里");
    }
}
