package com.baratali.cnote.feature_note.util

import java.security.MessageDigest

object PasswordUtils {
    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun verifyPassword(inputPassword: String, storedHash: String?): Boolean {
        if (storedHash == null) return false
        return hashPassword(inputPassword) == storedHash
    }
}