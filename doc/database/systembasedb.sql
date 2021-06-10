/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : systembasedb

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 17/04/2021 09:53:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_black_list
-- ----------------------------
DROP TABLE IF EXISTS `t_black_list`;
CREATE TABLE `t_black_list`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据值',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '引入数据字典 1.用户名 2.ip',
  `create_user` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_user` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_black_list
-- ----------------------------

-- ----------------------------
-- Table structure for t_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary`;
CREATE TABLE `t_dictionary`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据名称',
  `value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据值',
  `meaning` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '含义',
  `create_user` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(40) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dictionary
-- ----------------------------
INSERT INTO `t_dictionary` VALUES (1, 'TALK-TYPE', '1', '问答', 'admin', '2021-03-28 12:47:31', 'admin', '2021-03-28 12:47:44', NULL);
INSERT INTO `t_dictionary` VALUES (2, 'TALK-TYPE', '2', '公告', 'admin', '2021-03-28 12:47:31', 'admin', '2021-03-28 12:47:44', NULL);
INSERT INTO `t_dictionary` VALUES (3, 'TALK-TYPE', '3', '技术提问专区', 'admin', '2021-03-28 12:47:31', 'admin', '2021-03-28 12:47:44', NULL);
INSERT INTO `t_dictionary` VALUES (4, 'RESOURCETYPE_ID', '0', '菜单', 'admin', '2021-03-02 09:12:15', 'admin', '2021-03-02 09:12:15', '资源类型');
INSERT INTO `t_dictionary` VALUES (5, 'RESOURCETYPE_ID', '1', '功能', 'admin', '2021-03-02 09:12:15', 'admin', '2021-03-02 09:12:15', '资源类型');
INSERT INTO `t_dictionary` VALUES (6, 'BLACKLIST_TYPE', '0', '用户名', 'admin', '2021-04-12 15:05:05', 'admin', '2021-04-12 15:05:11', '黑名单类型');
INSERT INTO `t_dictionary` VALUES (7, 'BLACKLIST_TYPE', '1', 'ip', 'admin', '2021-04-12 15:06:41', 'admin', '2021-04-12 15:06:45', '黑名单类型');

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源描述',
  `seq` int(0) NOT NULL COMMENT '资源序号',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源地址',
  `pid` int(0) NULL DEFAULT NULL COMMENT '父级ID',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '0-菜单，1-功能 引用数据字典表',
  `create_user` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES (1, '系统管理', NULL, 100, '/system', NULL, '0', 'admin', '2021-03-29 17:46:27', 'admin', '2021-03-31 09:38:50');
INSERT INTO `t_resource` VALUES (2, '资源管理', NULL, 100, '/system/resource', 1, '0', 'admin', '2021-03-29 17:46:27', 'admin', '2021-03-29 18:23:32');
INSERT INTO `t_resource` VALUES (3, '角色管理', NULL, 101, '/system/role', 1, '0', 'admin', '2021-03-29 17:46:27', 'admin', '2021-03-29 18:21:23');
INSERT INTO `t_resource` VALUES (4, '系统用户管理', NULL, 102, '/system/sysuser', 1, '0', 'admin', '2021-03-29 17:46:27', 'admin', '2021-03-29 17:46:27');
INSERT INTO `t_resource` VALUES (11, '添加资源', NULL, 101, '/system/resource/add', 2, '1', 'admin', '2021-03-30 21:18:09', 'admin', '2021-03-31 09:26:19');
INSERT INTO `t_resource` VALUES (12, '修改资源', '', 102, '/system/resource/update', 2, '1', 'admin', '2021-03-30 21:27:06', 'admin', '2021-03-31 09:26:25');
INSERT INTO `t_resource` VALUES (13, '删除资源', '', 103, '/system/resource/delete', 2, '1', 'admin', '2021-03-30 22:05:46', 'admin', '2021-03-31 09:26:29');
INSERT INTO `t_resource` VALUES (14, '查询资源', '', 100, '/system/resource/list', 2, '1', 'admin', '2021-03-31 09:25:59', 'admin', '2021-04-01 15:19:57');
INSERT INTO `t_resource` VALUES (15, '添加角色', '', 101, '/system/role/add', 3, '1', 'admin', '2021-03-31 09:27:35', 'admin', '2021-03-31 09:27:35');
INSERT INTO `t_resource` VALUES (16, '查询角色', '', 100, '/system/role/list', 3, '1', 'admin', '2021-03-31 09:27:59', 'admin', '2021-03-31 09:27:59');
INSERT INTO `t_resource` VALUES (17, '修改角色', '', 102, '/system/role/update', 3, '1', 'admin', '2021-03-31 09:30:00', 'admin', '2021-03-31 09:30:00');
INSERT INTO `t_resource` VALUES (18, '删除角色', '', 103, '/system/role/delete', 3, '1', 'admin', '2021-03-31 09:30:35', 'admin', '2021-03-31 09:30:35');
INSERT INTO `t_resource` VALUES (19, '查询用户', '', 100, '/system/user/list', 4, '1', 'admin', '2021-03-31 09:32:31', 'admin', '2021-03-31 09:32:31');
INSERT INTO `t_resource` VALUES (20, '添加用户', '', 101, '/system/user/add', 4, '1', 'admin', '2021-03-31 09:32:55', 'admin', '2021-03-31 09:32:55');
INSERT INTO `t_resource` VALUES (21, '修改用户', '', 102, '/system/user/update', 4, '1', 'admin', '2021-03-31 09:33:21', 'admin', '2021-03-31 09:33:21');
INSERT INTO `t_resource` VALUES (22, '删除用户', '', 100, '/system/user/delete', 4, '1', 'admin', '2021-03-31 09:33:49', 'admin', '2021-03-31 09:33:49');
INSERT INTO `t_resource` VALUES (23, '用户加解锁', '', 104, '/system/user/operating', 4, '1', 'admin', '2021-03-31 09:35:50', 'admin', '2021-03-31 09:35:50');
INSERT INTO `t_resource` VALUES (24, '用户授权', '', 105, '/system/user/authorization', 4, '1', 'admin', '2021-03-31 09:36:48', 'admin', '2021-03-31 09:36:48');
INSERT INTO `t_resource` VALUES (25, '用户修改密码', '', 106, '/system/user/update/pwd', 4, '1', 'admin', '2021-03-31 09:38:00', 'admin', '2021-03-31 09:38:00');
INSERT INTO `t_resource` VALUES (47, '参数管理', '', 101, '/parameter', NULL, '0', 'admin', '2021-04-12 09:33:47', 'admin', '2021-04-12 09:33:47');
INSERT INTO `t_resource` VALUES (48, '数据字典管理', '', 100, '/dictionaries', 47, '0', 'admin', '2021-04-12 09:36:15', 'admin', '2021-04-12 10:13:02');
INSERT INTO `t_resource` VALUES (49, '添加字典', '', 101, '/system/dictionary/add', 48, '1', 'admin', '2021-04-12 09:36:53', 'admin', '2021-04-12 09:36:53');
INSERT INTO `t_resource` VALUES (50, '查询字典', '', 100, '/system/dictionary/list', 48, '1', 'admin', '2021-04-12 09:39:17', 'admin', '2021-04-12 09:39:17');
INSERT INTO `t_resource` VALUES (51, '修改字典', '', 102, '/system/dictionary/update', 48, '1', 'admin', '2021-04-12 10:07:56', 'admin', '2021-04-12 10:07:56');
INSERT INTO `t_resource` VALUES (52, '删除字典', '', 103, '/system/dictionary/delete', 48, '1', 'admin', '2021-04-12 10:08:23', 'admin', '2021-04-12 10:08:23');
INSERT INTO `t_resource` VALUES (53, '名单管理', '', 100, '/black', NULL, '0', 'admin', '2021-04-12 15:30:31', 'admin', '2021-04-12 15:30:31');
INSERT INTO `t_resource` VALUES (54, '黑名单管理', '', 100, '/list', 53, '0', 'admin', '2021-04-12 15:31:22', 'admin', '2021-04-12 15:31:22');
INSERT INTO `t_resource` VALUES (55, '查询黑名单', '', 100, '/system/black/list', 54, '1', 'admin', '2021-04-12 15:39:29', 'admin', '2021-04-12 15:39:29');
INSERT INTO `t_resource` VALUES (56, '添加黑名单', '', 101, '/system/black/add', 54, '1', 'admin', '2021-04-12 15:39:54', 'admin', '2021-04-12 15:39:54');
INSERT INTO `t_resource` VALUES (57, '修改黑名单', '', 102, '/system/black/update', 54, '1', 'admin', '2021-04-12 15:40:16', 'admin', '2021-04-12 15:40:16');
INSERT INTO `t_resource` VALUES (58, '删除黑名单', '', 103, '/system/black/delete', 54, '1', 'admin', '2021-04-12 15:40:36', 'admin', '2021-04-12 15:40:36');
INSERT INTO `t_resource` VALUES (59, '博客管理', '', 100, '/article', NULL, '0', 'admin', '2021-04-15 10:17:18', 'admin', '2021-04-15 10:17:18');
INSERT INTO `t_resource` VALUES (60, '博客', '', 100, '/article/list', 59, '0', 'admin', '2021-04-15 10:17:38', 'admin', '2021-04-15 10:17:38');
INSERT INTO `t_resource` VALUES (61, '查询博客', '', 100, '/backstage/article/list', 60, '1', 'admin', '2021-04-15 10:22:08', 'admin', '2021-04-15 10:22:08');
INSERT INTO `t_resource` VALUES (62, '添加博客', '', 101, '/web/article/add', 60, '1', 'admin', '2021-04-15 15:34:00', 'admin', '2021-04-15 15:34:00');
INSERT INTO `t_resource` VALUES (63, '修改博客', '', 102, '/web/article/update', 60, '1', 'admin', '2021-04-15 15:34:25', 'admin', '2021-04-15 15:34:25');
INSERT INTO `t_resource` VALUES (64, '删除博客', '', 103, '/backstage/article/delete', 60, '1', 'admin', '2021-04-15 16:09:06', 'admin', '2021-04-15 16:09:06');
INSERT INTO `t_resource` VALUES (65, '上推荐,好文,官方', '', 104, '/backstage/article/update', 60, '1', 'admin', '2021-04-15 17:06:36', 'admin', '2021-04-15 17:06:36');
INSERT INTO `t_resource` VALUES (66, '审核博客', '', 105, '/backstage/article/review', 60, '1', 'admin', '2021-04-15 18:27:04', 'admin', '2021-04-15 18:27:04');
INSERT INTO `t_resource` VALUES (67, '源码管理', '', 100, '/code', NULL, '0', 'admin', '2021-04-15 10:17:18', 'admin', '2021-04-15 10:17:18');
INSERT INTO `t_resource` VALUES (68, '源码', '', 100, '/code/list', 67, '0', 'admin', '2021-04-15 10:17:38', 'admin', '2021-04-15 10:17:38');
INSERT INTO `t_resource` VALUES (69, '查询源码', '', 100, '/backstage/code/list', 68, '1', 'admin', '2021-04-15 10:22:08', 'admin', '2021-04-15 10:22:08');
INSERT INTO `t_resource` VALUES (70, '添加源码', '', 101, '/web/code/add', 68, '1', 'admin', '2021-04-15 15:34:00', 'admin', '2021-04-15 15:34:00');
INSERT INTO `t_resource` VALUES (71, '修改源码', '', 102, '/web/code/update', 68, '1', 'admin', '2021-04-15 15:34:25', 'admin', '2021-04-15 15:34:25');
INSERT INTO `t_resource` VALUES (72, '删除源码', '', 103, '/backstage/code/delete', 68, '1', 'admin', '2021-04-15 16:09:06', 'admin', '2021-04-15 16:09:06');
INSERT INTO `t_resource` VALUES (73, '上推荐,好文,官方', '', 104, '/backstage/code/update', 68, '1', 'admin', '2021-04-15 17:06:36', 'admin', '2021-04-15 17:06:36');
INSERT INTO `t_resource` VALUES (74, '审核源码', '', 105, '/backstage/code/review', 68, '1', 'admin', '2021-04-15 18:27:04', 'admin', '2021-04-15 18:27:04');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `update_user` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `pid` int(0) NULL DEFAULT NULL COMMENT '父级角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '超管', '', 'admin', '2021-03-02 10:13:30', 'admin', '2021-03-02 10:13:30', NULL);

-- ----------------------------
-- Table structure for t_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource`  (
  `role_id` int(0) NOT NULL COMMENT '角色id',
  `resource_id` int(0) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`role_id`, `resource_id`) USING BTREE,
  INDEX `TROLE_ID`(`role_id`) USING BTREE,
  INDEX `idx_roleresource_roldid`(`role_id`) USING BTREE,
  INDEX `idx_roleresource_resourceid`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_resource
