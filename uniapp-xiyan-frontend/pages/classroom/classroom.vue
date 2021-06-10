<template>
	<view class="box">
		<nav-bar :title="'课堂'"></nav-bar>
		<nav-bottom :current="3"></nav-bottom>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		onLoad(){
			uni.showToast({
				title:"暂未开放，敬请期待",
				icon:"none"
			})
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
