<script>
	export default {
		onLaunch: function() {
			console.log('onLaunch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		methods: {},
	}
</script>

<style lang="scss">
	/*每个页面公共css colorui*/
	@import "colorui/main.css";
	@import "colorui/icon.css";
	/* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
	@import "uview-ui/index.scss";

	.box {
		/*  #ifdef  H5  */
		padding-bottom: 118upx;
		padding-top: 90upx;
		top: var(--status-bar-height);
		/*  #endif  */

		/*  #ifndef   H5 */
		padding-bottom: 118upx;
		/*  #ifndef   MP-ALIPAY */
		padding-top: 134upx;
		/*  #endif */
		top: var(--status-bar-height);
		/*  #endif */


	}
</style>
