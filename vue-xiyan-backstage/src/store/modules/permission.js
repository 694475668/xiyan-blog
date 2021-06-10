import { asyncRouterMap, constantRouterMap } from '@/router/index';

//判断是否有权限访问该菜单
function hasPermission(menus, route) {
    if (route.meta.title) {
        let currMenu = getMenu(route.meta.title, menus);
        if (currMenu != null) {
            //自定义属性，用来存取url方便自定义组件v-permit来控制权限按钮
            route.permit = currMenu.attributes.url
                //设置菜单的标题、图标和可见性
            if (currMenu.label != null && currMenu.label !== '') {
                route.meta.title = currMenu.label;
            }
            return true;
        } else {
            route.sort = 0;
            if (route.hidden !== undefined && route.hidden === true) {
                return true;
            } else {
                return false;
            }
        }
    } else {
        return true
    }
}

//根据路由名称获取菜单
function getMenu(title, menus) {
    for (let i = 0; i < menus.length; i++) {
        let menu = menus[i];
        if (title === menu.label) {
            return menu;
        }
    }
    return null;
}

//对菜单进行排序
function sortRouters(accessedRouters) {
    for (let i = 0; i < accessedRouters.length; i++) {
        let router = accessedRouters[i];
        if (router.children && router.children.length > 0) {
            router.children.sort(compare("sort"));
        }
    }
    accessedRouters.sort(compare("sort"));
}

//降序比较函数
function compare(p) {
    return function(m, n) {
        let a = m[p];
        let b = n[p];
        return b - a;
    }
}

const state = {
    routers: [],
    addRouters: [],
    permission: ''
}
const mutations = {
    SET_ROUTERS: (state, routers) => {
        state.addRouters = routers;
        state.routers = constantRouterMap.concat(routers);
    },
    //用来判断前端按钮权限
    SET_PERMISSION: (state, permission) => {
        state.permission = permission
    }
}
const actions = {
    GenerateRoutes({ commit }, menus) {
        return new Promise(resolve => {

            const arr = []
            let permission = []
            asyncRouterMap.map((v, i) => {
                const idx = menus.findIndex(val => val.label === v.meta.title)
                if (idx === -1) {
                    arr.push(i)
                }
                let arr2 = []
                menus.map(val => {
                    v.children.map(item => {
                        val.children.map(key => {
                            if (key.label == item.meta.title) {
                                item.meta.attributes = key.children
                                permission = permission.concat(key.children)
                                arr2.push(item)
                            }
                        })
                    })

                })
                v.children = arr2
            })
            for (let i = arr.length - 1; i >= 0; i--) {
                asyncRouterMap.splice(arr[i], 1)
            }
            commit('SET_PERMISSION', permission)
            commit('SET_ROUTERS', asyncRouterMap);
            resolve(asyncRouterMap);
        })
    }
}
export default {
    namespaced: true,
    state,
    mutations,
    actions
}