import Vue from 'vue'
//由于iview的走马灯不好设置移动端的滑动，所以我这里走马灯用的是elementui
import { Carousel, CarouselItem, Button, Input, Popover } from 'element-ui';

//elementui的走马灯挂载组件
Vue.use(Carousel)
Vue.use(CarouselItem)
Vue.use(Button)
Vue.use(Input)
Vue.use(Popover)