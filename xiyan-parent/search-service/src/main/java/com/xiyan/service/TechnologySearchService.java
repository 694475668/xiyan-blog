package com.xiyan.service;

import com.xiyan.dto.TechnologySearchDTO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.TechnologySearchVO;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/25 11:16
 */
public interface TechnologySearchService {

    /**
     * 搜索
     *
     * @param technologySearchDTO
     * @return
     */
    CommonListVO<TechnologySearchVO> list(TechnologySearchDTO technologySearchDTO);
}
