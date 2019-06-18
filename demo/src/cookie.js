/**
 * 创建Cookie
 * @param name Cookie名
 * @param value Cookie的值
 * @param expireHour Cookie的有效时长（小时）
 */
function createCookie (name, value, expireHour) {
  let date = new Date()
  date.setTime(date.getTime() + (expireHour * 60 * 60 * 1000))
  let expires = '; expires=' + date.toUTCString()
  document.cookie = name + '=' + value + expires + '; path=/'
}

/**
 * 根据Cookie名读取Cookie
 * @param name Cookie名
 * @returns 返回值即为Cookie的值，直接使用即可
 */
function readCookie (name) {
  let arr
  let reg = new RegExp('(^| )' + name + '=([^;]*)(;|$)')
  if ((arr = document.cookie.match(reg))) {
    return (arr[2])
  } else {
    return null
  }
}

/**
 * 删除Cookie
 * @param name Cookie名
 */
function eraseCookie (name) {
  createCookie(name, '', -1)
}

export {
  createCookie,
  readCookie,
  eraseCookie
}
