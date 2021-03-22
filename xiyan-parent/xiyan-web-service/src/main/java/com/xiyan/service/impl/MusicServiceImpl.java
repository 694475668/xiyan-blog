package com.xiyan.service.impl;


import com.xiyan.domain.MusicDO;
import com.xiyan.mapper.MusicMapper;
import com.xiyan.service.MusicService;
import com.xiyan.util.ObjectConvertUtil;
import com.xiyan.vo.CommonListVO;
import com.xiyan.vo.MusicVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: bright
 * @date:Created in 2020/11/8 14:46
 * @describe :
 */
@Service
public class MusicServiceImpl implements MusicService {
    @Resource
    private MusicMapper musicMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Cacheable(value = "music", key = "#root.caches[0].name")
    public CommonListVO<MusicVO> list() {
        CommonListVO<MusicVO> common = new CommonListVO<>();
        List<MusicDO> musicList = musicMapper.selectList(null);
        //歌词换行
        for (MusicDO musicDO : musicList) {
            musicDO.setLrc(musicDO.getLrc().replaceAll("\\[", "\n$0"));
        }
        List<MusicVO> list = ObjectConvertUtil.convertInstance().objectConvert(musicList, MusicVO.class);
        common.setTotal(Long.valueOf(list.size()));
        common.setVoList(list);
        return common;
    }
}
