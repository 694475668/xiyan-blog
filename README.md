# 夕颜博客（K8S+Docker Compose容器编排+集成支付）
<br>
<br>
<p align="center">
  <img src="https://images.gitee.com/uploads/images/2021/0127/122407_d420f070_4856424.png "xiyan-logo.png" ></img>
</p>

<p align="center">
<a target="_blank" https://gitee.com/bright-boy/xiyan-blog">
    	<img src="https://img.shields.io/hexpm/l/plug.svg" ></img>
		<img src="https://img.shields.io/badge/JDK-1.8+-green.svg" ></img>
        <img src="https://img.shields.io/badge/Springboot-2.3.5.RELEASE-green" ></img>
<img src="https://img.shields.io/badge/SpringCloud-Hoxton.SR8-green" ></img>
<img src="https://img.shields.io/badge/Vue-2.5.17-green" ></img>
<img src="https://img.shields.io/badge/K8S-ff69b4" ></img>
<img src="https://img.shields.io/badge/Swagger-3.0.0-brightgreen" ></img>
<img src="https://img.shields.io/badge/mybatis-Plus_3.4.1-green" ></img>
<img src="https://img.shields.io/badge/SpringSecurity-blue" ></img>
<img src="https://img.shields.io/badge/Redis-red" ></img>
<img src="https://img.shields.io/badge/Kafka-blue" ></img>
<img src="https://img.shields.io/badge/七牛云-ff69b4" ></img>
<img src="https://img.shields.io/badge/AES数据加解密-ff69b4" ></img>
<img src="https://img.shields.io/badge/Zipkin-ff69b4" ></img>
<img src="https://img.shields.io/badge/Seata分布式事务-ff69b4" ></img>
<img src="https://img.shields.io/badge/ElasticSearch-green" ></img>
<img src="https://img.shields.io/badge/ELK+Kafka+Filebeat-ff69b4" ></img>
<img src="https://img.shields.io/badge/XXL-Job-ff69b4" ></img>
<img src="https://img.shields.io/badge/Sentinel-blue" ></img>
</a></p>


### 介绍
夕颜博客，一个基于微服务架构的前后端分离博客源码系统。Web端使用Vue + iView , 并且很好的适配移动端，目前支付宝小程序，微信小程序版，QQ小程序版，百度小程序版，字节跳动小程序版，Android版，IOS版，快应用版，360小程序版，H5版，采用的技术uniapp进行开发，一套代码发布10个平台，后端使用SpringCloudAlibaba + mybatis-plus进行开发，使用 Jwt 做登录验证，使用ElasticSearch作为全文检索服务，使用sentinel实现网关限流，熔断，降级，使用zipkin实现链路追踪，使用seate实现分布式事务，使用spring cloud stream 消息队列使用kafka，分布式任务调度XXL-JOB，使用ELK+Kafka+Filebeat日志收集，文件上传使用七牛云,数据加密AES,采用Nuxt.js作为服务端SSR渲染，加快SEO优化加快爬虫抓取,有Vue和Nuxt二套前端源码，个人免签支付系统采用的技术是SpringBoot+Layui+DB2+JPA，支持Linux和Windows一键部署夕颜博客系统，非常适合需要学习微服务的人才，采用K8S和docker compose二种容器编排模式 **如果不喜欢粒子特效的可以在App.vue里面进行注释**,觉得不错的，帮忙点个star
<br>
<br>
| 微信公众号，每天给大家提供技术干货  | QQ群欢迎各位一起来交流技术，探讨技术，互相学习  |
|---|---|
|<img src="./doc/qrcode_for_gh_7651310b1058_258.jpg" width="250"/>|<img src="./doc/215236_dda1a80d_4856424.jpeg" width="400"/>|


### 站点演示
【夕颜Vue前端】：http://vue.xiyanit.cn/ <br>
【夕颜支付】：http://pay.xiyanit.cn/ <br>
【夕颜后台管理系统】：http://manage.xiyanit.cn/

| 支付宝小程序版  | 微信小程序版  |
|---|---|
| <img src="./doc/ali.jpg" width="200"/>  | <img src="./doc/wechat.jpg" width="200"/>  |
| H5版  | QQ小程序版  |
| <img src="./doc/h5.png" width="200"/>  | <img src="./doc/qq.png" width="200"/>  |
| Android版  |
| <img src="./doc/app.png" width="250"/>  |

### 系统架构图

![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/172528_428cdedf_4856424.png "总体架构图.png")

### 后端技术

