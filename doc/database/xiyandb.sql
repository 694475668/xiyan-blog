/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.22 : Database - xiyandb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`xiyandb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `xiyandb`;

/*Table structure for table `t_article` */

DROP TABLE IF EXISTS `t_article`;

CREATE TABLE `t_article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章封面',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章名称',
  `tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标签',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章简介',
  `read_count` int NOT NULL DEFAULT '0' COMMENT '文章阅读量',
  `con_count` int NOT NULL DEFAULT '0' COMMENT '文章评论数',
  `star_count` int NOT NULL DEFAULT '0' COMMENT '文章点赞数',
  `state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '审核是否通过：0待审核 1通过 2 不通过',
  `user_id` int NOT NULL COMMENT '发布人',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章内容  转换html的内容',
  `markdown_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'markdown 未转换html的内容',
  `markdown_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0. mavon-editor 1.editor-wang',
  `type` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章类型',
  `good` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '好文：0不是 1是',
  `recommend` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '推荐：0不是 1是',
  `official` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '官方出品：0不是 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='博客表';

/*Data for the table `t_article` */


/*Table structure for table `t_attention` */

DROP TABLE IF EXISTS `t_attention`;

CREATE TABLE `t_attention` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `attention_user_id` int NOT NULL COMMENT '被关注的用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='关注表';

/*Data for the table `t_attention` */

insert  into `t_attention`(`id`,`user_id`,`attention_user_id`,`create_time`) values (1,21,1,'2021-01-30 11:47:28'),(2,21,1,'2021-01-30 11:47:30'),(3,21,1,'2021-01-30 11:47:31'),(4,21,1,'2021-01-30 11:47:31'),(5,21,1,'2021-01-30 11:47:32'),(6,21,1,'2021-01-30 11:47:34'),(7,21,1,'2021-01-30 11:48:35'),(8,21,1,'2021-01-30 11:50:00');

/*Table structure for table `t_code` */

DROP TABLE IF EXISTS `t_code`;

