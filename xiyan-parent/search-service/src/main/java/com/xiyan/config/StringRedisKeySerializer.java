package com.xiyan.config;

import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;

/**
 * @author : bright
 * @Date: create in 2020/7/30 9:42
 * @Description:
 */
public class StringRedisKeySerializer extends StringRedisSerializer {

    private String prefix;

    public StringRedisKeySerializer(String prefix) {
        super();
        this.prefix = prefix;
    }

    public StringRedisKeySerializer(String prefix, Charset charset) {
        super(charset);
        this.prefix = prefix;
    }

    @Override
    public String deserialize(byte[] bytes) {
        String key = super.deserialize(bytes);
        if (key != null && key.startsWith(prefix)) {
            return key.substring(prefix.length() + 1);
        }
        return key;
    }

    @Override
    public byte[] serialize(String key) {
        String key1 = prefix + ":" + key;
        return super.serialize(key1);
    }

}
