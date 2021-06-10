import Vue from 'vue'
import store from '../store'
/**
 * @export 自定义权限指令，通过v-permit="'/system/role/grant'"进行按钮鉴权
 */
export function directive() {
    Vue.directive('permit', {
        bind(el, binding) {
            store.state.permission.permission.findIndex(v => v.attributes.url === binding.value) !== -1 ? {} : el.style.display = 'none'
        }
    })
}