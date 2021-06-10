export const request = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: options.url,
			method: options.method || 'GET',
			data: options.data || {},
			header: {
				'x-access-token': uni.getStorageSync("token"),
				//参数和返回都不加密
				'x-aes': '0'
			},
			success: (response) => {
				if (!response.data.success) {
					return uni.showToast({
						title: response.data.errorMsg,
						icon: "none"
					})
					reject('error')
					console.log("sss");
				} else {
					resolve(response.data)
				}
			},
			fail: (error) => {
				uni.showToast({
					title: "服务器异常"
				})
				reject(error)
			}
		})
	})
}