CREATE TABLE `t_code` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '封面',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章简介',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `tag` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签',
  `download_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '下载地址',
  `read_count` int NOT NULL DEFAULT '0' COMMENT '阅读量',
  `star_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `con_count` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `download_count` int NOT NULL DEFAULT '0' COMMENT '下载数',
  `type` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统类型 0.JAVA 1.Python 2.GO 3.PHP 4.VUE 5.JavaScript 6.C 7.C++ 8.其它',
  `download_point` int NOT NULL DEFAULT '0' COMMENT '下载需要金币数',
  `state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '审核是否通过：0待审核 1通过 2 不通过',
  `user_id` int NOT NULL COMMENT '发布人',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容  转换html的内容',
  `markdown_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'markdown 未转换html的内容',
  `markdown_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0. mavon-editor 1.editor-wang',
  `top` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '置顶：0不是 1是',
  `boutique` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '精品：0不是 1是',
  `recommend` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '推荐：0不是 1是',
  `official` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '官方出品：0不是 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='源码表';

/*Data for the table `t_code` */

insert  into `t_code`(`id`,`pic`,`remark`,`title`,`tag`,`download_url`,`read_count`,`star_count`,`con_count`,`download_count`,`type`,`download_point`,`state`,`user_id`,`content`,`markdown_content`,`markdown_type`,`top`,`boutique`,`recommend`,`official`,`create_time`,`update_time`) values (232,'http://qiniu-picture.xiyanit.cn/QQ截图20200630130053.png','springboot前后端分离工资管理系统（带文档）','springboot前后端分离工资管理系统（带文档）','Java,','http://qiniu-file.xiyanit.cn/Group12-master.zip',150,0,0,1,'0',500,'1',1,'<p><strong>项目描述</strong></p><p>springboot前后端分离工资管理系统</p><p><strong>运行环境</strong></p><p>jdk8+tomcat8+mysql+IntelliJ IDEA或者Eclipse+maven</p><p><br></p><p><strong>项目技术(必填)</strong></p><p>springboot+vue</p><p><br></p><p><strong>数据库文件(可选)</strong></p><p>项目目录下</p><p><br></p><p><strong>依赖包文件(可选)</strong></p><p>压缩包超20M请把依赖包文件拆出上传到百度网盘,提供百度网盘分享地址(比如java的jar包)</p><p><br></p><p><strong>运行视频(可选)</strong></p><p>mp4视频文件请上传到百度网盘,提供百度网盘分享地址,加快官方审核速度</p><p><br></p><p><strong>项目截图(必填)</strong></p><p><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630130000.png\" style=\"max-width:100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630123832.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630130006.png\" style=\"max-width: 100%;\"></p><p><strong>运行截图(必填)</strong></p><p><span style=\"font-size: large;\">财务管理员: pyzy&nbsp; &nbsp;密码：123456kk</span></p><p><span style=\"font-size: large;\">院系管理员:&nbsp; 李华&nbsp; &nbsp; 密码：zhangjuan</span></p><p><span style=\"font-size: large;\">用户:&nbsp; 张三&nbsp; &nbsp; &nbsp;密码：newpass</span></p><p><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630125906.png\" style=\"max-width:100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630130053.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630130104.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630130113.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630130128.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630130136.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630130649.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200630130728.png\" style=\"max-width: 100%;\"></p><p><strong>注意事项(可选)</strong></p><p><strong><br></strong></p><p><span style=\"font-size: x-large;\">npm install&nbsp;\r\n\r\n</span></p><p><span style=\"font-size: x-large;\"><br></span></p><p><span style=\"font-size: x-large;\">npm install --save moment</span></p><p><span style=\"font-size: x-large;\"><br></span></p><p><span style=\"font-size: x-large;\">npm run&nbsp;</span></p>','','1','1','1','1','1','2020-06-30 13:16:46','2020-12-18 23:25:05'),(243,'http://qiniu-picture.xiyanit.cn/QQ截图20200704132035.png','springboot书城商城+后台','springboot书城商城+后台','Java,','http://qiniu-file.xiyanit.cn/bookstore-master.zip',262,0,0,2,'0',400,'1',1,'<p><strong>项目描述</strong></p><p>springboot书城商城+后台</p><p><strong>运行环境</strong></p><p>jdk8+tomcat8+mysql+IntelliJ IDEA或者Eclipse+maven</p><p><br></p><p><strong>项目技术(必填)</strong></p><p>springbootmybatis+bootstrap+jquery</p><p><br></p><p><strong>数据库文件(可选)</strong></p><p>项目目录下</p><p><br></p><p><strong>依赖包文件(可选)</strong></p><p>压缩包超20M请把依赖包文件拆出上传到百度网盘,提供百度网盘分享地址(比如java的jar包)</p><p><br></p><p><strong>运行视频(可选)</strong></p><p>mp4视频文件请上传到百度网盘,提供百度网盘分享地址,加快官方审核速度</p><p><br></p><p><strong>项目截图(必填)</strong></p><p><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132751.png\" style=\"max-width:100%;\"></p><p><strong>运行截图(必填)</strong></p><p>首页：<a href=\"http://localhost/\">http://localhost</a>:8080</p><p>后台：<a href=\"http://localhost/admin\">http://localhost/admin</a>&nbsp; &nbsp; &nbsp;密码都是123</p><p><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132035.png\" style=\"max-width:100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132044.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132058.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132112.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132123.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132129.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132136.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132237.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132250.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132313.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132541.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132547.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132553.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132617.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132622.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132629.png\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/QQ截图20200704132635.png\" style=\"max-width: 100%;\"></p><p><strong>注意事项(可选)</strong></p><p><br></p>','','1','1','1','1','1','2020-07-04 13:31:24','2020-12-20 03:03:32'),(248,'http://qiniu-picture.xiyanit.cn/d78437f0390d4400b4ebb51242d7e5f9.jpg','springboot客户管理系统','springboot客户管理系统','Java,','http://qiniu-file.xiyanit.cn/2020_06_28_springboot-kehu.zip',194,0,0,2,'0',200,'1',1,'<p><b>项目描述</b></p><p>很简单实用的一个springboot客户管理系统</p><p><b>运行环境</b></p><p>tomcat7.0+jdk1.7<font face=\"宋体\">或以上</font></p><p>eclipse<font face=\"宋体\">或</font><font face=\"Calibri\">idea</font></p><p><b>项目技术</b></p><p>springboot + mysql<br></p><p><b>运行截图</b></p><p><img src=\"http://qiniu-picture.xiyanit.cn/6e65b3ec35df49e6a4ff41582acb470d.jpg\" style=\"max-width:100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/c0ded8d22a204de8a3c497a28ad83a92.jpg\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/ce9e873c9e1b407c98477a7dba00b000.jpg\" style=\"max-width: 100%;\"><img src=\"http://qiniu-picture.xiyanit.cn/d78437f0390d4400b4ebb51242d7e5f9.jpg\" style=\"max-width: 100%;\"></p><p><br></p>','','1','1','1','1','1','2020-09-06 13:04:49','2020-12-23 17:41:06');

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `info_id` int DEFAULT NULL COMMENT '文章或者源码或者说说id',
  `comment_user_id` int NOT NULL COMMENT '评论id',
  `target_user_id` int NOT NULL COMMENT '被评论用户id',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '1.blog 2.code 3.shuoshuo',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论表';

/*Data for the table `t_comment` */

insert  into `t_comment`(`id`,`info_id`,`comment_user_id`,`target_user_id`,`content`,`create_time`,`type`) values (1,42,21,1,'[微笑]','2021-01-30 11:48:41','1'),(3,41,27,1,'[嘻嘻]','2021-02-02 21:07:19','1'),(4,61,49,1,'[嘻嘻]','2021-03-18 17:20:00','1');

/*Table structure for table `t_download` */

DROP TABLE IF EXISTS `t_download`;

CREATE TABLE `t_download` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `code_id` int NOT NULL COMMENT '源码id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='下载记录表';

/*Data for the table `t_download` */

insert  into `t_download`(`id`,`user_id`,`code_id`,`create_time`,`update_time`) values (1,4,212,'2021-01-29 22:18:20',NULL),(2,4,252,'2021-02-01 23:36:04',NULL),(3,4,62,'2021-02-01 23:39:43',NULL),(4,1,79,'2021-03-01 18:01:13',NULL),(5,39,251,'2021-03-10 16:12:33',NULL),(6,39,194,'2021-03-11 16:52:42',NULL),(7,1,157,'2021-03-12 15:22:00',NULL),(8,1,130,'2021-03-16 09:34:10',NULL),(9,1,162,'2021-03-16 10:47:03',NULL);

/*Table structure for table `t_favorites` */

DROP TABLE IF EXISTS `t_favorites`;

CREATE TABLE `t_favorites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `info_id` int NOT NULL COMMENT '资源id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型 1blog 2.code 3.shuoshuo',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='收藏表';

