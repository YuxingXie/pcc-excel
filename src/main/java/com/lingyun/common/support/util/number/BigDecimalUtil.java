package com.lingyun.common.support.util.number;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: lxd
 * Date: 12-6-6
 * Time: 下午7:00
 * To change this template use File | Settings | File Templates.
 */
public class BigDecimalUtil {
    private static final Logger logger = LoggerFactory.getLogger(BigDecimalUtil.class);
    private BigDecimalUtil() {
    }
    public static void main(String[] args){
        logger.info(BigDecimalUtil.format_twoDecimal(-0.025d));
        logger.info(BigDecimalUtil.format_twoDecimal(0.025d));
        logger.info(BigDecimalUtil.format_twoDecimal(0.25d));
        logger.info(BigDecimalUtil.format_twoDecimal(2.5d));
        logger.info(BigDecimalUtil.format_twoDecimal(22.5d));
        logger.info(BigDecimalUtil.format_twoDecimal(222.5d));
        logger.info(BigDecimalUtil.format_twoDecimal(2222.5d));
        logger.info(BigDecimalUtil.format_twoDecimal(22222.5d));
        logger.info(BigDecimalUtil.format_twoDecimal(222222.5d));
        logger.info(BigDecimalUtil.format_twoDecimal(2222222.5d));
        logger.info(BigDecimalUtil.format_twoDecimal(22222222.5d));
        logger.info(BigDecimalUtil.format_twoDecimal(222222222.5d));
    }
    public   static   String   format_twoDecimal(double number)   {

        DecimalFormat df1   =   new   DecimalFormat( "0.00");
        return df1.format(number);
    }

    /**
     * 输入多个数字进行相乘
     * @param values
     * @return
     */
    public static Double multiply(Number... values) {
        BigDecimal result = new BigDecimal(1);
        for (Number v : values) {
            if(v==null){
                continue;
            }
            result = result.multiply(new BigDecimal(String.valueOf(v)), MathContext.DECIMAL32);
        }
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 输入多个数字进行相加
     * @param values
     * @return
     */
    public static Double add(Number... values) {
        BigDecimal result = BigDecimal.ZERO;
        for (Number v : values) {
            if(v==null){
                continue;
            }
            result = result.add(new BigDecimal(String.valueOf(v)), MathContext.DECIMAL32);
        }
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 前面减后面
     * @param start
     * @param end
     * @return
     */
    public static Double subtract(Number start, Number end) {
        BigDecimal result = new BigDecimal(String.valueOf(start==null?0:start)).subtract(new BigDecimal(String.valueOf(end==null?0:end)));
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 前台除后面
     * @param start
     * @param end
     * @return
     */
    public static Double divide(Number start, Number end) {
        BigDecimal result = new BigDecimal(String.valueOf(start==null?0:start)).divide(new BigDecimal(String.valueOf(end)), MathContext.DECIMAL32);
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
