const getters = {
    sidebar: state => state.app.sidebar,
    token: state => state.user.token,
    userInfo: state => state.user.userInfo,
}
export default getters