/*Data for the table `t_favorites` */

insert  into `t_favorites`(`id`,`user_id`,`info_id`,`create_time`,`update_time`,`type`) values (1,4,2,'2021-01-27 08:16:22',NULL,'2'),(2,4,2,'2021-01-27 08:16:22',NULL,'2'),(3,21,253,'2021-01-29 19:35:10',NULL,'2'),(4,21,253,'2021-01-29 19:35:11',NULL,'2'),(5,21,253,'2021-01-29 19:35:14',NULL,'2'),(6,21,42,'2021-01-30 11:48:13',NULL,'1'),(7,21,42,'2021-01-30 11:48:15',NULL,'1'),(8,21,42,'2021-01-30 11:48:15',NULL,'1'),(9,31,104,'2021-02-22 10:19:19',NULL,'2'),(10,31,104,'2021-02-22 10:19:21',NULL,'2'),(11,36,253,'2021-03-10 01:44:36',NULL,'2'),(12,36,253,'2021-03-10 01:44:42',NULL,'2'),(13,36,253,'2021-03-10 01:44:43',NULL,'2'),(14,36,253,'2021-03-10 01:44:43',NULL,'2'),(15,44,253,'2021-03-10 15:26:08',NULL,'2'),(16,44,253,'2021-03-10 15:26:09',NULL,'2'),(17,44,253,'2021-03-10 15:26:12',NULL,'2'),(18,1,59,'2021-03-11 18:32:04',NULL,'1'),(19,1,59,'2021-03-11 18:32:05',NULL,'1'),(20,1,59,'2021-03-11 18:32:05',NULL,'1'),(21,1,59,'2021-03-11 18:32:05',NULL,'1'),(22,1,59,'2021-03-11 18:32:06',NULL,'1'),(23,40,253,'2021-03-15 15:53:48',NULL,'2');

/*Table structure for table `t_friend_link` */

DROP TABLE IF EXISTS `t_friend_link`;

