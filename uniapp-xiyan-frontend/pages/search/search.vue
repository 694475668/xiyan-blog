<template>
	<view class="box">
		<nav-bar :title="'搜索结果'" :back="true" :leftText="'返回'"></nav-bar>
		<nav-bottom></nav-bottom>
		<u-back-top :scroll-top="scrollTop" icon="arrow-up" :icon-style="{'fontSize':'32rpx','color':'#fff'}"
			:custom-style="{'background':'#29CD84'}" tips="顶部"></u-back-top>
		<view class="card">
			<view class="cu-card case" v-for="(item,i) in dataList" :key="item.id" @click="details(item)">
				<view class="cu-item shadow">
					<view class="image">
						<image :src="item.pic" mode="widthFix"></image>
						<view class="cu-tag bg-blue">{{item.type|formatType}}
						</view>
						<view class="cu-bar bg-shadeBottom"> <text class="text-cut"
								v-html="item.title">{{item.title}}</text></view>
					</view>
					<view class="cu-list menu-avatar">
						<view class="cu-item">
							<view class="cu-avatar round lg" :style="{'background-image':'url('+item.image+')'}">
							</view>
							<view class="content flex-sub">
								<view class="text-grey">{{item.name}}</view>
								<view class="text-gray text-sm flex justify-between">
									{{item.createTime|formatDate}}
									<view class="text-gray text-sm" style="color: #3cc2a8">
										<text class="cuIcon-attentionfill margin-lr-xs" style="color: #3cc2a8"></text>
										{{ item.readCount }}
										<text class="cuIcon-appreciatefill margin-lr-xs" style="color:#007aff;"></text>
										{{ item.starCount }}
										<text class="cuIcon-messagefill margin-lr-xs" style="color: #d4237a"></text>
										{{ item.conCount }}
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="isOver" v-if="isOver">-----我是有底线的-----</view>
		<!-- 遮罩层 -->
		<u-mask :show="isShow"></u-mask>
	</view>
</template>

<script>
	import {
		mixin
	} from "@/util";
	export default {
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
		},
		data() {
			return {
				total: "",
				isOver: false,
				isShow: false,
				current: 0,
				scrollTop: 0,
				mode: 'default',
				dataList: [],
				searchParam: {
					pageNo: 1,
					pageSize: 10,
					keywords: "",
				},
			}
		},
		mixins: [mixin],
		filters: {
			//格式化日期
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
			formatType(item) {
				if (item == "0") {
					return "JAVA";
				} else if (item == "1") {
					return "Python";
				} else if (item == "2") {
					return "GO";
				} else if (item == "3") {
					return "PHP";
				} else if (item == "4") {
					return "VUE";
				} else if (item == "5") {
					return "JavaScript";
				} else if (item == "6") {
					return "C";
				} else if (item == "7") {
					return "C++";
				} else if (item == "8") {
					return "Linux";
				} else if (item == "9") {
					return "APP";
				} else if (item == "10") {
					return "其它";
				}
			},
		},
		methods: {
			async list() {
				try {
					this.isShow = true;
					uni.showLoading({
						title: '加载中......'
					});
					const res = await this.$request({
						url: this.$baseURL + "/search/technology/list",
						method: "POST",
						data: this.searchParam
					})
					this.dataList = [...this.dataList, ...res.voList];
					this.total = res.total
					uni.hideLoading()
					this.isShow = false;
				} catch (err) {
					uni.hideLoading()
					this.isShow = false;
				}
			},
			details(item) {
				//判断是博客内容还是源码内容
				if (item.good == null) {
					uni.navigateTo({
						url: "/pages/details/details?id=" + item.id + "&title='搜索结果'" + "&type=code",
						animationType: "pop-in",
						animationDuration: 300
					});
				} else {
					uni.navigateTo({
						url: "/pages/details/details?id=" + item.id + "&type=blog",
						animationType: "pop-in",
						animationDuration: 300
					});
				}
			},
		},
		onLoad(optios) {
			this.searchParam.keywords = optios.keywords
			this.list();
		},
		//触底事件
		onReachBottom() {
			return this.isOver = true;
		},
	}
</script>

<style lang="scss" scoped>
	@import "@/style/icon/iconfont.css";

	.box {
		/deep/.u-search {
			margin: 15px 15px 0px 15px !important;
		}

		/deep/.u-back-top__content__tips {
			color: #fff !important;
		}

		.isOver {
			width: 100%;
			height: 50px;
			line-height: 50px;
			text-align: center;
			font-size: 28rpx;
			color: $uni-text-color-grey;
		}
	}
</style>
