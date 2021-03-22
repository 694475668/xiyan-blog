package com.xiyan.feign;

import com.xiyan.back.UserFallback;
import com.xiyan.dto.GetUserDTO;
import com.xiyan.dto.UserUpdateDTO;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.UserByIdVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Description:  feign服务调用
 *
 * @author: bright
 * @date:Created in 2020/11/27 13:53
 */
@FeignClient(name = "system-base-service", fallbackFactory = UserFallback.class)
public interface UserFeign {

    /**
     * 根据参数查询
     *
     * @param getUserDTO
     * @return
     */
    @PostMapping("/user/by")
    UserByIdVO getUser(@RequestBody GetUserDTO getUserDTO);

    /**
     * 修改
     *
     * @param userUpdateDTO
     * @return
     */
    @PutMapping("/user/update")
    BaseVO update(@RequestBody UserUpdateDTO userUpdateDTO);
}
