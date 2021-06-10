// eslint-disable-next-line no-unused-vars,camelcase
function loadJs (url, _callback_success, _callback_fail) {
  // eslint-disable-next-line camelcase
  var callback_success = function (response) {
    console.log('load js success...', response)
  }
  // eslint-disable-next-line camelcase
  var callback_fail = function (response) {
    console.log('load js fail...', response)
  }
  // eslint-disable-next-line camelcase
  _callback_success = _callback_success || callback_success
  // eslint-disable-next-line camelcase
  _callback_fail = _callback_fail || callback_fail
  // eslint-disable-next-line no-undef
  $.getScript(url).done(_callback_success).fail(_callback_fail)
}
var API = (function () {
  function API () {
  }

  // 加载js的函数
  API.prototype.loadJs = function (url, callback){
    callback = callback || {}
    loadJs(url, callback.success, callback.fail)
  }

  return API
})()

export default API
