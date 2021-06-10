const webpack = require('webpack')
export default {
    // Global page headers: https://go.nuxtjs.dev/config-head
    head: {
        htmlAttrs: {
            lang: 'en'
        },
        meta: [
            { charset: 'utf-8' },
            { name: 'viewport', content: 'width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no' },
            { hid: 'description', name: 'description', content: '' }
        ],
        link: [
            { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
        ],
        // script: [
        //     //<!-- 51统计 -->
        //     {
        //         type: 'text/javascript',
        //         src: 'https://js.users.51.la/21038499.js'
        //     },
        //     // <!-- 百度统计 -->
        //     {
        //         src: 'https://hm.baidu.com/hm.js?88b551f7172ccc8bbee6f8928f5abbce'
        //     },
        //     //<!-- 360自动收录 -->
        //     {
        //         id: 'sozz',
        //         src: 'https://jspassport.ssl.qhimg.com/11.0.1.js?d182b3f28525f2db83acfaaf6e696dba'
        //     },
        // ],
    },

    // Global CSS: https://go.nuxtjs.dev/config-css
    css: [
        'view-design/dist/styles/iview.css',
        '@/icon/iconfont.css',
        'element-ui/lib/theme-chalk/index.css',
        '@/assets/stylus/index.styl',
        '@/assets/stylus/article.css',
        '@/assets/stylus/article.styl',
        '@/assets/stylus/main.css',
        '@/assets/stylus/theme.styl',
        '@/assets/stylus/mixin.styl',
        '@/assets/stylus/index.css',
        '@/assets/stylus/base.styl',
        'mavon-editor/dist/css/index.css',
        'babel-polyfill',
        'viewerjs/dist/viewer.css',
        '@/assets/css/main.css',
        'vue-social-share/dist/client.css'
    ],

    // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
    plugins: [{
            src: '@/plugins/view-ui',
            //是否需要服务端熏染
            ssr: true,
        }, {
            src: '~/icon/iconfont.js',
            //是否需要服务端熏染
            ssr: false,
        }, //elementui
        {
            src: '@/plugins/element-ui',
            //是否需要服务端熏染
            ssr: true,
        }
        //引入jquery的爱心js
        , {
            src: '@/assets/js/love',
            //是否需要服务端熏染
            ssr: false,
        }, {
            //mavon-editor 富文本编辑器
            src: '@/plugins/mavon-editor',
            //是否需要服务端熏染
            ssr: true,
        },
        //wangeditor
        { src: '@/plugins/wangeditor', ssr: false },
        {
            src: '@/plugins/vue-particles',
            //是否需要服务端熏染
            ssr: false,
        }, {
            //兼容ie
            src: '@/plugins/hls',
            //是否需要服务端熏染
            ssr: false,
        },
        {
            //移动端手指滑动监控插件
            src: '@/plugins/vue-touch',
            //是否需要服务端熏染
            ssr: false,
        }, {
            //滑动验证码
            src: '@/plugins/vue-monoplasty-slide-verify',
            //是否需要服务端熏染
            ssr: true,
        }, {
            //图片预览插件
            src: '@/plugins/v-viewer',
            //是否需要服务端熏染
            ssr: true,
        }, {
            //axios
            src: "~/plugins/axios",
            ssr: true
        }, {
            //分享插件
            src: "~/plugins/vue-social-share",
            ssr: false
        },
        //配置路由守卫
        { src: '@/plugins/permission', ssr: true },
        { src: '@/plugins/vue-video-player', ssr: false },
        { src: '@/plugins/vue-aplayer', ssr: false },
    ],
    //设置接口地址环境变量
    env: {
        baseUrl: process.env.BASE_URL
    },
    // Auto import components: https://go.nuxtjs.dev/config-components
    components: true,

    // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
    buildModules: [],

    // Modules: https://go.nuxtjs.dev/config-modules
    modules: [
        //cookie
        'cookie-universal-nuxt',
        //axios
        '@nuxtjs/axios',
    ],
    //路由进度条配置
    loading: {
        color: '#19be6b',
    },
    // Build Configuration: https://go.nuxtjs.dev/config-build
    build: {
        //使用jquery
        plugins: [
            new webpack.ProvidePlugin({ jQuery: "jquery", $: "jquery" })
        ],
    },
}