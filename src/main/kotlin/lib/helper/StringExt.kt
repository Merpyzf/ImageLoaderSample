package lib.helper

import java.lang.Exception
import java.lang.StringBuilder
import kotlin.experimental.and
import java.security.MessageDigest


fun String.toHexStr(): String {
    val md5: MessageDigest? = try {
        MessageDigest.getInstance("MD5")
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
    val charArray: CharArray = this.toCharArray()
    val byteArray = ByteArray(charArray.size)
    for (i in charArray.indices) byteArray[i] = charArray[i].toByte()
    val md5Bytes = md5?.digest(byteArray)
    val hexValue = StringBuffer()
    for (i in md5Bytes!!.indices) {
        val `val` = md5Bytes[i].toInt() and 0xff
        if (`val` < 16) hexValue.append("0")
        hexValue.append(Integer.toHexString(`val`))
    }
    return hexValue.toString()
}