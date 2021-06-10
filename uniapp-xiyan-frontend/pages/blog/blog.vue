<template>
	<view class="box">
		<nav-bar :title="'博客'"></nav-bar>
		<nav-bottom :current="2"></nav-bottom>
		<u-back-top :scroll-top="scrollTop" icon="arrow-up" :icon-style="{'fontSize':'32rpx','color':'#fff'}"
			:custom-style="{'background':'#29CD84'}" tips="顶部"></u-back-top>
		<view>
			<uni-notice-bar scrollable="true" showClose showIcon background-color="#fff" single="true"
				text="夕颜社区 - 专注于技术|源码分享的IT技术平台"></uni-notice-bar>
		</view>
		<view>
			<u-swiper :list="banners" :interval="4000" effect3d></u-swiper>
		</view>
		<view>
			<u-search shape="round" bg-color="#29CD84" animation border-color="#29CD84" :show-action="false"
				color="#ffffff" placeholder-color="#ffffff" margin="30rpx" @search="search" @blur="search"></u-search>
		</view>
		<view>
			<u-tabs :list="tabsList" active-color="#2dc261" bg-color="#F1F1F1" :current="tabCurrent"
				@change="tabChange"></u-tabs>
		</view>
		<view class="card">
			<view class="cu-card case" v-for="(item,i) in blogList" :key="item.id" @click="details(item.id)">
				<view class="cu-item shadow">
					<view class="image">
						<image :src="item.pic" mode="widthFix"></image>
						<view class="cu-tag bg-blue">{{item.type|formatType}}
						</view>
						<view class="cu-bar bg-shadeBottom"> <text class="text-cut">{{item.title}}</text></view>
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
		<u-mask :show="isShow" @click="isShow = false"></u-mask>
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
				tabsList: [{
					name: '全部'
				}, {
					name: 'JAVA'
				}, {
					name: 'Python',
				}, {
					name: 'GO',
				}, {
					name: 'PHP',
				}, {
					name: 'Vue',
				}, {
					name: 'JavaScript',
				}, {
					name: 'C',
				}, {
					name: 'C++',
				}, {
					name: 'Linux',
				}, {
					name: 'App',
				}, {
					name: '其他',
				}],
				banners: [{
						image: 'https://qiniu-picture.xiyanit.cn/sdf.gif'
					},
					{
						image: 'https://qiniu-picture.xiyanit.cn/ab.gif'
					}
				],
				total: "",
				isOver: false,
				isShow: false,
				current: 0,
				scrollTop: 0,
				tabCurrent: 0,
				mode: 'default',
				cancelButton: "auto",
				blogList: [],
				listQuery: {
					pageNo: 1,
					pageSize: 10,
					//默认是以创建时间倒叙排序
					sortField: "create_time",
					type: "",
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
			search(value) {
				uni.navigateTo({
					url: "/pages/search/search?keywords=" + value
				})
			},
			tabChange(index) {
				this.tabCurrent = index;
				this.listQuery.type = ""
				this.blogList = []
				if (index != 0) {
					this.listQuery.type = index - 1;
				}
				this.list();
			},
			change(e) {
				this.current = e.detail.current;
			},
			async list() {
				try {
					this.isShow = true;
					uni.showLoading({
						title: '加载中......'
					});
					const res = await this.$request({
						url: this.$baseURL + "/web/article/list",
						method: "POST",
						data: this.listQuery
					})
					this.blogList = [...this.blogList, ...res.voList];
					this.total = res.total
					uni.hideLoading()
					this.isShow = false;
				} catch (err) {
					uni.hideLoading()
					this.isShow = false;
				}
			},
			details(id) {
				uni.navigateTo({
					url: "/pages/details/details?id=" + id + "&title=博客详情" + "&type=blog",
					animationType: "pop-in",
					animationDuration: 300
				});
			},
		},
		onLoad() {
			this.list()
		},
		//触底事件
		onReachBottom() {
			if (this.blogList.length == this.total)
				return this.isOver = true;
			this.listQuery.pageNo++;
			this.list();
		},
		//下拉刷新
		onPullDownRefresh() {
			this.listQuery.pageNo = 1;
			this.blogList = [];
			this.isOver = false;
			this.list().then(res => {
				//关闭下拉刷新
				uni.stopPullDownRefresh()
			});
		}
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
