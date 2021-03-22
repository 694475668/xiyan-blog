package com.xiyan.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiyan.bo.ArticleBO;
import com.xiyan.bo.CodeBO;
import com.xiyan.constants.Constant;
import com.xiyan.domain.ArticleDO;
import com.xiyan.domain.CodeDO;
import com.xiyan.domain.CommentDO;
import com.xiyan.dto.CommentAddDTO;
import com.xiyan.dto.CommentDTO;
import com.xiyan.dto.GetUserDTO;
import com.xiyan.enumeration.ErrorCodeEnum;
import com.xiyan.feign.UserFeign;
import com.xiyan.mapper.ArticleMapper;
import com.xiyan.mapper.CodeMapper;
import com.xiyan.mapper.CommentMapper;
import com.xiyan.repository.ArticleRepository;
import com.xiyan.repository.CodeRepository;
import com.xiyan.service.CommentService;
import com.xiyan.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
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
    private UserFeign userFeign;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private CodeRepository codeRepository;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public CommonListVO<CommentVO> list(CommentDTO commentDTO) {
        CommonListVO<CommentVO> commonListVO = new CommonListVO<>();
        String data = redisTemplate.opsForValue().get(commentDTO.getType() + "_commentList_" + commentDTO.getId());
        if (StringUtils.isNotBlank(data)) {
            return JSONArray.parseObject(data, CommonListVO.class);
        }
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
        List<CommentVO> commentVOList = getCommentList(commentDOList, userId);
        commonListVO.setVoList(commentVOList);
        commonListVO.setTotal(Long.valueOf(commentVOList.size()));
        //写入redis
        redisTemplate.opsForValue().set(commentDTO.getType() + "_commentList_" + commentDTO.getId(), JSONArray.toJSONString(commonListVO));
        return commonListVO;
    }

    @Override
    public BaseVO add(CommentAddDTO commentAddDTO, Integer id) {
        CommentDO commentDO = new CommentDO();
        BeanUtils.copyProperties(commentAddDTO, commentDO);
        commentDO.setCreateTime(new Date());
        commentDO.setCommentUserId(id);
        if (Constant.COMMENT_TYPE_BLOG.equals(commentAddDTO.getType())) {
            ArticleDO articleDO = articleMapper.selectById(commentAddDTO.getInfoId());
            if (articleDO.getUserId() == id && null == commentAddDTO.getTargetUserId()) {
                return new BaseVO(false, ErrorCodeEnum.E0707.getKey(), ErrorCodeEnum.E0707.getValue());
            }
            commentDO.setTargetUserId(null == commentAddDTO.getTargetUserId() ? articleDO.getUserId() : commentAddDTO.getTargetUserId());
            commentMapper.insert(commentDO);
            //查询博客的评论数
            Integer count = commentMapper.selectCount(new QueryWrapper<CommentDO>().and(articleDOQueryWrapper -> articleDOQueryWrapper.eq("info_id", commentAddDTO.getInfoId()).eq("type", Constant.COMMENT_TYPE_BLOG)));
            //修改数据库的评论数
            ArticleDO newArticle = new ArticleDO();
            newArticle.setId(commentAddDTO.getInfoId());
            newArticle.setConCount(count);
            articleMapper.updateById(newArticle);
            //修改es的评论数
            ArticleBO article = articleRepository.findArticleById(commentAddDTO.getInfoId());
            article.setConCount(count);
            articleRepository.save(article);
            //写入redis
            CommonListVO<CommentVO> commonListVO = new CommonListVO<>();
            //查找数据库的评论
            List<CommentDO> commentDOList = commentMapper.selectList(new QueryWrapper<CommentDO>().and(articleDOQueryWrapper -> articleDOQueryWrapper.eq("info_id", commentAddDTO.getInfoId()).eq("type", Constant.COMMENT_TYPE_BLOG)));
            List<CommentVO> commentVOList = getCommentList(commentDOList, articleDO.getUserId());
            commonListVO.setVoList(commentVOList);
            commonListVO.setTotal(Long.valueOf(commentVOList.size()));
            redisTemplate.opsForValue().set(commentAddDTO.getType() + "_commentList_" + commentAddDTO.getInfoId(), JSONArray.toJSONString(commonListVO));

        } else if (Constant.COMMENT_TYPE_CODE.equals(commentAddDTO.getType())) {
            CodeDO codeDO = codeMapper.selectById(commentAddDTO.getInfoId());
            if (codeDO.getUserId() == id && null == commentAddDTO.getTargetUserId()) {
                return new BaseVO(false, ErrorCodeEnum.E0707.getKey(), ErrorCodeEnum.E0707.getValue());
            }
            commentDO.setTargetUserId(null == commentAddDTO.getTargetUserId() ? codeDO.getUserId() : commentAddDTO.getTargetUserId());
            commentMapper.insert(commentDO);
            //查询源码的评论数
            Integer count = commentMapper.selectCount(new QueryWrapper<CommentDO>().and(articleDOQueryWrapper -> articleDOQueryWrapper.eq("info_id", commentAddDTO.getInfoId()).eq("type", Constant.COMMENT_TYPE_CODE)));
            //修改数据库的评论数
            CodeDO newCode = new CodeDO();
            newCode.setId(commentAddDTO.getInfoId());
            newCode.setConCount(count);
            codeMapper.updateById(newCode);
            //修改es的评论数
            CodeBO code = codeRepository.findArticleById(commentAddDTO.getInfoId());
            code.setConCount(count);
            codeRepository.save(code);
            //写入redis
            CommonListVO<CommentVO> commonListVO = new CommonListVO<>();
            //查找数据库的评论
            List<CommentDO> commentDOList = commentMapper.selectList(new QueryWrapper<CommentDO>().and(articleDOQueryWrapper -> articleDOQueryWrapper.eq("info_id", commentAddDTO.getInfoId()).eq("type", Constant.COMMENT_TYPE_CODE)));
            List<CommentVO> commentVOList = getCommentList(commentDOList, codeDO.getUserId());
            commonListVO.setVoList(commentVOList);
            commonListVO.setTotal(Long.valueOf(commentVOList.size()));
            redisTemplate.opsForValue().set(commentAddDTO.getType() + "_commentList_" + commentAddDTO.getInfoId(), JSONArray.toJSONString(commonListVO));
        } else if (Constant.COMMENT_TYPE_TALK.equals(commentAddDTO.getType())) {

        }
        return new BaseVO();
    }

    private List<CommentVO> getCommentList(List<CommentDO> commentDOList, Integer userId) {
        CommentVO commentVO = null;
        CommentUserVO commentUserVO = null;
        List<CommentVO> commentVOList = new ArrayList<>();
        for (CommentDO commentDO : commentDOList) {
            commentVO = new CommentVO();
            commentUserVO = new CommentUserVO();
            UserByIdVO user = userFeign.getUser(new GetUserDTO(commentDO.getCommentUserId(), null));
            commentUserVO.setId(user.getId());
            commentUserVO.setNickName(user.getName());
            commentUserVO.setAvatar(user.getPhoto());
            commentVO.setCommentUser(commentUserVO);
            commentUserVO = new CommentUserVO();
            user = userFeign.getUser(new GetUserDTO(commentDO.getTargetUserId(), null));
            commentUserVO.setId(user.getId());
            commentUserVO.setNickName(user.getName());
            commentUserVO.setAvatar(user.getPhoto());
            commentVO.setTargetUser(commentUserVO);
            commentVO.setId(commentDO.getId());
            commentVO.setCreateDate(commentDO.getCreateTime());
            commentVO.setContent(commentDO.getContent());
            StringBuilder stringBuilder = new StringBuilder("<a>@" + user.getName() + ": </a>");
            //如果目标id是就是发布这篇博客的用户id，内容前面就不需要加@用户名
            if (!commentDO.getTargetUserId().equals(userId)) {
                commentVO.setContent(stringBuilder.append(commentDO.getContent()).toString());
            }
            commentVOList.add(commentVO);
        }
        return commentVOList;
    }
}