-- ----------------------------
INSERT INTO `t_role_resource` VALUES (1, 1);
INSERT INTO `t_role_resource` VALUES (1, 2);
INSERT INTO `t_role_resource` VALUES (1, 3);
INSERT INTO `t_role_resource` VALUES (1, 4);
INSERT INTO `t_role_resource` VALUES (1, 11);
INSERT INTO `t_role_resource` VALUES (1, 12);
INSERT INTO `t_role_resource` VALUES (1, 13);
INSERT INTO `t_role_resource` VALUES (1, 14);
INSERT INTO `t_role_resource` VALUES (1, 15);
INSERT INTO `t_role_resource` VALUES (1, 16);
INSERT INTO `t_role_resource` VALUES (1, 17);
INSERT INTO `t_role_resource` VALUES (1, 18);
INSERT INTO `t_role_resource` VALUES (1, 19);
INSERT INTO `t_role_resource` VALUES (1, 20);
INSERT INTO `t_role_resource` VALUES (1, 21);
INSERT INTO `t_role_resource` VALUES (1, 22);
INSERT INTO `t_role_resource` VALUES (1, 23);
INSERT INTO `t_role_resource` VALUES (1, 24);
INSERT INTO `t_role_resource` VALUES (1, 25);
INSERT INTO `t_role_resource` VALUES (1, 47);
INSERT INTO `t_role_resource` VALUES (1, 48);
INSERT INTO `t_role_resource` VALUES (1, 49);
INSERT INTO `t_role_resource` VALUES (1, 50);
INSERT INTO `t_role_resource` VALUES (1, 51);
INSERT INTO `t_role_resource` VALUES (1, 52);
INSERT INTO `t_role_resource` VALUES (1, 53);
INSERT INTO `t_role_resource` VALUES (1, 54);
INSERT INTO `t_role_resource` VALUES (1, 55);
INSERT INTO `t_role_resource` VALUES (1, 56);
INSERT INTO `t_role_resource` VALUES (1, 57);
INSERT INTO `t_role_resource` VALUES (1, 58);
INSERT INTO `t_role_resource` VALUES (1, 59);
INSERT INTO `t_role_resource` VALUES (1, 60);
INSERT INTO `t_role_resource` VALUES (1, 61);
INSERT INTO `t_role_resource` VALUES (1, 62);
INSERT INTO `t_role_resource` VALUES (1, 63);
INSERT INTO `t_role_resource` VALUES (1, 64);
INSERT INTO `t_role_resource` VALUES (1, 65);
INSERT INTO `t_role_resource` VALUES (1, 66);
INSERT INTO `t_role_resource` VALUES (1, 67);
INSERT INTO `t_role_resource` VALUES (1, 68);
INSERT INTO `t_role_resource` VALUES (1, 69);
INSERT INTO `t_role_resource` VALUES (1, 70);
INSERT INTO `t_role_resource` VALUES (1, 71);
INSERT INTO `t_role_resource` VALUES (1, 72);
INSERT INTO `t_role_resource` VALUES (1, 73);
INSERT INTO `t_role_resource` VALUES (1, 74);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态1：正常，0：锁定',
  `last_login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员',
  `pid` int(0) NULL DEFAULT NULL COMMENT '父级用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '系统管理员', '', NULL, '$2a$10$ECSsGGPxjsEWk27ue.TIxum63qXrNIKUsrAtMAM0QIqRmjTMgNuLe', '1', '2019-11-07 15:34:57', '2016-05-04 13:48:25', 'admin', '2019-09-01 10:39:22', 'admin', NULL);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `role_id` int(0) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `idx_roleuser_roldid`(`role_id`) USING BTREE,
  INDEX `idx_roleuser_userid`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(0) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(0) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
