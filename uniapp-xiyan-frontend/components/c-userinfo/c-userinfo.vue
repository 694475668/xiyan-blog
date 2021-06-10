<template>
	<view class="container">
		<view class="ui-all">
			<view class="avatar" @tap="avatarChoose">
				<view class="imgAvatar">
					<view class="iavatar" :style="'background: url('+user.photo+') no-repeat center/cover #eeeeee;'">
					</view>
				</view>
				<text>修改头像</text>
			</view>
			<view class="ui-list">
				<text>昵称</text>
				<input type="text" :value="user.name" @input="bindName" placeholder-class="place" />
			</view>
			<view class="ui-list">
				<text>金币</text>
				<input disabled :value="user.point" placeholder-class="place" />
			</view>
			<view class="ui-list">
				<text>状态</text>
				<input disabled :value="user.state" placeholder-class="place" />
			</view>
			<view class="ui-list">
				<text>创建时间</text>
				<input disabled placeholder-class="place" :value="user.createTime"></input>
			</view>
			<button class="save" @tap="savaInfo">保 存 修 改</button>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {},
				userParam: {
					id: "",
					name: "",
					photo: ""
				}
			}

		},
		created() {
			let user = uni.getStorageSync("info");
			this.userParam.id = user.id;
			this.userParam.name = user.name;
			this.userParam.photo = user.photo
			if (user.state == 0) {
				user.state = "正常";
			} else if (user.state == 0) {
				user.state = "锁定";
			}
			user.createTime = this.formatDate(user.createTime);
			this.user = user
		},
		methods: {
			formatDate(value) {
				let date = new Date(value);
				let y = date.getFullYear();
				let MM = date.getMonth() + 1;
				MM = MM < 10 ? "0" + MM : MM;
				let d = date.getDate();
				d = d < 10 ? "0" + d : d;
				let h = date.getHours();
				h = h < 10 ? "0" + h : h;
				let m = date.getMinutes();
				m = m < 10 ? "0" + m : m;
				let s = date.getSeconds();
				s = s < 10 ? "0" + s : s;
				return y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;
			},
			bindName(e) {
				this.userParam.name = e.detail.value;
			},
			avatarChoose() {
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					sourceType: ['album', 'camera'],
					success: res => {
						// tempFilePath可以作为img标签的src属性显示图片
						this.imgUpload(res.tempFilePaths);
					}
				});
			},
			async savaInfo() {
				if (!this.userParam.name) {
					uni.showToast({
						title: '请填写昵称',
						icon: 'none',
						duration: 1500
					});
					return;
				}
				const res = await this.$request({
					url: this.$baseURL + "/auth/user/update",
					method: "PUT",
					data: this.userParam
				})
				if (res.success) {
					uni.showToast({
						title: "信息已更新",
						icon: "none"
					})
					const res = await this.$request({
						url: this.$baseURL + "/auth/user/by",
						method: "POST",
						data: this.userParam
					})
					uni.setStorageSync("info", res);
					setTimeout(() => {
						uni.navigateTo({
							url: "/pages/user/personal"
						})
					}, 1000)
				}
			},
			//上传图片
			imgUpload(file) {
				let that = this;
				uni.uploadFile({
					header: {
						"x-access-token": uni.getStorageSync('token'),
						"x-aes": "0"
					},
					url: this.$baseURL + '/oss/image/upload', //需传后台图片上传接口
					filePath: file[0],
					fileType: "image",
					name: 'file',
					formData: {
						type: 'user_headimg'
					},
					success: res => {
						let response = JSON.parse(res.data);
						if (response.success) {
							this.user.photo = response.downloadUrl;
							this.userParam.photo = response.downloadUrl;
						}
					},
					fail: function(error) {

					}
				});
			},

		},
	}
</script>

<style lang="less">
	.container {
		display: block;
	}

	.ui-all {
		padding: 20rpx 40rpx;

		.avatar {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #f2f2f2;
			position: relative;

			.imgAvatar {
				width: 140rpx;
				height: 140rpx;
				border-radius: 50%;
				display: inline-block;
				vertical-align: middle;
				overflow: hidden;

				.iavatar {
					width: 100%;
					height: 100%;
					display: block;
				}
			}

			text {
				display: inline-block;
				vertical-align: middle;
				color: #8e8e93;
				font-size: 28rpx;
				margin-left: 40rpx;
			}

			&:after {
				content: ' ';
				width: 20rpx;
				height: 20rpx;
				border-top: solid 1px #030303;
				border-right: solid 1px #030303;
				transform: rotate(45deg);
				-ms-transform: rotate(45deg);
				/* IE 9 */
				-moz-transform: rotate(45deg);
				/* Firefox */
				-webkit-transform: rotate(45deg);
				/* Safari 和 Chrome */
				-o-transform: rotate(45deg);
				position: absolute;
				top: 85rpx;
				right: 0;
			}
		}

		.ui-list {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #f2f2f2;
			position: relative;

			text {
				color: #4a4a4a;
				font-size: 28rpx;
				display: inline-block;
				vertical-align: middle;
				min-width: 150rpx;
			}

			input {
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
			}

			button {
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				background: none;
				margin: 0;
				padding: 0;

				&::after {
					display: none;
				}
			}

			picker {
				width: 90%;
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				position: absolute;
				top: 30rpx;
				left: 150rpx;
			}

			textarea {
				color: #030303;
				font-size: 30rpx;
				vertical-align: middle;
				height: 150rpx;
				width: 100%;
				margin-top: 50rpx;
			}

			.place {
				color: #999999;
				font-size: 28rpx;
			}
		}

		.right:after {
			content: ' ';
			width: 20rpx;
			height: 20rpx;
			border-top: solid 1px #030303;
			border-right: solid 1px #030303;
			transform: rotate(45deg);
			-ms-transform: rotate(45deg);
			/* IE 9 */
			-moz-transform: rotate(45deg);
			/* Firefox */
			-webkit-transform: rotate(45deg);
			/* Safari 和 Chrome */
			-o-transform: rotate(45deg);
			position: absolute;
			top: 40rpx;
			right: 0;
		}

		.save {
			background-image: linear-gradient(to right, rgb(17, 153, 142), rgb(56, 239, 125));
			border: none;
			color: #ffffff;
			margin-top: 40rpx;
			font-size: 28rpx;
		}
	}
</style>
