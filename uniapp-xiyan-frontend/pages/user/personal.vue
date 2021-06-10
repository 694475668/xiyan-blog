<template>
	<view class="box">
		<nav-bar :title="'个人资料'" :back="true" :leftText="'返回'"></nav-bar>
		<nav-bottom :current="4"></nav-bottom>
		<c-userinfo></c-userinfo>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {

		}
	}
</script>

<style>

</style>
