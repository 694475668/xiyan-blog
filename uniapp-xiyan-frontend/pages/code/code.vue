<template>
	<view class="box">
		<nav-bar :title="'毕设'"></nav-bar>
		<nav-bottom :current="1"></nav-bottom>
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
			<uni-card v-for="(item,i) in codeList" :key="i" :title="item.title" mode="style" :is-shadow="true" isShadow
				:thumbnail="item.pic" note="Tips" @click="details(item.id)">
				<uni-tag text="官方" v-if="item.official == '1'" size="small" type="primary" :circle="true"></uni-tag>
				<uni-tag text="精品" v-if="item.boutique == '1'" size="small" type="success" :circle="true"></uni-tag>
				<uni-tag text="推荐" v-if="item.recommend == '1'" size="small" type="error" :circle="true"></uni-tag>
				<uni-tag text="置顶" v-if="item.top == '1'" size="small" type="warning" :circle="true"></uni-tag>
				<template v-slot:footer>
					<view class="footer-box">
						<view class="left">
							<view>
								<span v-for="(tag, idx) in item.tagList" :key="idx">
									<uni-icons type="map-filled" :color="idx | mapTagColor" class="icon"></uni-icons>
									<text>
										{{ tag }}
									</text>
								</span>
							</view>
							<view>
								<uni-icons type="" class="icon iconfont icon-liulan" style="color: #d4237a"></uni-icons>
								<text style="color: #d4237a">
									{{ item.readCount }}
								</text>
								<uni-icons type="" class="icon iconfont icon-dianzan" style="color: #d81e06">
								</uni-icons>
								<text style="color: #d81e06">
									{{ item.starCount }}
								</text>
								<uni-icons type="" class="icon iconfont icon-pinglun_huabanfuben"
									style="color: #3cc2a8">
								</uni-icons>
								<text style="color: #3cc2a8">
									{{ item.conCount }}
								</text>

								<uni-icons type="" class="icon iconfont icon-download" style="color: #1296db">
								</uni-icons>
								<text style="color: #1296db">
									{{ item.downloadCount }}
								</text>

							</view>
						</view>
						<view class="right">
							<view>
								<uni-icons type="contact" class="icon" style="color:#007aff;"></uni-icons>
								<text style="color: #007aff">
									{{ item.name }}
								</text>
							</view>
							<view>
								<uni-icons type="" class="icon iconfont icon-riqi" style="color: #808080"></uni-icons>
								<text style="color: #808080">
									{{item.createTime|formatDate}}
								</text>
							</view>
						</view>
					</view>
				</template>
			</uni-card>
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
				tabCurrent: 0,
				isShow: false,
				current: 0,
				scrollTop: 0,
				mode: 'default',
				cancelButton: "auto",
				codeList: [],
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
					return y + "-" + MM + "-" + d;
				}
			},
		},
		methods: {
			search(value) {
				uni.navigateTo({
					url: "/pages/search/search?keywords=" + value
				})
			},
			change(e) {
				this.current = e.detail.current;
			},
			tabChange(index) {
				this.tabCurrent = index;
				this.codeList = []
				this.listQuery.type = ""
				if (index != 0) {
					this.listQuery.type = index - 1;
				}
				this.list();
			},
			async list() {
				try {
					this.isShow = true;
					uni.showLoading({
						title: '加载中......'
					});
					const res = await this.$request({
						url: this.$baseURL + "/web/code/list",
						method: "POST",
						data: this.listQuery
					})
					this.codeList = [...this.codeList, ...res.voList];
					this.total = res.total
					uni.hideLoading()
					this.isShow = false;
				} catch (err) {
					this.isShow = false;
					uni.hideLoading()
				}
			},
			details(id) {
				uni.navigateTo({
					url: "/pages/details/details?id=" + id + "&type=code",
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
			if (this.codeList.length == this.total)
				return this.isOver = true;
			this.listQuery.pageNo++;
			this.list();
		}, //下拉刷新
		onPullDownRefresh() {
			this.listQuery.pageNo = 1;
			this.codeList = [];
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
		.isOver {
			width: 100%;
			height: 50px;
			line-height: 50px;
			text-align: center;
			font-size: 28rpx;
			color: $uni-text-color-grey;
		}

		/deep/.u-back-top__content__tips {
			color: #fff !important;
		}

		/deep/.uni-card__thumbnailimage {
			height: 180px !important;
		}

		/deep/.uni-card {
			margin: 18px 16px !important;
		}

		.footer-box {
			display: inline-flex;
		}

		.card {
			/deep/.uni-tag {
				display: inline-flex !important;
				width: 15% !important;
				margin-right: 10rpx !important;
			}

			.left,
			.right {
				display: flex;
				justify-content: flex-start;
				flex-direction: column;
				align-content: center;
				font-size: 24rpx;

				.icon {
					margin-right: 6rpx;
				}

				text {
					margin-right: 8rpx;
				}
			}

			.right {
				margin-left: 160rpx;
			}
		}
	}
</style>
