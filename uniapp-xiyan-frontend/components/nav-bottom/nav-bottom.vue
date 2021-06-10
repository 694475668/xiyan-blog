<template>
	<view>
		<view>
			<cmd-bottom-nav background-color="linear-gradient(to right, rgb(17, 153, 142), rgb(56, 239, 125))"
				font-color="#fff" active-font-color="#fff" :current="current" :list="tabBarList" text-auto
				@click="router">
			</cmd-bottom-nav>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				tabBarList: [{
					"pagePath": "pages/home/home",
					"text": "首页",
					"src": "../../static/tabbar/home.png",
					"srcSelect": "../../static/tabbar/home-active.png"
				}, {
					"pagePath": "pages/code/code",
					"text": "毕设",
					"src": "../../static/tabbar/code.png",
					"srcSelect": "../../static/tabbar/code-active.png"
				}, {
					"pagePath": "pages/blog/blog",
					"text": "博客",
					"src": "../../static/tabbar/blog.png",
					"srcSelect": "../../static/tabbar/blog-active.png"
				}, {
					"pagePath": "pages/classroom/classroom",
					"text": "课堂",
					"src": "../../static/tabbar/classroom.png",
					"srcSelect": "../../static/tabbar/classroom-active.png"
				}, {
					"pagePath": "pages/user/user",
					"text": "我的",
					"src": "../../static/tabbar/user.png",
					"srcSelect": "../../static/tabbar/user-active.png"
				}],
			}
		},
		props: {
			current: {
				type: Number,
				default: 0
			}
		},
		methods: {
			router(e) {
				uni.redirectTo({
					url: e.item.pagePath
				})
			}
		}
	}
</script>

<style>
</style>
