package com.baratali.cnote.feature_note.presentation.util

fun String.isRtlLanguage(): Boolean {
    if (isEmpty()) return false
    return Character.getDirectionality(first()) == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
            Character.getDirectionality(first()) == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC
}