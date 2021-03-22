package com.xiyan.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bright
 */
public class ErrLogConfig extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        try {
            if (event == null)
                return "";
            if (StringUtils.isBlank(event.getMessage()))
                return "";
            // 尝试获取错误日志--------------------------------------------
            IThrowableProxy err = event.getThrowableProxy();
            StringBuilder errStr = null;
            if (err != null) {
                errStr = new StringBuilder();
                errStr.append(err.getClassName());
                errStr.append(" : ");
                errStr.append(err.getMessage());
                errStr.append(" \n ");
                StackTraceElementProxy[] errTrack = err.getStackTraceElementProxyArray();
                for (StackTraceElementProxy stackTraceElementProxy : errTrack) {
                    errStr.append(stackTraceElementProxy.getStackTraceElement());
                    errStr.append(" \n ");
                }
                errStr.delete(errStr.length() - 3, errStr.length());
                return errStr.toString();
            }
        } catch (Exception e) {
            return "ESEncoder日志工具错误";
        }

        return "";
    }

    public static String printReplace(String first, Object... replaces) {
        try {
            int replaceLen = 0;
            if (StringUtils.isBlank(first))
                return first;
            if (replaces == null || (replaceLen = replaces.length) == 0)
                return first;

            StringBuilder result = new StringBuilder();
            int replaceIdx = 0;
            int curCpIdx = 0;

            Matcher m = Pattern.compile("\\{\\}").matcher(first);
            while (m.find()) {
                if (replaceIdx < replaceLen) {
                    result.append(first.substring(curCpIdx, m.start()));
                    result.append(replaces[replaceIdx] == null ? "null" : replaces[replaceIdx].toString());
                    curCpIdx = m.end();
                } else {
                    result.append(first.substring(curCpIdx, first.length()));
                    break;
                }
                replaceIdx++;
            }
            result.append(first.substring(curCpIdx, first.length()));

            return result.toString();
        } catch (Throwable e) {
            return first;
        }
    }
}
