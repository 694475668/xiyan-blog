package com.xiyan.util;


/**
 * 对比了雪花算法和美团的leaf算法生成订单id
 * 最终采用雪花算法生成订单id
 * Description：生成订单号工具类
 *
 * @author bright
 */
public class OrderNumberGenerationUtil {
    /**
     * 这里的0，0分别是
     *
     * @param workerId 工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)，可以写在配置文件中。
     */
    private static UniqueOrderGenerate idWorker = new UniqueOrderGenerate(0, 0);

    public static String getOrderId() {
        return DateUtils.getDateStr("yyyyMMdd") + idWorker.nextId();
    }

}