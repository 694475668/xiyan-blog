<template>
	<view class="box">
		<nav-bar :title="'我的'"></nav-bar>
		<nav-bottom :current="4"></nav-bottom>
		<!-- 头部 -->
		<view class="user-wrap">
			<div class="clouds_one"></div>
			<div class="clouds_two"></div>
			<div class="clouds_three"></div>
			<view class="setting iconfont icon31shezhi"></view>
			<view class="info">
				<image class="avatar" mode="aspectFill" :src="userInfo.photo"></image>
				<view class="nickname">{{ userInfo.name }}</view>
			</view>
		</view>

		<!-- 滑动导航 -->
		<view style="border-radius: 20rpx; overflow: hidden; margin: 0 20rpx;">
			<com-nav :list="list" :col="4"></com-nav>
		</view>

		<!-- 用户功能 -->
		<view class="com-item">
			<view class="com-wrap">
				<view class="cell" v-for="(item, index) in userList" :key="index" @click="userBtn(index)">
					<view class="cell-left">
						<image class="cell-icon" :src="item.icon" mode="aspectFill"></image>
						<view class="cell-text">{{ item.title }}</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 用户服务 -->
		<view class="com-item">
			<view class="com-wrap">
				<view class="cell" v-for="(item, index) in serverList" :key="index" @click="serverBtn(index)">
					<view class="cell-left">
						<image class="cell-icon" :src="item.icon" mode="aspectFill"></image>
						<view class="cell-text">{{ item.title }}</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import comNav from './components/com-nav.vue'
	export default {
		components: {
			comNav
		},
		data() {
			return {
				userInfo: {
					photo: '/static/images/user/logo.jpg',
					name: '夕颜社区'
				},
				currentIndex: 0,
				list: [{
						icon: '/static/images/user/class-01.png',
						text: '我的博客'
					},
					{
						icon: '/static/images/user/class-02.png',
						text: '我的源码'
					},
					{
						icon: '/static/images/user/class-03.png',
						text: '我的下载'
					},
					{
						icon: '/static/images/user/class-04.png',
						text: '金币明细'
					}
				],
				userList: [{
						title: '我的资料',
						icon: '/static/images/user/my.png',
						path: 'address-list'
					},
					{
						title: '在线充值',
						icon: '/static/images/user/buy.png',
						path: 'collect-list'
					},
					{
						title: '我的私信',
						icon: '/static/images/user/letter.png',
						path: 'browse-list'
					}
				],
				serverList: [{
						title: '扫一扫',
						icon: '/static/images/user/qr.png',
					},
					{
						title: '关于我们',
						icon: '/static/images/user/contact.png',
						path: 'help'
					},
					{
						title: '退出登录',
						icon: '/static/images/user/logout.png',
						text: '1.0.0'
					}
				]
			};
		},
		onShow() {
			let info = uni.getStorageSync("info")
			if (info) {
				this.userInfo = info;
			}
		},
		methods: {
			serverBtn(index) {
				//判断是哪个按钮进行点击
				if (index == 0) {
					uni.scanCode({
						success: (res) => {
							console.log(res);
						}
					})
				} else if (index == 1) {
					uni.navigateTo({
						url: "/pages/user/contact"
					})
				} else if (index == 2) {
					if (uni.getStorageSync("token") != "") {
						uni.removeStorageSync("info");
						uni.removeStorageSync("token");
						uni.showToast({
							title: "已退出",
							icon: "none"
						});
						setTimeout(() => {
							uni.reLaunch({
								url: "/pages/user/user"
							})
						}, 500)
					} else {
						uni.showToast({
							title: "您还未登录哦",
							icon: "none"
						});
					}
				}
			},
			userBtn(index) {
				if (index == 0) {
					uni.navigateTo({
						url: "/pages/user/personal"
					})
				} else if (index == 1) {
					uni.navigateTo({
						url: "/pages/user/pay"
					})
				} else if (index == 2) {
					uni.showToast({
						title: "暂未开放，敬请期待",
						icon: "none"
					})
				}
			}
		}
	};
</script>

<style lang="scss">
	@import '~@/style/user/hovertreeplane.css';

	page {
		background: #f2f2f2;
	}

	.btn-hover {
		background: #f2f2f2 !important;
	}

	.box {
		.user-wrap {
			display: flex;
			justify-content: center;
			align-items: center;
			height: 50vw;
			padding: 30rpx;
			z-index: 9;
			border-radius: 0 0 3% 3%;
			background: #007fd5;

			.setting {
				color: #fff;
				position: absolute;
				top: 60rpx;
				left: 60rpx;
				font-size: 50rpx;
			}

			.info {
				position: absolute;
				text-align: center;

				.avatar {
					width: 150rpx;
					height: 150rpx;
					border-radius: 50%;
				}

				.nickname {
					color: #fff;
					font-size: 28rpx;
				}
			}
		}

		.order-status {
			padding: 0 20rpx;
			margin-top: -10vw;

			.status-wrap {
				border-radius: 25rpx;
				overflow: hidden;

				.status-list {
					display: flex;
					justify-content: space-evenly;
					align-items: center;
					background: #fff;
					padding-top: 30rpx;
					padding-bottom: 30rpx;

					.status-item {
						flex: 1;
						display: flex;
						flex-direction: column;
						justify-content: center;
						align-items: center;

						.item-icon {
							line-height: 1;
							font-size: 65rpx;
							color: #bbb;
						}

						.item-text {
							font-size: 28rpx;
							color: #666;
							margin-top: 5rpx;
						}
					}
				}
			}
		}

		.com-item {
			padding-left: 20rpx;
			padding-right: 20rpx;
			margin-top: 20rpx;

			.com-wrap {
				border-radius: 25rpx;
				overflow: hidden;
			}
		}

		.cell {
			height: 80rpx;
			padding-left: 20rpx;
			padding-right: 20rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			background: #fff;
			border-bottom: 1px solid #f8f8f8;

			&:active {
				background: #f2f2f2;
			}

			&:last-child {
				border-bottom: none !important;
			}

			.cell-left {
				display: flex;
				align-items: center;
				z-index: 9999;

				.cell-icon {
					width: 50rpx;
					height: 50rpx;
				}

				.cell-text {
					color: #666;
					font-size: 28rpx;
					margin-left: 20rpx;
				}
			}

			.iconfont {
				font-size: 40rpx;
				color: #999;
			}
		}
	}
</style>
