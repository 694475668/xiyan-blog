import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/
export const constantRouterMap = [{
        path: '/login',
        component: () =>
            import ('@/views/login/index'),
        hidden: true
    },
    {
        path: '/404',
        component: () =>
            import ('@/views/404'),
        hidden: true
    },
    {
        path: '/code/publishProject',
        name: 'publishProject',
        hidden: true,
        component: () =>
            import ('@/views/code/publishProject.vue'),
    },
    {
        path: '/article/postArticle',
        name: 'postArticle',
        hidden: true,
        component: () =>
            import ('@/views/blog/postArticle.vue'),
    },
    {
        path: '/',
        component: Layout,
        redirect: '/home',
        hidden: true,
        children: [{
            path: 'home',
            name: 'home',
            component: () =>
                import ('@/views/dashboard/index'),
            meta: {
                title: '主页',
            }
        }]
    },
]

export const asyncRouterMap = [
    //根据后台查询实现动态路由
    //系统管理
    {
        path: '/systems',
        component: Layout,
        redirect: '/systems/resource',
        alwaysShow: true, // will always show the root menu
        name: 'systems',
        meta: {
            title: '系统管理',
            icon: 'system', // you can set roles in root nav
        },
        children: [{
                path: 'resource',
                component: () =>
                    import ('@/views/system/resource.vue'),
                name: 'resource',
                meta: {
                    title: '资源管理',
                    icon: 'resource',
                }
            },
            {
                path: 'role',
                component: () =>
                    import ('@/views/system/role.vue'),
                name: 'role',
                meta: {
                    title: '角色管理',
                    icon: 'role',
                }
            },
            {
                path: 'systemuser',
                component: () =>
                    import ('@/views/system/user.vue'),
                name: 'systemuser',
                meta: {
                    title: '系统用户管理',
                    icon: 'sysuser',
                }
            },
        ]
    },
    //参数管理
    {
        path: '/parameter',
        component: Layout,
        redirect: '/parameter/dictionaries',
        alwaysShow: true, // will always show the root menu
        name: 'Paramete',
        meta: {
            title: '参数管理',
            icon: 'guide', // you can set roles in root nav
        },
        children: [{
            path: 'dictionaries',
            component: () =>
                import ('@/views/parameter/dictionaries.vue'),
            name: 'Dictionarie',
            meta: {
                title: '数据字典管理',
                icon: 'dictionaries',
            }
        }]
    },
    //黑名单管理
    {
        path: '/black',
        component: Layout,
        redirect: '/black/list',
        alwaysShow: true, // will always show the root menu
        name: 'black',
        meta: {
            title: '名单管理',
            icon: 'black1', // you can set roles in root nav
        },
        children: [{
            path: 'list',
            component: () =>
                import ('@/views/list/index.vue'),
            name: 'list',
            meta: {
                title: '黑名单管理',
                icon: 'black',
            }
        }]
    },
    //博客
    {
        path: '/article',
        component: Layout,
        redirect: '/article/list',
        alwaysShow: true, // will always show the root menu
        name: 'article',
        meta: {
            title: '博客管理',
            icon: 'article', // you can set roles in root nav
        },
        children: [{
            path: 'list',
            component: () =>
                import ('@/views/blog/index.vue'),
            name: 'list',
            meta: {
                title: '博客',
                icon: 'blog',
            }
        }]
    },
    //源码
    {
        path: '/code',
        component: Layout,
        redirect: '/code/list',
        alwaysShow: true, // will always show the root menu
        name: 'code',
        meta: {
            title: '源码管理',
            icon: 'code', // you can set roles in root nav
        },
        children: [{
            path: 'list',
            component: () =>
                import ('@/views/code/index.vue'),
            name: 'list',
            meta: {
                title: '源码',
                icon: 'yuanma',
            }
        }]
    },
]

const createRouter = () => new Router({
    mode: 'history', // require service support
    scrollBehavior: () => ({
        y: 0
    }),
    routes: constantRouterMap
})
const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
// export function resetRouter() {
// 	const newRouter = createRouter()
// 	router.matcher = newRouter.matcher // reset router
// }

export default router