package com.example.mtctrial.utils

import java.text.Normalizer

fun CharSequence.unaccent(): String {
    val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}