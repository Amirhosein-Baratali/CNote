package com.baratali.cnote.settings.domain.use_case

import com.baratali.cnote.feature_note.util.PasswordUtils
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import javax.inject.Inject

class VerifyPasswordUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(password: String): Boolean =
        PasswordUtils.verifyPassword(password, dataStoreRepository.getPasswordHash())
}