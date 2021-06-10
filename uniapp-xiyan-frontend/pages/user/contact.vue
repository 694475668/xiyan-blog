<template>
	<view class="box">
		<nav-bar :title="'关于我们'" :back="true" :leftText="'返回'"></nav-bar>
		<nav-bottom :current="4"></nav-bottom>
		<image class="img" src="../../static/images/user/gywmban.jpg"></image>
		<view class="info">
			<u-link color="#303133" font-size="30rpx" href="http://wpa.qq.com/msgrd?v=3&uin=694475668&site=qq&menu=yes">
				联系QQ：694475668</u-link>
			<u-line color="#BBBBB8" margin="15rpx" />
			<u-link class="qq" color="#303133" font-size="30rpx"
				href="https://qm.qq.com/cgi-bin/qm/qr?k=g58e_OK0YbSw91Y_2FxJtqyAAQSVx19Q&jump_from=webapi">
				QQ群：557423713</u-link>
			<u-line color="#BBBBB8" margin="15rpx" />
			<view>目前就职于公司地址</view>
		</view>
		<map class="map" enable-3D show-compass enable-zoom enable-scroll enable-rotate enable-overlooking
			enable-satellite enable-traffic enable-poi enable-building show-location enable-indoorMap
			:longitude="longitude" :scale="scale" :latitude="latitude" :markers="markers"></map>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				longitude: 113.991116,
				latitude: 22.559121,
				scale: 16,
				markers: [{
					longitude: 113.991116,
					latitude: 22.559121,
					iconPath: '../../static/images/user/ByteDance.jpg',
					height: 40,
					width: 110,
					title: "北京字节跳动科技有限公司深圳分公司"
				}]
			}
		},
		methods: {}
	}
</script>

<style lang="scss">
	.box {
		.img {
			width: 750rpx;
			height: 320rpx;
		}

		.info {
			padding: 10rpx 20rpx;
			font-size: 30rpx;

			.qq {
				line-height: 60rpx;
				border-bottom: 1px solid #eee;
			}
		}

		.map {
			width: 750rpx;
			height: 750rpx;
		}
	}
</style>
