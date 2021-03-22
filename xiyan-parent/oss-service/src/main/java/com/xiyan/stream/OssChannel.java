package com.xiyan.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author bright
 * @date 2020-10-15
 */
public interface OssChannel {

    /**
     * 发消息的通道名称
     */

    String OSS_OUTPUT = "oss_output";

    /**
     * 消息的订阅通道名称
     */

    String OSS_INPUT = "oss_input";


    /**
     * 发消息通道
     *
     * @return
     */
    @Output(OSS_OUTPUT)
    MessageChannel sendMessageChannel();

    /**
     * 收消息的通道
     *
     * @return
     */
    @Input(OSS_INPUT)
    SubscribableChannel relieveMessage();
}