CREATE TABLE `t_friend_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '友链名称',
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '友链地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `t_friend_link` */

insert  into `t_friend_link`(`id`,`name`,`address`) values (1,'阿里云','https://www.aliyun.com/'),(2,'腾讯云','https://cloud.tencent.com/'),(3,'百度智能云','https://cloud.baidu.com/'),(10,'华为云','https://activity.huaweicloud.com/');

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='留言表';

/*Data for the table `t_message` */

insert  into `t_message`(`id`,`content`,`create_time`) values (8,'嘿嘿','2021-02-13 21:28:07'),(9,'留言板好像有bug，回车以后看不到别人的留言了','2021-02-13 21:29:21'),(10,'是吗','2021-03-06 15:38:37');

/*Table structure for table `t_music` */

DROP TABLE IF EXISTS `t_music`;

CREATE TABLE `t_music` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '音乐名称',
  `artist` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作者',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '歌曲来源',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '歌曲封面',
  `lrc` varchar(8000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '歌词',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `t_music` */

insert  into `t_music`(`id`,`name`,`artist`,`url`,`cover`,`lrc`) values (1,'沉醉的青丝 (想你 念你dj版)','你的大表哥曲甲','http://qiniu-mp3.xiyanit.cn/%E6%B2%89%E9%86%89%E7%9A%84%E9%9D%92%E4%B8%9D%20%28%E6%83%B3%E4%BD%A0%20%E5%BF%B5%E4%BD%A0dj%E7%89%88%29.mp3','http://p1.music.126.net/q34SncqK9YfGrKclq0jFmg==/109951165501197086.jpg?param=300x300','[00:00.000] 作词 : 辛沐/林乔[00:01.000] 作曲 : 武鹏飞[00:02.000] 编曲 : 中意[00:03.57]想你 念你[00:05.42]停止再继续[00:08.19]如果回忆容易[00:10.45]我会想你念你[00:12.30]假装你在这里[00:15.86]那本旧的日历[00:18.18]翻了日期[00:19.97]告诉我牢记不容易[00:23.19]如果可以作弊[00:24.97]我会想你念你[00:26.71]到最后的荼蘼[00:30.39]时间不会犹豫[00:32.71]等一缕[00:33.84]为你而沉醉的青丝[00:40.70]想你 念你[00:43.72]想你 念你[00:47.25]想你 念你[00:50.86]想你 念你[00:56.56]想你 念你[00:59.98]我追着你而去[01:03.61]想你 念你[01:07.23]停止再继续[01:11.51]我走过那条长椅[01:15.40]默守着那个秘密[01:18.57]拼命在脑海中寻你[01:24.55]如果回忆容易[01:26.96]我会想你念你[01:28.63]假装你在这里[01:32.14]那本旧的日历[01:34.49]翻了日期[01:36.31]告诉我牢记不容易[01:39.52]如果可以作弊[01:41.36]我会想你念你[01:43.17]到最后的荼蘼[01:46.77]时间不会犹豫[01:49.05]等一缕[01:50.30]为你而沉醉的青丝[01:56.31]想你 念你[01:59.96]想你 念你[02:03.61]想你 念你[02:07.19]等一缕[02:09.21]沉醉的青丝[02:10.72]出品方：网易音乐人x飓风计划[02:11.13]本歌曲来自〖网易飓风计划〗[02:11.59]10亿现金激励，千亿流量扶持！[02:11.92]业务联系：jf399@vip.163.com[02:12.22]混音：dB Audio Studio[02:12.54]录音：欧几[02:12.85]制作人：孙正洵[02:13.20]监制：何湲[02:14.20]策划：刘宇航/胡圣羽[02:14.54]宣传：王嘉晟/黄嘉慧/贾焱祺[02:14.83]DJ制作：深声文化（DJ Yaha）[02:15.12]OP: 讯飞音乐'),(2,'是想你的声音啊（DJ完整版）','傲七爷','http://qiniu-mp3.xiyanit.cn/%E6%98%AF%E6%83%B3%E4%BD%A0%E7%9A%84%E5%A3%B0%E9%9F%B3%E5%95%8A%EF%BC%88DJ%E5%AE%8C%E6%95%B4%E7%89%88%EF%BC%89.mp3','http://p2.music.126.net/cIR63lyPGgQ4mAyuOTg8lA==/109951165109878587.jpg?param=300x300','[00:00.05]出品方：网易音乐人x飓风工作室[00:00.06]10亿现金激励，千亿流量扶持！[00:00.07]网易音乐人合作：st399@vip.163.com[00:00.08]飓风计划商务合作：jf399@vip.163.com[00:00.09]作曲 : 小雨滴[00:00.42]作词 : 小雨滴[00:00.56]编曲：罗洋（卡其漠）[00:00.74]制作：DjYaha@深声文化[00:00.93]出品：匠心音乐[00:01.08]企划：网易音乐人x飓风计划[00:01.47]你快听 滴答滴 滴答滴 滴答滴 是雨滴的声音[00:07.63]你快听 是我在 是我在 是我在哼你最爱听的旋律[00:15.30]你快听 滴答滴 滴答滴 滴答滴 是雨滴的声音[00:22.73]你快听 是我在 是我在 是我在哼你最爱听的旋律[00:30.22]你快听 滴答滴 滴答滴 滴答滴 窗外又在下雨[00:37.65]难道这 不是你最爱的天气[00:45.87]该继续猜测[00:47.46]直接就开口吗[00:49.79]纠结如何说破[00:53.15]可越想了解的[00:55.50]越是无法琢磨[01:00.43]爱就是爱着[01:02.50]不爱就不爱了[01:04.89]请全部藏好它[01:08.17]可越想忘记的[01:11.90]反而记得深刻[01:17.24]你快听 滴答滴 滴答滴 滴答滴 是雨滴的声音[01:24.80]你快听 是我在 是我在 是我在哼你最爱听的旋律[01:32.24]你快听 滴答滴 滴答滴 滴答滴 是雨滴的声音[01:39.58]你快听 是我在 是我在 是我在哼你最爱听的旋律[01:47.15]你快听 滴答滴 滴答滴 滴答滴 窗外又在下雨[01:54.59]难道这 不是你最爱的天气[02:03.90][99:00.01]出品方：网易音乐人x飓风工作室[99:00.01]10亿现金激励，千亿流量扶持！[99:00.01]网易音乐人合作：st399@vip.163.com[99:00.01]飓风计划商务合作：jf399@vip.163.com'),(3,'备爱DJ版','周思涵','http://qiniu-mp3.xiyanit.cn/%E5%A4%87%E7%88%B1DJ.mp3','http://p2.music.126.net/g-TmCrEEW8T29M91ALrxHw==/109951164339640657.jpg?param=300x300','[00:00.355]作曲 : Xun(易硕成)[00:00.532]作词 : 莫凝/阿涵[00:01.319]我永远在你身后[00:02.585]傻傻陪伴 做你的备爱[00:04.953]也许朋友只是找个借口留下不离开[00:08.937]你永远不明白 我想要的未来[00:12.781]只是想变成你的习惯能被你宠爱[00:54.417]又一次你们闹别扭[00:57.953]又一次你伤心泪流[01:01.728]又一次你转身 留我一个[01:05.548]我以为你不会走[01:09.291]又一次你说放不开[01:12.909]又一次被你无心伤害[01:16.612]这次我还是在 可悲的存在感[01:20.257]会刺痛我的偏爱[01:25.496]我永远在你身后[01:26.979]傻傻陪伴 做你的备爱[01:29.406]也许朋友只是找个借口留下不离开[01:33.357]你永远不明白 我想要的未来[01:37.152]就是简单的依赖和有你的现在[01:40.478]我永远在你身后[01:41.964]傻傻等待 做你的备爱[01:44.609]也许朋友只是个理由能让你依赖[01:48.415]你永远不明白 最悲哀的备爱[01:52.062]只是想变成你的习惯能被你宠爱[02:30.112]在你身后傻傻陪伴的备爱[02:33.548]朋友只是找个借口不离开[02:37.221]你永远不明白 我想要的未来[02:40.924]就是简单的依赖和有你的现在[02:44.244]我永远在你身后[02:45.688]傻傻等待 做你的备爱[02:48.433]朋友只是个理由能让你依赖[02:54.049]做悲哀的备爱[02:57.910]能被你宠爱'),(4,'云与海','Kiki','http://qiniu-plug.xiyanit.cn/%E4%BA%91%E4%B8%8E%E6%B5%B7.mp3','http://p1.music.126.net/rj6Ul-n2WFz2Tx-ZMMnDxw==/109951165461137409.jpg','[00:00.000] 编曲 : GG龙[00:03.534]倘若时间重来能选择这起点[00:08.033]不再相隔云与海[00:10.850]无论爱的界限有多远[00:13.967]只为相恋[00:18.068]如果海角天边有你等在终点[00:22.401]只为厮守那天[00:25.284]到最后 两两相望 也值得[00:56.501]天边的云遥不可及[01:03.768]平行的距离无法靠近[01:09.466]我们背道而行[01:13.001]越走越远的距离[01:16.101]无声言语 久久不能平息[01:24.352]如果注定要爱上你[01:27.953]飞蛾赴火般的勇气[01:31.686]我只要你眼神坚定[01:34.513]不褪去[01:39.013]多想与你浑为一体[01:42.497]悱恻无期[01:45.330]来相信 结局不是唯一[01:52.647]倘若时间重来能选择这起点[01:57.147]不再相隔云与海[01:59.921]无论爱的界限有多远[02:03.066]只为相恋[02:07.165]如果海角天边有你等在终点[02:11.515]只为厮守那天[02:14.332]到最后 两两相望 也值得[02:37.182]如果注定要爱上你[02:40.747]飞蛾赴火般的勇气[02:44.431]我只要你眼神坚定[02:47.270]不褪去[02:51.631]多想与你浑为一体[02:55.215]悱恻无期[02:58.098]来相信 结局不是唯一[03:05.298]倘若时间重来能选择这起点[03:09.865]不再相隔云与海[03:12.548]无论爱的界限有多远[03:15.881]只为相恋[03:19.881]如果海角天边有你等在终点[03:24.181]只为厮守那天[03:27.181]到最后 两两相望 也值得[03:34.397]倘若时间重来能选择这起点[03:38.881]不再相隔云与海[03:41.730]无论爱的界限有多远[03:44.948]只为相恋[03:48.980]如果海角天边有你等在终点[03:53.282]只为厮守那天[03:56.270]到最后 两两相望 也值得');

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `price` decimal(10,2) NOT NULL COMMENT '金额',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型：1：充值 2：提现',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 1：待完成 2：已完成',
  `is_member` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pay_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付方式 1.微信 2.支付宝',
  `user_id` int NOT NULL COMMENT '充值人',
  `point` int NOT NULL COMMENT '充值金币',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `t_order` */

insert  into `t_order`(`id`,`order_id`,`price`,`type`,`status`,`is_member`,`pay_type`,`user_id`,`point`,`create_time`,`update_time`) values (2,'2021013072288779112546304','199.00','1','1','','1',1,0,'2021-01-30 11:29:48','2021-01-30 11:29:48'),(3,'2021013072288804693606400','199.00','1','1','','1',1,0,'2021-01-30 11:29:54','2021-01-30 11:29:54'),(4,'2021013072288821495988224','10.00','1','1','','1',1,100,'2021-01-30 11:29:58','2021-01-30 11:29:58'),(5,'2021013072289224409219072','199.00','1','1','','1',1,0,'2021-01-30 11:31:34','2021-01-30 11:31:34'),(6,'2021030584576300244664320','199.00','1','1','1','1',1,0,'2021-03-05 09:16:01',NULL),(7,'2021030584576329143418880','10.00','1','1','0','1',1,100,'2021-03-05 09:16:08',NULL),(8,'2021030986101298511872000','199.00','1','1','1','1',43,0,'2021-03-09 14:15:49',NULL),(9,'2021030986101375997444096','30.00','1','1','0','2',43,500,'2021-03-09 14:16:08',NULL),(10,'2021031186890588770664448','199.00','1','1','1','1',1,0,'2021-03-11 18:32:11',NULL),(11,'2021031588240528352608256','199.00','1','1','1','1',40,0,'2021-03-15 11:56:21',NULL),(12,'2021031588240588264046592','199.00','1','1','1','2',40,0,'2021-03-15 11:56:36',NULL);

/*Table structure for table `t_tv` */

DROP TABLE IF EXISTS `t_tv`;

CREATE TABLE `t_tv` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '电视名称',
  `type` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'rtmp/mp4' COMMENT '类型',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='电视表';

/*Data for the table `t_tv` */

insert  into `t_tv`(`id`,`name`,`type`,`url`) values (1,'CCTV-1综合','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv1hd'),(2,'CCTV-2财经','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv2hd'),(3,'CCTV-3综艺','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv3hd'),(4,'CCTV-4中文国际','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv4hd'),(5,'CCTV-5体育','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv5hd'),(6,'CCTV-6电影','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv6hd'),(7,'CCTV-7军事农业','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv7hd'),(8,'CCTV-8电视剧','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv8hd'),(9,'CCTV-9记录','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv9hd'),(10,'CCTV-10科教','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv10hd'),(11,'CCTV-11戏曲','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv11'),(12,'CCTV-12社会与法','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv12hd'),(13,'CCTV-13新闻','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv13'),(14,'CCTV-14少儿','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv14hd'),(15,'CCTV-15音乐','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv15'),(16,'安徽卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/ahhd'),(17,'兵团卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/bttv'),(18,'重庆卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cqhd'),(19,'东方卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/dfhd'),(20,'东南卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/dnhd'),(21,'广东卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/gdhd'),(22,'广西卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/gxtv'),(23,'甘肃卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/gstv'),(24,'贵州卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/gztv'),(25,'湖北卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/hbhd'),(26,'湖南卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/hunanhd'),(27,'河北卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/hebhd'),(28,'河南卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/hntv'),(29,'黑龙江卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/hljtv'),(30,'江苏卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/jshd'),(31,'江西卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/jxhd'),(32,'吉林卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/jltv'),(33,'辽宁卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/lnhd'),(34,'内蒙古卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/nmtv'),(35,'宁夏卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/nxtv'),(36,'青海卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/qhtv'),(37,'四川卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/schd'),(38,'山东卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/sdhd'),(39,'山西卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/sxrtv'),(40,'陕西卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/sxtv'),(41,'山东教育','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/sdetv'),(42,'中国教育-1','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cetv1'),(43,'中国教育-3','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cetv3'),(44,'中国教育-4','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cetv4'),(45,'CCTV-第一剧场','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/dyjctv'),(46,'CCTV-国防军事','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/gfjstv'),(47,'CCTV-怀旧剧场','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/hjjctv'),(48,'CCTV-风云剧场','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/fyjctv'),(49,'CCTV-风云足球','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/fyzqtv'),(50,'CCTV-风云音乐','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/fyyytv'),(51,'CCTV-世界地理','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/sjdltv'),(52,'CCTV 5+体育','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv5phd'),(53,'CGTN-新闻','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cctv16'),(54,'CETV-1','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cetv1'),(55,'CETV-3','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cetv3'),(56,'CETV-4','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/cetv4'),(57,'北京卫视高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv1hd'),(58,'北京影视高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv4hd'),(59,'北京体育高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv6hd'),(60,'北京新闻高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv9hd'),(61,'北京纪实高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv11hd'),(62,'北京文艺','rtmp/mp4','tmp://58.200.131.2:1935/livetv/btv2hd'),(63,'北京科教','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv3'),(64,'北京财经','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv5'),(65,'北京生活','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv7'),(66,'北京青年','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv8'),(67,'北京卡酷','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/btv10'),(69,'天津卫视高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/tjhd'),(71,'浙江卫视高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/zjhd'),(72,'深圳卫视高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/szhd'),(73,'黑龙江卫视高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/hljhd'),(74,'CHC高清电影','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/chchd'),(75,'上海纪实高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/docuchina'),(76,'金鹰纪实高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/gedocu'),(77,'全纪实高清','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/documentaryhd'),(78,'凤凰卫视中文台','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/fhzw'),(79,'凤凰卫视资讯台','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/fhzx'),(80,'凤凰卫视电影台','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/fhdy'),(81,'星空卫视','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/startv'),(82,'Star Sports','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/starsports'),(83,'Channel[V]','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/channelv'),(84,'探索频道','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/discovery'),(85,'国家地理频道','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/natlgeo'),(86,'CHC家庭影院','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/chctv'),(87,'CHC动作电影','rtmp/mp4','rtmp://58.200.131.2:1935/livetv/chcatv'),(88,'美国电视频道','rtmp/mp4','rtmp://media3.scctv.net/live/scctv_800'),(89,'香港财经','rtmp/mp4','rtmp://202.69.69.180:443/webcast/bshdlive-pc'),(90,'韩国GoodTV','rtmp/mp4','rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp'),(92,'美国1','rtmp/mp4','rtmp://ns8.indexforce.com/home/mystream'),(93,'美国2','rtmp/mp4','rtmp://media3.scctv.net/live/scctv_800');

/*Table structure for table `undo_log` */

DROP TABLE IF EXISTS `undo_log`;

CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `undo_log` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;