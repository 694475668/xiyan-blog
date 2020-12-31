import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/index/Index'

// 公共区域
// import CommonHeader from '@/components/header/CommonHeader';
import SimpleHeader from '@/components/header/SimpleHeader/SimpleHeader'
import CommonFooter from '@/components/footer/CommonFooter'

// 首页
import HomeContent from '@/components/content/HomeContent'
import ArticleContent from '@/components/content/ArticleContent'
import CodeContent from '@/components/content/CodeContent'
import ArticleList from '@/components/content/ArticleList'
import CodeList from '@/components/content/CodeList'
import TalkList from '@/components/content/TalkList'
import ToolContent from '@/components/content/ToolContent'
import SearchResultContent from '@/components/content/SearchResultContent'
import postArticle from '@/components/content/PostArticle'
import publishProject from '@/components/content/PublishProject'
import postExchange from '@/components/content/PostExchange'
import LoginContent from '@/components/content/LoginContent'
import Classroom from '@/components/content/Classroom'
import Tv from '@/components/content/Tv'
import Recharge from '@/components/content/Recharge'
import Home from '@/components/content/Home'
import MessageBoard from '@/components/content/MessageBoard'
import Leaderboard from '@/components/content/Leaderboard'
import RegistContent from '@/components/content/RegistContent'
import RetrievePassword from '@/components/content/RetrievePassword'

Vue.use(Router)

export default new Router({
    mode: 'history',
    // base:"/aqian_blog/",
    scrollBehavior: () => ({ y: 0 }),
    routes: [{
        path: '/',
        name: 'Index',
        component: Index,
        children: [
            //首页
            {
                path: '/',
                name: 'index',
                components: {
                    content: Home,
                }
            },
            {
                path: '/home',
                name: 'home',
                components: {
                    header: SimpleHeader,
                    content: HomeContent,
                    footer: CommonFooter
                }
            },
            //登录页
            {
                path: '/login',
                name: 'login',
                components: {
                    header: SimpleHeader,
                    content: LoginContent,
                    footer: CommonFooter
                }
            },
            //注册
            {
                path: '/regist',
                name: 'regist',
                components: {
                    header: SimpleHeader,
                    content: RegistContent,
                    footer: CommonFooter
                }
            },
            //修改密码
            {
                path: '/recover',
                name: 'recover',
                components: {
                    header: SimpleHeader,
                    content: RetrievePassword,
                    footer: CommonFooter
                }
            },
            //全部文章列表
            {
                path: '/articleList',
                name: 'articleList',
                components: {
                    header: SimpleHeader,
                    content: ArticleList,
                    footer: CommonFooter
                }
            },
            //文章详情页
            {
                path: '/article',
                name: 'article',
                components: {
                    header: SimpleHeader,
                    content: ArticleContent,
                    footer: CommonFooter
                }
            },
            //文章详情页
            {
                path: '/code',
                name: 'code',
                components: {
                    header: SimpleHeader,
                    content: CodeContent,
                    footer: CommonFooter
                }
            },
            //发表文章
            {
                path: '/postArticle',
                name: 'postArticle',
                components: {
                    header: SimpleHeader,
                    content: postArticle,
                    footer: CommonFooter
                }
            },
            {
                path: '/postExchange',
                name: 'postExchange',
                components: {
                    header: SimpleHeader,
                    content: postExchange,
                    footer: CommonFooter
                }
            },
            {
                path: '/publishProject',
                name: 'publishProject',
                components: {
                    header: SimpleHeader,
                    content: publishProject,
                    footer: CommonFooter
                }
            },
            //文章查询页
            {
                path: '/articles/search',
                name: 'search',
                components: {
                    header: SimpleHeader,
                    content: SearchResultContent,
                    footer: CommonFooter
                }
            },
            //源码分享
            {
                path: '/codes',
                name: 'codes',
                components: {
                    header: SimpleHeader,
                    content: CodeList,
                    footer: CommonFooter
                }
            },

            //交流
            {
                path: '/talk',
                name: 'talk',
                components: {
                    header: SimpleHeader,
                    content: TalkList,
                    footer: CommonFooter
                }
            },
            //工具
            {
                path: '/tool',
                name: 'tool',
                components: {
                    header: SimpleHeader,
                    content: ToolContent,
                    footer: CommonFooter
                }
            },
            //充值
            {
                path: '/recharge',
                name: 'recharge',
                components: {
                    header: SimpleHeader,
                    content: Recharge,
                    footer: CommonFooter
                }
            },
            //留言板
            {
                path: '/messageBoard',
                name: 'messageBoard',
                components: {
                    header: SimpleHeader,
                    content: MessageBoard,
                    footer: CommonFooter
                }
            },
            //排行榜
            {
                path: '/leaderboard',
                name: 'leaderboard',
                components: {
                    header: SimpleHeader,
                    content: Leaderboard,
                    footer: CommonFooter
                }
            },
            {
                path: '/classroom',
                name: 'classroom',
                components: {
                    header: SimpleHeader,
                    content: Classroom,
                    footer: CommonFooter
                }
            },
            {
                path: '/tv',
                name: 'tv',
                components: {
                    header: SimpleHeader,
                    content: Tv,
                    footer: CommonFooter
                }
            }
        ]
    }]
})