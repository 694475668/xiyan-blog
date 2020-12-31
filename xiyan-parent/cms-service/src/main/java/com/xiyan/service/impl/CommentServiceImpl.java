package com.xiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.bo.ArticleBO;
import com.xiyan.bo.CodeBO;
import com.xiyan.constants.Constant;
import com.xiyan.domain.ArticleDO;
import com.xiyan.domain.CodeDO;
import com.xiyan.domain.CommentDO;
import com.xiyan.domain.UserDO;
import com.xiyan.dto.CommentAddDTO;
import com.xiyan.dto.CommentDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.mapper.ArticleMapper;
import com.xiyan.mapper.CodeMapper;
import com.xiyan.mapper.CommentMapper;
import com.xiyan.mapper.UserMapper;
import com.xiyan.repository.ArticleRepository;
import com.xiyan.repository.CodeRepository;
import com.xiyan.service.CommentService;
import com.xiyan.vo.BaseVO;
import com.xiyan.vo.CommentUserVO;
import com.xiyan.vo.CommentVO;
import com.xiyan.vo.CommonListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author: bright
 * @date:Created in 2020/12/13 12:38
 * @describe :
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private CodeRepository codeRepository;


    @Override
    @Cacheable(cacheNames = "comment", key = "#root.caches[0].name")
    public CommonListVO<CommentVO> list(CommentDTO commentDTO) {
        CommonListVO<CommentVO> commonListVO = new CommonListVO<>();
        List<CommentVO> commentVOList = new ArrayList<>();
        CommentVO commentVO = null;
        CommentUserVO commentUserVO = null;
        Integer userId = null;
        //根据id查询内容
        if (Constant.COMMENT_TYPE_BLOG.equals(commentDTO.getType())) {
            ArticleDO articleDO = articleMapper.selectById(commentDTO.getId());
            userId = articleDO.getUserId();
        } else if (Constant.COMMENT_TYPE_CODE.equals(commentDTO.getType())) {
            CodeDO codeDO = codeMapper.selectById(commentDTO.getId());
            userId = codeDO.getUserId();
        }
        //查询所属博客或者源码的评论信息
        List<CommentDO> commentDOList = commentMapper.selectList(new QueryWrapper<CommentDO>().and(commentDOQueryWrapper -> commentDOQueryWrapper.eq("info_id", commentDTO.getId()).eq("type", commentDTO.getType())));
        //排序
        commentDOList.sort(Comparator.comparing(CommentDO::getTargetUserId));
        for (CommentDO commentDO : commentDOList) {
            commentVO = new CommentVO();
            commentUserVO = new CommentUserVO();
            UserDO userDO = userMapper.selectById(commentDO.getCommentUserId());
            commentUserVO.setId(userDO.getId());
            commentUserVO.setNickName(userDO.getName());
            commentUserVO.setAvatar(userDO.getPhoto());
            commentVO.setCommentUser(commentUserVO);
            commentUserVO = new CommentUserVO();
            userDO = userMapper.selectById(commentDO.getTargetUserId());
            commentUserVO.setId(userDO.getId());
            commentUserVO.setNickName(userDO.getName());
            commentUserVO.setAvatar(userDO.getPhoto());
            commentVO.setTargetUser(commentUserVO);
            commentVO.setId(commentDO.getId());
            commentVO.setCreateDate(commentDO.getCreateTime());
            commentVO.setContent(commentDO.getContent());
            StringBuilder stringBuilder = new StringBuilder("<a>@" + userDO.getName() + ": </a>");
            //如果目标id是就是发布这篇博客的用户id，内容前面就不需要加@用户名
            if (!commentDO.getTargetUserId().equals(userId)) {
                commentVO.setContent(stringBuilder.append(commentDO.getContent()).toString());
            }
            commentVOList.add(commentVO);
        }
        commonListVO.setVoList(commentVOList);
        commonListVO.setTotal(Long.valueOf(commentVOList.size()));
        return commonListVO;
    }

    @Override
    @CacheEvict(cacheNames = "comment", key = "#root.caches[0].name")
    public BaseVO add(CommentAddDTO commentAddDTO, Integer id) {
        CommentDO commentDO = new CommentDO();
        BeanUtils.copyProperties(commentAddDTO, commentDO);
        commentDO.setCreateTime(new Date());
        commentDO.setCommentUserId(id);
        if (Constant.COMMENT_TYPE_BLOG.equals(commentAddDTO.getType())) {
            ArticleDO articleDO = articleMapper.selectById(commentAddDTO.getInfoId());
            if (articleDO.getUserId() == id) {
                return new BaseVO(false, ErrorCodeEnum.E0714.getKey(), ErrorCodeEnum.E0714.getValue());
            }
            commentDO.setTargetUserId(null == commentAddDTO.getTargetUserId() ? articleDO.getUserId() : commentAddDTO.getTargetUserId());
            commentMapper.insert(commentDO);
            //查询博客的评论数
            Integer count = commentMapper.selectCount(new QueryWrapper<CommentDO>().and(articleDOQueryWrapper -> articleDOQueryWrapper.eq("info_id", commentAddDTO.getInfoId()).eq("type", Constant.COMMENT_TYPE_BLOG)));
            //修改数据库的评论数
            articleDO = new ArticleDO();
            articleDO.setId(commentAddDTO.getInfoId());
            articleDO.setConCount(count);
            articleMapper.updateById(articleDO);
            //修改es的评论数
            ArticleBO article = articleRepository.findArticleById(commentAddDTO.getInfoId());
            article.setConCount(count);
            articleRepository.save(article);
        } else if (Constant.COMMENT_TYPE_CODE.equals(commentAddDTO.getType())) {
            CodeDO codeDO = codeMapper.selectById(commentAddDTO.getInfoId());
            if (codeDO.getUserId() == id) {
                return new BaseVO(false, ErrorCodeEnum.E0714.getKey(), ErrorCodeEnum.E0714.getValue());
            }
            commentDO.setTargetUserId(null == commentAddDTO.getTargetUserId() ? codeDO.getUserId() : commentAddDTO.getTargetUserId());
            commentMapper.insert(commentDO);
            //查询源码的评论数
            Integer count = commentMapper.selectCount(new QueryWrapper<CommentDO>().and(articleDOQueryWrapper -> articleDOQueryWrapper.eq("info_id", commentAddDTO.getInfoId()).eq("type", Constant.COMMENT_TYPE_CODE)));
            //修改数据库的评论数
            codeDO = new CodeDO();
            codeDO.setId(commentAddDTO.getInfoId());
            codeDO.setConCount(count);
            codeMapper.updateById(codeDO);
            //修改es的评论数
            CodeBO code = codeRepository.findArticleById(commentAddDTO.getInfoId());
            code.setConCount(count);
            codeRepository.save(code);
        } else if (Constant.COMMENT_TYPE_TALK.equals(commentAddDTO.getType())) {

        }
        return new BaseVO();
    }
}
