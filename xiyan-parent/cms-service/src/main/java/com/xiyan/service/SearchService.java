package com.xiyan.service;

import com.xiyan.dto.SearchDTO;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.SearchVO;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/25 11:16
 */
public interface SearchService {

    /**
     * 搜索
     *
     * @param searchDTO
     * @return
     */
    CommonListVO<SearchVO> list(SearchDTO searchDTO);
}