|      技术      |           说明            |                             官网                             |
| :------------: | :-----------------------: | :----------------------------------------------------------: |
|   SpringBoot   |          MVC框架          | [ https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
|  SpringCloud   |        微服务框架         |           https://spring.io/projects/spring-cloud/           |
|  MyBatis-Plus  |          ORM框架          |                   https://mp.baomidou.com/                   |
|   Swagger-UI   |       文档生产工具        | [ https://github.com/swagger-api/swagger-ui](https://github.com/swagger-api/swagger-ui) |
|     Kibana     |     分析和可视化平台      |               https://www.elastic.co/cn/kibana               |
| Elasticsearch  |         搜索引擎          | [ https://github.com/elastic/elasticsearch](https://github.com/elastic/elasticsearch) |
|     Filebeat|     轻量型数据采集器      |               https://www.elastic.co/cn/beats/filebeat               |
|    Logstash    | 用于接收Kafka的数据并处理 |              https://www.elastic.co/cn/logstash              |                |
|     kafka|         消息队列          |    http://kafka.apache.org/   |
|     mail|         邮箱          |     |
|     Seata|         分布式事务          |    http://seata.io/zh-cn/   |
|     Spring Security|         安全框架          |    https://spring.io/projects/spring-security/  |
|     fastjson|         Json数据化          |    https://github.com/alibaba/fastjson/   |
|     Sentinel|         流量管理          |    https://github.com/alibaba/Sentinel   |
|     Redis      |        分布式缓存         |                      https://redis.io/                       |
|     Docker     |        容器化部署         |      [ https://www.docker.com](https://www.docker.com/)      |
|     Hikari      |       数据库连接池        | https://github.com/brettwooldridge/HikariCP |
|     七牛云     |     七牛云 - 对象储存     |         https://developer.qiniu.com/sdk#official-sdk         |
|      JWT       |        JWT登录支持        |                 https://github.com/jwtk/jjwt                 |
|     SLF4J      |         日志框架          |                    http://www.slf4j.org/                     |
|     Lombok     |     简化对象封装工具      | [ https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok) |
|     Nginx      |  HTTP和反向代理web服务器  |                      http://nginx.org/                       |
|     Hutool     |      Java工具包类库       |                  https://hutool.cn/docs/#/                   |
|      AES|        数据加解密         |              https://blog.csdn.net/qq_40942490/article/details/107283195              |
|     Zipkin     |         链路追踪          |             https://github.com/openzipkin/zipkin             |
|   第三方登录 |      QQ 微博       |                      |
|   xxl-job|     分布式任务调度      |            https://www.xuxueli.com/xxl-job/            |

### 前端技术

|         技术          |                  说明                   |                             官网                             |
| :-------------------: | :-------------------------------------: | :----------------------------------------------------------: |
|        Vue.js         |                前端框架                 |                      https://vuejs.org/                      |
|      Vue-router       |                路由框架                 |                  https://router.vuejs.org/                   |
|         Vuex          |            全局状态管理框架             |                   https://vuex.vuejs.org/                    |
|        Nuxt.js        |        创建服务端渲染 (SSR) 应用        |                    https://zh.nuxtjs.org/                    |
|        iView        |               前端ui框架                |    [ http://v1.iviewui.com/docs/introduce](http://v1.iviewui.com/docs/introduce)    |
|         Axios         |              前端HTTP框架               | [ https://github.com/axios/axios](https://github.com/axios/axios) |                    
|       mavon-editor        |              富文本编辑器               |                    https://www.npmjs.com/package/mavon-editor                     
|     Highlight.js      |            代码语法高亮插件             |         https://github.com/highlightjs/highlight.js          |          |    |
|    bright-comment   |          Vue Emoji表情评论组件          |       https://gitee.com/bright-boy/bright-comment      |             |
|      vue-touch      |           移动端手指滑动插件            |         https://github.com/vuejs/vue-touch          |
|     vue-particles      |           粒子特效             |           https://github.com/whq920729/vue-particles            |
|      vue-social-share      |       分享插件       |                  https://github.com/nicolasbeauvais/vue-social-sharing                  |
|   crypto-js    |               数据加密               |        https://www.npmjs.com/package/crypto-js       |
|        vue-star-plus|            点赞插件             |                  https://github.com/vue-cabin/vue-star-plus                 |
|       vue-video-player        |         视频播放器          |             https://github.com/weilanwl/ColorUI              |
|      @moefe/vue-aplayer        |         音乐播放器          |             https://www.npmjs.com/package/@moefe/vue-aplayer              |
|       wangeditor | wangeditor富文本编辑器  |           http://www.wangeditor.com/            |
|       v-viewer| 富文本图片预览插件  |           https://www.npmjs.com/package/v-viewer            |
|       vue-monoplasty-slide-verify| vue滑动验证码  |           https://github.com/monoplasty/vue-monoplasty-slide-verify            |

### 环境要求

```
1. JDK（1.8+）
2. Maven (3.3.0+)
3. Redis服务 (3.0+)
4. MySQL (8.0+)
5. es（6.4）
6. Nginx
7. sentinel
8. kafka
9. ELK+Filebeat
10. Nacos
11. XXL-JOB
12. Seata
13. Zipkin


```
### 运行配置
夕颜源码使用了一些监控的 SpringCloud 组件，但是并不一定都需要部署，必须启动的服务包含

nacos，nginx，kafka， redis，mysql，xiyan-gateway，cms-service，oss-service，seate，xxl-job

其它的服务都可以不启动，也不影正常使用，可以根据自身服务器配置来启动

最低配置：1核2G 需要开启虚拟内存  [文档地址](http://xiyanit.cn/article?id=10)

推荐配置：2核4G 

### 项目目录

项目开发模式采用阿里巴巴规范进行开发,持久层DO,数据传输层DTO，业务BO,视图层VO  Java开发手册（嵩山版） 链接：https://pan.baidu.com/s/1KVEVXtpt7qEtKM__-00ysw 
提取码：w0n3 
1. - doc <br>
     1.1. - 数据库脚本<br>
     1.2. docker compose 一键部署脚本 <br>
     1.3. 本地后端和前端构建镜像脚本 <br>
     1.4. K8S 一键部署博客脚本 <br>
     1.5. Nginx部署配置文件 <br>
2. - xiyan-parent<br>
        2.1. - xiyan-gateway gateway 网关<br>
        2.2. - xiyan-web-service 夕颜web服务<br>
        2.3. - oss-service 文件服务<br>
        2.4. - system-base-service 系统基础服务<br>
        2.5. - backstage-service 夕颜后台服务<br>
        2.6. - user-oauth2-auth 认证服务<br>
        2.7. - user-oauth2-auth-api 认证Feign调用api<br>
        2.8. - search-service 搜索服务<br>
        2.9. - admin-server Spring Boot Admin监控服务
3. - nuxt-xiyan-frontend 夕颜源码Nuxt前端
4. - vue-xiyan-frontend 夕颜源码Vue前端
5. - vue-xiyan-backstage 夕颜源码后台管理系统
6. - xiyan-pay 夕颜个人免签支付
7. - xxl-job 分布式任务调度

### 项目地址

目前项目托管在 Gitee 和 Github 平台上中，欢迎大家 Star 和 Fork 支持~

Gitee地址：https://gitee.com/bright-boy/xiyan-blog <br><br>
Github地址：https://github.com/694475668/xiyan-blog

### 部署项目文档

https://bright-boy.gitee.io/docs

### 备用文档
http://doc.xiyanit.cn/


### 后期计划：

- [x] &nbsp;&nbsp;&nbsp;个人在线支付<br>
- [x] &nbsp;&nbsp;&nbsp;ElasticSearch全文检索<br>
- [x] &nbsp;&nbsp;&nbsp;七牛云对象存储<br>
- [x] &nbsp;&nbsp;&nbsp;在线评论，回复，（支持表情）<br>
- [x] &nbsp;&nbsp;&nbsp;集成wangEditor和mavon双编辑器<br>
- [x] &nbsp;&nbsp;&nbsp;ELFK+Kafka高吞吐量采集日志<br>
- [x] &nbsp;&nbsp;&nbsp;Docker compose实现容器编排与管理<br>
- [x] &nbsp;&nbsp;&nbsp;毕设源码<br>
- [x] &nbsp;&nbsp;&nbsp;在线电视直播<br>
- [x] &nbsp;&nbsp;&nbsp;集成Spring Security安全框架<br>
- [x] &nbsp;&nbsp;&nbsp;在线留言板<br>
- [x] &nbsp;&nbsp;&nbsp;第三方QQ，微博登陆<br>
- [x] &nbsp;&nbsp;&nbsp;Vue改造Nuxt<br>
- [x] &nbsp;&nbsp;&nbsp;K8S部署夕颜博客<br>
- [x] &nbsp;&nbsp;&nbsp;后台管理系统<br>
- [x] &nbsp;&nbsp;&nbsp;夕颜博客App<br>
- [x] &nbsp;&nbsp;&nbsp;夕颜博客小程序<br>
- [ ] &nbsp;&nbsp;&nbsp;在线聊天<br>
- [ ] &nbsp;&nbsp;&nbsp;夕颜App扫码登录web端<br>
- [ ] &nbsp;&nbsp;&nbsp;夕颜课堂<br>
- [ ] &nbsp;&nbsp;&nbsp;个人中心<br>

### 项目中初始用户和密码
后台：test 密码：yoostar403<br>
Mysql：用户：root，密码：yoostar403<br>
Redis：密码：yoostar403<br>
Nacos管理页面： 用户：nacos，密码：nacos<br>
Sentinel管理页面： 用户：sentinel，密码：sentinel<br>
xxl-job： 用户：admin，密码：123456<br>
支付： 用户：admin，密码：admin<br>

### Vue改Nuxt全面的SEO收录报告
![输入图片说明](https://images.gitee.com/uploads/images/2021/0420/094641_ab50e532_4856424.png "QQ截图20210420094352.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0420/094706_61d270ef_4856424.png "QQ截图20210420094509.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0420/094713_a0d314b4_4856424.png "QQ截图20210420094540.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0420/094723_fc5a202c_4856424.png "QQ截图20210420094553.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0420/094730_2dc0b17d_4856424.png "QQ截图20210420094602.png")

### 小程序，App截图
![输入图片说明](https://images.gitee.com/uploads/images/2021/0429/220223_322a9cb6_4856424.png "QQ截图20210429215850.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0429/220232_b38f2428_4856424.png "QQ截图20210429215909.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0429/220243_820fbe62_4856424.png "QQ截图20210429215933.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0429/220307_c73a0af4_4856424.png "QQ截图20210429215955.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0429/220315_bbcce819_4856424.png "QQ截图20210429220013.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0429/220322_bdfeca87_4856424.png "QQ截图20210429220026.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0429/220329_b6a6bb7b_4856424.png "QQ截图20210429220110.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0429/220337_92ec7ee6_4856424.png "QQ截图20210429220136.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0429/220346_5f422bc9_4856424.png "QQ截图20210429220206.png")

### 移动端截图
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/183856_a3d62b60_4856424.png "QQ截图20201229183512.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/183908_437dd294_4856424.png "QQ截图20201229183532.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/183921_03516694_4856424.png "QQ截图20201229183552.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/183930_57d94640_4856424.png "QQ截图20201229183604.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/183942_d78a6e34_4856424.png "QQ截图20201229183612.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/183951_cbb5befe_4856424.png "QQ截图20201229183622.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184002_b5a552e0_4856424.png "QQ截图20201229183657.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184013_d15f37d4_4856424.png "QQ截图20201229183723.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184022_de24f6af_4856424.png "QQ截图20201229183739.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184032_36e6c2fd_4856424.png "QQ截图20201229183749.png")
### PC端截图
登陆注册页
![输入图片说明](https://images.gitee.com/uploads/images/2021/0205/213116_23177c67_4856424.png "QQ截图20210205212951.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0205/213127_5bcc9bd5_4856424.png "QQ截图20210205213012.png")
毕设源码栏目
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184722_60578ae0_4856424.png "QQ截图20201229184541.png")
开发工具栏目
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184732_f4dd442e_4856424.png "QQ截图20201229184552.png")
技术交流栏目
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/151342_7cb291de_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/151357_7f1e9c10_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/151414_4e33d184_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/151432_d52a0029_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/151450_b7b32234_4856424.png "屏幕截图.png")

说说交流栏目
![输入图片说明](https://images.gitee.com/uploads/images/2021/0328/184218_62651915_4856424.png "QQ截图20210328184158.png")
金币充值栏目
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184751_28070bcc_4856424.png "QQ截图20201229184557.png")
排行榜栏目
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184800_855ebe34_4856424.png "QQ截图20201229184604.png")
留言板栏目
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184809_188ab654_4856424.png "QQ截图20201229184615.png")
电视直播栏目
![![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/184833_1a09413e_4856424.png "QQ截图20201229184712.png")](https://images.gitee.com/uploads/images/2020/1229/184824_716e194a_4856424.png "QQ截图20201229184703.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1229/185032_f9943d1c_4856424.png "QQ截图20201229184957.png")
内容详情
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/151525_397f4596_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/151609_0c1a7b1c_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/151627_0c0c4c58_4856424.png "屏幕截图.png")
评论
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/163907_59e3cfc8_4856424.png "屏幕截图.png")

### 夕颜后台管理系统第一版
![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/150310_c421cbcf_4856424.png "QQ截图20210416150210.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/150322_d3b3f6c4_4856424.png "QQ截图20210416150219.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/150331_5ca2de84_4856424.png "QQ截图20210416150227.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/150340_43f4950c_4856424.png "QQ截图20210416150236.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/150350_850a0444_4856424.png "QQ截图20210416150245.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/150359_277d912c_4856424.png "QQ截图20210416150252.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/150407_fda8a155_4856424.png "QQ截图20210416150302.png")

### 支付系统
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/171011_7ad727f0_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/171023_5e9a10e9_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/171033_54a27e37_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/171043_0b84f353_4856424.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0112/171052_95b2ce55_4856424.png "屏幕截图.png")