package com.baratali.cnote.settings.domain.use_case

import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(newPassword: String) {
        dataStoreRepository.updatePassword(newPassword)
    }
}