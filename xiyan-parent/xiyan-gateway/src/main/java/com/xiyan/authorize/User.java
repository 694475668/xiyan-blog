package com.xiyan.authorize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author: bright
 * @date:Created in 2020-07-11 9:00
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class User {
    private String userId;
    private String username;
}
