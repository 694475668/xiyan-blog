<template>
	<view class="box">
		<nav-bar :title="'密码找回'" :back="true" :leftText="'返回'"></nav-bar>
		<nav-bottom :current="4"></nav-bottom>
		<u-navbar :is-back="false" :background="{}" :border-bottom="false" title="注册">
		</u-navbar>
		<image src="@/static/images/user/register.png" mode='aspectFit' class="nx-logo"></image>
		<view class="nx-title">夕颜社区</view>
		<view class="nx-form">
			<input class="nx-input" placeholder="请输入邮箱" v-model="recover.username" />
			<input class="nx-input" password placeholder="请输入新密码" v-model="recover.password" />
			<view class="nx-input" style="display: inline-flex;justify-content: center;align-items: center;">
				<input placeholder="请输入验证码" v-model="recover.code" />
				<!-- 验证码组件 -->
				<u-verification-code ref="uCode" @change="codeChange"></u-verification-code>
				<u-button type="success" size="mini" shape="circle" :ripple="true" @tap="getCode">{{tips}}</u-button>
			</view>
			<button class="nx-btn" @click="recoverBtn">找回密码</button>
			<navigator url="./login" open-type='navigateBack' hover-class="none" class="nx-label">已有账号，点此去登录
			</navigator>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				recover: {
					username: '', //邮箱
					password: '', //密码
					code: "" //验证码
				},
				tips: '',
			};
		},
		methods: {
			codeChange(text) {
				this.tips = text;
			},
			async getCode() {
				if (this.$refs.uCode.canGetCode) {
					// 模拟向后端请求验证码
					uni.showLoading({
						title: '正在获取验证码'
					})
					//请求后端发送验证码
					const res = await this.$request({
						url:this.$baseURL+ "/auth/user/send",
						method: "POST",
						data: this.recover
					})
					if (res.success) {
						uni.hideLoading();
						uni.showToast({
							title: "验证码已发送"
						})
						// 通知验证码组件内部开始倒计时
						this.$refs.uCode.start();
					}
				} else {
					uni.showToast({
						title: "一分钟后可重新发送"
					})
				}
			},
			async recoverBtn() {
				if (!this.recover.username) {
					uni.showToast({
						title: '请输入邮箱',
						icon: 'none'
					});
					return;
				}
				if (!this.recover.password) {
					uni.showToast({
						title: '请输入新密码',
						icon: 'none'
					});
					return;
				}
				if (!this.recover.code) {
					uni.showToast({
						title: '请输入验证码',
						icon: 'none'
					});
					return;
				}
				const res = await this.$request({
					url: this.$baseURL+"/auth/user/retrieve",
					method: "PUT",
					data: this.recover
				})
				if (res.success) {
					uni.showToast({
						title: '密码修改成功！',
						icon: 'none'
					});
					setTimeout(() => {
						uni.navigateTo({
							url: "./login"
						});
					}, 1000)
				}
			}
		}
	}
</script>

<style scoped>
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

	.input-placeholder,
	.nx-input {
		color: #94afce;
	}

	.nx-label {
		padding: 60upx 0;
		text-align: center;
		font-size: 30upx;
		color: #a7b6d0;
	}

	.nx-btn {
		background-image: linear-gradient(to right, rgb(17, 153, 142), rgb(56, 239, 125));
		color: #fff;
		border: 0;
		border-radius: 100upx;
		font-size: 36upx;
		margin-top: 60upx;
	}

	.nx-btn:after {
		border: 0;
	}

	/*按钮点击效果*/
	.nx-btn.button-hover {
		transform: translate(1upx, 1upx);
	}
</style>
