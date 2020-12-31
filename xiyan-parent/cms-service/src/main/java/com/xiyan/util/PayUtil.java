package com.xiyan.util;


import com.xiyan.bo.PaySaPiBO;
import com.xiyan.constants.Constant;
import com.xiyan.vo.OrderVO;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;


/**
 * @author bright
 */
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
        if (!StringUtils.isEmpty(paySaPiBO.getPrice())) {
            key += paySaPiBO.getPrice();
        }
        key += Constant.APP_SECRET;
        return paySaPiBO.getSign().equals(MD5Util.encryption(key));
    }
}
