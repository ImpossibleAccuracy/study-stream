package com.studystream.app.data.utils

fun StringBuilder.replace(
    original: String,
    updates: () -> String,
) {
    var i = 0

    while (i + original.length <= length) {
        for (j in i..length) {
            var equal = true

            for (k in original.indices) {
                if (original[k] != get(i + k)) {
                    equal = false
                    break
                }
            }

            if (equal) {
                replace(i, i + original.length, updates())
            }
        }

        i += 1
    }
}

/**
 * If given string is shorten then [maxLength] return it, else crop string and return result.
 */
fun substring(string: String, maxLength: Int): String =
    if (string.length <= maxLength) string
    else string.substring(0, maxLength)
