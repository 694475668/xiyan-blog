<template>
	<view class="box">
		<nav-bar :title="type=='blog'?'博客详情':'毕设详情'" :back="true" :leftText="'返回'"></nav-bar>
		<nav-bottom :current="type=='blog'?0:type=='code'?1:9"></nav-bottom>
		<u-back-top :scroll-top="scrollTop" icon="arrow-up" :icon-style="{'fontSize':'32rpx','color':'#fff'}"
			:custom-style="{'background':'#29CD84'}" tips="顶部"></u-back-top>
		<view>
			<uni-title type="h2" :title="list.title" align="center"></uni-title>
		</view>
		<view style="margin-bottom: 40rpx;">
			<view class="cu-capsule radius">
				<view class='cu-tag bg-red sm'>
					<text class='cuIcon-myfill'></text>
				</view>
				<view class="cu-tag line-red sm">
					<text>{{list.name}}</text>
				</view>
			</view>
			<view class="cu-capsule radius">
				<view class='cu-tag bg-orange sm'>
					<text class='cuIcon-attention'></text>
				</view>
				<view class="cu-tag line-orange sm">
					<text>{{list.readCount}}</text>
				</view>
			</view>
			<view class="cu-capsule radius">
				<view class='cu-tag bg-green sm'>
					<text class='cuIcon-appreciatefill'></text>
				</view>
				<view class="cu-tag line-green sm">
					<text>{{list.starCount}}</text>
				</view>
			</view>
			<view class="cu-capsule radius">
				<view class='cu-tag bg-blue sm'>
					<text class='cuIcon-comment'></text>
				</view>
				<view class="cu-tag line-blue sm">
					<text>{{list.conCount}}</text>
				</view>
			</view>
			<view class="cu-capsule radius">
				<view class='cu-tag bg-pink sm'>
					<text class='cuIcon-timefill'></text>
				</view>
				<view class="cu-tag line-pink sm">
					<text>{{list.createTime|formatDate}}</text>
				</view>
			</view>
		</view>
		<view>
			<u-parse :html="list.content"></u-parse>
		</view>
		<!-- 遮罩层 -->
		<u-mask :show="isShow" @click="isShow = false"></u-mask>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: {},
				scrollTop: 0,
				type: "",
				isShow: false
			}
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
		},
		onLoad(options) {
			this.type = options.type;
			this.getDataById(options.id);

		},
		methods: {
			async getDataById(id) {
				try {
					this.isShow = true;
					uni.showLoading({
						title: '加载中......'
					});
					let res = null;
					if (this.type == "blog") {
						res = await this.$request({
							url: this.$baseURL + "/web/article/by/" + id,
							method: "GET"
						});
					} else if (this.type == "code") {
						res = await this.$request({
							url: this.$baseURL + "/web/code/by/" + id,
							method: "GET"
						});
					}
					this.list = res;
					uni.hideLoading()
					this.isShow = false;
				} catch (err) {
					uni.hideLoading()
					this.isShow = false;
				}
			}
		},
		filters: {
			formatDate(value) {
				if (value != null) {
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
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
	.box {
		width: 95%;
		margin: 0 auto;

		/deep/.u-back-top__content__tips {
			color: #fff !important;
		}
	}
</style>
