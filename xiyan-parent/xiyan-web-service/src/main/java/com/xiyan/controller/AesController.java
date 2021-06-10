package com.xiyan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.bo.ArticleBO;
import com.xiyan.bo.CodeBO;
import com.xiyan.constants.Constant;
import com.xiyan.domain.ArticleDO;
import com.xiyan.domain.CodeDO;
import com.xiyan.mapper.ArticleMapper;
import com.xiyan.mapper.CodeMapper;
import com.xiyan.repository.ArticleRepository;
import com.xiyan.repository.CodeRepository;
import com.xiyan.vo.AesVO;
import com.xiyan.vo.BaseVO;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @version :
 * @author: bright
 * @date:Created in 2020/7/9 13:36
 */
@Api(tags = "AES密钥集合")
@RestController
@Log4j2
public class AesController {
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private CodeRepository codeRepository;
    @Resource
    private CodeMapper codeMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleRepository articleRepository;


    @PostMapping("/aes/key")
    public BaseVO getKey() {
        AesVO aesVO = new AesVO();
        String aesKeypair = Constant.AES_KEYPAIR;
        //设置密钥到redis中
        redisTemplate.opsForValue().set(Constant.AES_KEY, aesKeypair);
        //获取密钥
        String keypair = redisTemplate.opsForValue().get(Constant.AES_KEY);
        log.info("写入redis-key [{}]", keypair);
        aesVO.setKey(keypair);
        return aesVO;
    }

    /**
     * 清理es并重新写入es数据
     *
     * @return
     */
    @GetMapping("/write")
    public BaseVO write() {
        //删除索引
        elasticsearchRestTemplate.indexOps(ArticleBO.class).delete();
        elasticsearchRestTemplate.indexOps(CodeBO.class).delete();
        //创建索引
        elasticsearchRestTemplate.indexOps(ArticleBO.class).create();
        elasticsearchRestTemplate.indexOps(CodeBO.class).create();
        List<CodeDO> codeDOList = codeMapper.selectList(new QueryWrapper<CodeDO>().eq("state", "1"));
        List<CodeBO> codeBOList = new ArrayList<>();
        for (CodeDO codeDO : codeDOList) {
            CodeBO codeBO = new CodeBO();
            BeanUtils.copyProperties(codeDO, codeBO);
            codeBOList.add(codeBO);
        }
        //插入到es中
        codeRepository.saveAll(codeBOList);
        List<ArticleDO> articleDOList = articleMapper.selectList(new QueryWrapper<ArticleDO>().eq("state", "1"));
        List<ArticleBO> articleBOList = new ArrayList<>();
        for (ArticleDO articleDO : articleDOList) {
            ArticleBO articleBO = new ArticleBO();
            BeanUtils.copyProperties(articleDO, articleBO);
            articleBOList.add(articleBO);
        }
        //插入到es中
        articleRepository.saveAll(articleBOList);
        return new BaseVO();
    }
}
