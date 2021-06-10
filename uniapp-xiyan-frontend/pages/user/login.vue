<template>
	<view class="box">
		<nav-bar :title="'登录'" :back="true" :leftText="'返回'"></nav-bar>
		<nav-bottom :current="4"></nav-bottom>
		<u-navbar :is-back="false" :background="{}" :border-bottom="false" title="登录">
		</u-navbar>
		<image src="@/static/images/user/login.png" mode='aspectFit' class="nx-logo"></image>
		<view class="nx-title">夕颜社区</view>
		<view class="nx-form">
			<input class="nx-input" placeholder="请输入邮箱" v-model="login.username" />
			<input class="nx-input" password placeholder="请输入密码" v-model="login.password" />
			<navigator url="./recover" hover-class="none" class="nx-label">忘记密码？</navigator>
			<button class="nx-btn" @tap="loginFunction">立即登录</button>
			<navigator url="./register" hover-class="none" class="nx-label">还没有账号？点此注册</navigator>
		</view>
	</view>
</template>

<script>
	export default {
		onShow() {

		},
		data() {
			return {
				login: {
					username: '', //邮箱
					password: '' //密码
				},
			};
		},
		methods: {
			//当前登录按钮操作
			async loginFunction() {
				if (!this.login.username) {
					uni.showToast({
						title: '请输入邮箱',
						icon: 'none'
					});
					return;
				}
				if (!this.login.password) {
					uni.showToast({
						title: '请输入密码',
						icon: 'none'
					});
					return;
				}
				const res = await this.$request({
					url: this.$baseURL + "/auth/user/login",
					method: "POST",
					data: this.login
				})
				if (res.success) {
					uni.showToast({
						title: '登录成功！',
						icon: 'none'
					});
					uni.setStorageSync('token', res.token);
					uni.setStorageSync('info', res.userVO);
					setTimeout(() => {
						uni.navigateBack();
					}, 1000)
				}
			}
		}
	}
</script>

<style scoped lang="scss">
	.box {
		padding: 0 100upx;
		position: relative;
		background: #fff;
		height: 100vh;
	}

	.nx-logo {
		width: 100%;
		width: 100%;
		height: 310upx;
	}

	.nx-title {
		position: absolute;
		top: 0;
		line-height: 540upx;
		font-size: 68upx;
		color: #fff;
		text-align: center;
		width: 100%;
		margin-left: -100upx;
	}

	.nx-form {
		margin-top: 150upx;
	}

	.nx-input {
		background: #e2f5fc;
		margin-top: 30upx;
		border-radius: 100upx;
		padding: 20upx 40upx;
		font-size: 36upx;
		box-sizing: content-box;
	}


	.nx-label {
		padding: 60upx 0;
		text-align: center;
		font-size: 30upx;
		color: #a7b6d0;
	}

	.nx-btn {
		color: #fff;
		border: 0;
		border-radius: 100upx;
		font-size: 36upx;
		background-image: linear-gradient(to right, rgb(17, 153, 142), rgb(56, 239, 125));
	}

	.nx-btn:after {
		border: 0;
	}

	/*按钮点击效果*/
	.nx-btn.button-hover {
		transform: translate(1upx, 1upx);
	}
</style>
