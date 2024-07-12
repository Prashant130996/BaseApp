package com.android.baseapp.util

import android.os.Build
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.util.regex.Pattern

val String.md5: String
    get() {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

val String.sha1: String
    get() {
        val bytes = MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

/*
* val md5Hash = "test".md5 // 098f6bcd4621d373cade4e832627b4f6
* val sha1Hash = "test".sha1 // a94a8fe5ccb19ba61c4c0873d391e987982fbbd3
* */

fun String.isEmailValid(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

val String.containsLatinLetter: Boolean
    get() = matches(Regex(".*[A-Za-z].*"))

val String.containsDigit: Boolean
    get() = matches(Regex(".*[0-9].*"))

val String.isAlphanumeric: Boolean
    get() = matches(Regex("[A-Za-z0-9]*"))

val String.hasLettersAndDigits: Boolean
    get() = containsLatinLetter && containsDigit

val String.isIntegerNumber: Boolean
    get() = toIntOrNull() != null

val String.toDecimalNumber: Boolean
    get() = toDoubleOrNull() != null

////////////////////////////////////////////////////////////////

val String.jsonObject: JSONObject?
    get() = try {
        JSONObject(this)
    } catch (e: JSONException) {
        null
    }

val String.jsonArray: JSONArray?
    get() = try {
        JSONArray(this)
    } catch (e: JSONException) {
        null
    }

/*
val json = "{\"key\": \"value\"}".jsonObject  // {"key": "value"}
val jsonAgain = json?.toString() // "{"key": "value"}"
val stringFromJson = json?.getString("key") // "value"
*/

/////////////////////////////////////

val String.lastPathComponent: String
    get() {
        var path = this
        if (path.endsWith("/"))
            path = path.substring(0, path.length - 1)
        var index = path.lastIndexOf('/')
        if (index < 0) {
            if (path.endsWith("\\"))
                path = path.substring(0, path.length - 1)
            index = path.lastIndexOf('\\')
            if (index < 0)
                return path
        }
        return path.substring(index + 1)
    }

/*
val lpc1 = "https://google.com/chrome/".lastPathComponent // chrome
val lpc2 = "C:\\Windows\\Fonts\\font.ttf".lastPathComponent // font.ttf
val lpc3 = "/dev/null".lastPathComponent // null
*/

//////////////////////////////////////////////////////


//Parsing hex String to AWT Color
/*val String.awtColor: Color?
    get() {
        val r = substring(1, 3).toIntOrNull(16) ?: return null
        val g = substring(3, 5).toIntOrNull(16) ?: return null
        val b = substring(5, 7).toIntOrNull(16) ?: return null
        return Color(r, g, b)
    }*/

//Format String as Credit Card Number
val String.creditCardFormatted: String
    get() {
        val preparedString = replace(" ", "").trim()
        val result = StringBuilder()
        for (i in preparedString.indices) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ")
            }
            result.append(preparedString[i])
        }
        return result.toString()
    }

// val ccFormatted = "1234567890123456".creditCardFormatted // "1234 5678 9012 3456"


fun isSdkIntTAndAbove(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}
