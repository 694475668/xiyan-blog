<template>
	<view>
		<!-- 支付宝会出现二个导航栏所以这里直接去掉 -->
		<!-- #ifndef MP-ALIPAY-->
		<cmd-nav-bar :left-text="leftText" @leftText="leftTextClick" :back="back" :title="title" font-color="#fff"
			background-color="linear-gradient(to right, rgb(17, 153, 142), rgb(56, 239, 125))">
		</cmd-nav-bar>
		<!-- #endif -->
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		props: {
			title: String,
			back: {
				type: Boolean,
				default: false
			},
			leftText: {
				type: String,
				default: ""
			}
		},
		methods: {
			leftTextClick() {
				uni.navigateBack();
			}
		}
	}
</script>

<style lang="scss" scoped>
</style>
