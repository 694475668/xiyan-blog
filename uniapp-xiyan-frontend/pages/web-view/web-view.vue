<template>
	<view class="box">
		<nav-bar :title="'支付'" :back="true" :leftText="'返回'"></nav-bar>
		<nav-bottom :current="4"></nav-bottom>
		<u-back-top :scroll-top="scrollTop" icon="arrow-up" :icon-style="{'fontSize':'32rpx','color':'#fff'}"
			:custom-style="{'background':'#29CD84'}" tips="顶部"></u-back-top>
		<!-- 用于专门放置访问外部网站 -->
		<web-view :src="url">
		</web-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				url: "", //要打开的外部链接
				scrollTop: 0
			}
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
		},
		onLoad: function(option) {
			//escape  unescape encodeURI decodeURI encodeURIComponent decodeURIComponent
			//unescape  decodeURIComponent  这二个函数才可以小程序url特殊字符解析
			this.url = unescape(option.url + "?payId=" + option.payId + "&type=" + option
				.type + "&sign=" + option
				.sign +
				"&param=" + (option.param == null ? '' : option.param) + "&price=" +
				option.price +
				"&isHtml=" + option
				.isHtml)
		},
		methods: {

		}
	}
</script>

<style>

</style>
