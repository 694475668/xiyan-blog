package com.xiyan.util;


import com.xiyan.bo.PaySaPiBO;
import com.xiyan.constants.Constant;
import com.xiyan.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;


/**
 * @author bright
 */
@Slf4j
public class PayUtil {

    public static String getSign(OrderVO orderVO) throws UnsupportedEncodingException {
        String key = "";
        if (null != orderVO.getPayId()) {
            key += orderVO.getPayId();
        }
        if (null != orderVO.getParam()) {
            key += orderVO.getParam();
        }
        if (null != orderVO.getType()) {
            key += orderVO.getType();
        }
        if (null != orderVO.getPrice()) {
            key += orderVO.getPrice();
        }
        key += Constant.APP_SECRET;
        return MD5Util.encryption(key);
    }

    public static boolean checkPayKey(PaySaPiBO paySaPiBO) throws UnsupportedEncodingException {
        String key = "";
        if (!StringUtils.isBlank(paySaPiBO.getPayId())) {
            key += paySaPiBO.getPayId();
        }
        if (!StringUtils.isBlank(paySaPiBO.getParam())) {
            key += paySaPiBO.getParam();
        }
        if (!StringUtils.isBlank(paySaPiBO.getType())) {
            key += paySaPiBO.getType();
        }
        if (paySaPiBO.getPrice() != null) {
            key += paySaPiBO.getPrice();
        }
        if (paySaPiBO.getReallyPrice() != null) {
            key += paySaPiBO.getReallyPrice();
        }
        key += Constant.APP_SECRET;
        log.info(key);
        log.info("加密后的：{}", MD5Util.encryption(key));
        return paySaPiBO.getSign().equals(MD5Util.encryption(key));
    }
}
