<template>
	<view class="box">
		<nav-bar :title="'充值'" :back="true" :leftText="'返回'"></nav-bar>
		<nav-bottom :current="4"></nav-bottom>
		<view class="block">
			<view class="title">
				我的账户：{{userInfo.username}}
			</view>
			<view class="content">
				<view class="my">
					我的金币余额：{{userInfo.point}}
				</view>
			</view>
		</view>
		<view class="block">
			<view class="title">
				充值金币
			</view>
			<view class="content">
				<view class="amount">
					<view class="list">
						<u-row gutter="16">
							<u-col text-align="center" span="5.2" class="col-sty" v-for="(amount,index) in amountList"
								:key="index" @click="select(amount.price)"
								:class="{'on':amount.price == payParam.price}">
								<view>{{amount.text}}</view>
							</u-col>
						</u-row>
					</view>
				</view>
			</view>
		</view>
		<view class="block">
			<view class="title">
				选择支付方式
			</view>
			<view class="content">
				<view class="pay-list">
					<view class="row" @click="payParam.type=2">
						<view class="left">
							<image src="/static/images/user/alipay.png"></image>
						</view>
						<view class="center">
							支付宝支付
						</view>
						<view class="right">
							<radio :checked="payParam.type==2" color="#2ec867" />
						</view>
					</view>
					<view class="row" @click="payParam.type=1">
						<view class="left">
							<image src="/static/images/user/wxpay.png"></image>
						</view>
						<view class="center">
							微信支付
						</view>
						<view class="right">
							<radio :checked="payParam.type==1" color="#2ec867" />
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="pay">
			<view class="btn" :disabled="disabled" @click="doDeposit">立即充值</view>
			<view class="tis">
				点击立即充值，即代表您同意<view class="terms">
					《条款协议》
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				disabled: false,
				amountList: [{
					text: "199￥/VIP会员",
					price: 199
				}, {
					text: "10￥/100金币",
					price: 10
				}, {
					text: "30￥/100金币",
					price: 30
				}, {
					text: "50￥/1000金币",
					price: 50
				}, {
					text: "100￥/1000金币",
					price: 100
				}],
				payParam: {
					price: 199,
					type: 2,
				},
				userInfo: {},
			};
		},
		methods: {
			select(amount) {
				this.payParam.price = amount;
			},
			async doDeposit() {
				const res = await this.$request({
					url: this.$baseURL + "/web/order/create",
					method: "POST",
					data: this.payParam
				});
				if (res.success) {
					var url = res.url + "/createOrder&payId=" + res.payId + "&type=" + res.type + "&sign=" + res.sign +
						"&param=" + (res.param == null ? '' : res.param) + "&price=" + res.price + "&isHtml=" + res
						.isHtml
					uni.redirectTo({
						url: "/pages/web-view/web-view?url=" + url
					})
				}
			},
		},
		onLoad() {
			let info = uni.getStorageSync("info")
			this.userInfo = info;
		},
	}
</script>

<style lang="scss" scoped>
	.box {
		background: #fff;

		.block {
			padding: 20upx 3%;

			.title {
				width: 100%;
				font-size: 34upx;
			}

			.content {
				.my {
					width: 100%;
					height: 100upx;
					display: flex;
					align-items: center;
					font-size: 30upx;
					border-bottom: solid 1upx #eee;
				}

				.amount {
					width: 100%;

					.list {
						display: flex;
						justify-content: space-between;
						padding: 20upx 0;

						.col-sty {
							width: 45%;
							height: 100upx;
							display: inline-flex;
							justify-content: center !important;
							align-items: center !important;
							border-radius: 10upx;
							box-shadow: 0upx 5upx 20upx rgba(0, 0, 0, 0.05);
							font-size: 36upx;
							background-color: #f1f1f1;
							margin: 0px 30rpx 30rpx 0px;
							color: 333;

							&.on {
								width: 45%;
								height: 100upx;
								border-radius: 10upx;
								box-shadow: 0upx 5upx 20upx rgba(0, 0, 0, 0.05);
								font-size: 36upx;
								background-color: #f1f1f1;
								color: 333;
								background-image: linear-gradient(to right, rgb(17, 153, 142), rgb(56, 239, 125));
								color: #fff;
							}
						}
					}
				}

				.pay-list {
					width: 100%;
					border-bottom: solid 1upx #eee;

					.row {
						width: 100%;
						height: 120upx;
						display: flex;
						align-items: center;

						.left {
							width: 100upx;
							flex-shrink: 0;
							display: flex;
							align-items: center;

							image {
								width: 80upx;
								height: 80upx;
							}
						}

						.center {
							width: 100%;
							font-size: 30upx;
						}

						.right {
							width: 100upx;
							flex-shrink: 0;
							display: flex;
							justify-content: flex-end;
						}
					}
				}
			}
		}

		.pay {
			margin: 20upx 0rpx 80rpx 0rpx;
			width: 100%;
			display: flex;
			justify-content: center;
			flex-wrap: wrap;

			.btn {
				width: 70%;
				height: 80upx;
				border-radius: 80upx;
				display: flex;
				justify-content: center;
				align-items: center;
				color: #fff;
				background-image: linear-gradient(to right, rgb(17, 153, 142), rgb(56, 239, 125));
				box-shadow: 0upx 5upx 10upx rgba(0, 0, 0, 0.2);
			}

			.tis {
				margin-top: 10upx;
				width: 100%;
				font-size: 24upx;
				display: flex;
				justify-content: center;
				align-items: baseline;
				color: #999;

				.terms {
					color: #5a9ef7;
				}
			}
		}
	}
</style>
