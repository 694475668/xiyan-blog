import CryptoJS from 'crypto-js';

/**
 *目前crypto-js已支持的算法有：MD5,SHA-1,SHA-256,AES,Rabbit,
 * MARC4,HMAC,HMAC-MD5,HMAC-SHA1,HMAC-SHA256,PBKDF2。
 * 常用的加密方式有MD5和AES，使用时可以引用总文件，也可以单独引用某一文件
 * @param word
 * @param encodeRule
 * @returns {string}
 * @constructor
 */


//AES加密
export function AESEncrypt(word, encodeRule) {
    var key = CryptoJS.enc.Utf8.parse(encodeRule);
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var resultByte = CryptoJS.AES.encrypt(srcs, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
    return resultByte.toString();
}

//AES解密
export function AESDecrypt(word, encodeRule) {
    var key = CryptoJS.enc.Utf8.parse(encodeRule);
    var resultByte = CryptoJS.AES.decrypt(word, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
    return CryptoJS.enc.Utf8.stringify(resultByte).toString()
}