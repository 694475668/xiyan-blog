package com.xiyan.back;

import com.xiyan.dto.GetUserDTO;
import com.xiyan.dto.UserUpdateDTO;
import com.xiyan.feign.UserFeign;
import com.xiyan.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/11/27 13:54
 */
@Component
public class UserFallback implements FallbackFactory<UserFeign> {

    @Override
    public UserFeign create(Throwable throwable) {
        return new UserFeign() {
            @Override
            public UserByIdVO getUser(GetUserDTO getUserDTO) {
                UserByIdVO userByIdVO = new UserByIdVO();
                userByIdVO.setName("服务降级");
                return userByIdVO;
            }

            @Override
            public BaseVO update(UserUpdateDTO userUpdateDTO) {
                return new BaseVO(false, "-999", "服务降级");
            }

        };
    }
}
