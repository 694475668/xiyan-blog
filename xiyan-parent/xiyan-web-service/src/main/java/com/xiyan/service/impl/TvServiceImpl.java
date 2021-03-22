package com.xiyan.service.impl;

import com.xiyan.domain.TvDO;
import com.xiyan.mapper.TvMapper;
import com.xiyan.service.TvService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.TvVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author: bright
 * @date:Created in 2020/12/17 16:31
 */
@Service
public class TvServiceImpl implements TvService {
    @Resource
    private TvMapper tvMapper;

    @Override
    @Cacheable(cacheNames = "tv", key = "#root.caches[0].name")
    public CommonListVO<TvVO> list() {
        List<TvDO> tvList = tvMapper.selectList(null);
        List<TvVO> list = ObjectConvertUtil.convertInstance().objectConvert(tvList, TvVO.class);
        CommonListVO commonListVO = new CommonListVO();
        commonListVO.setVoList(list);
        return commonListVO;
    }
}
