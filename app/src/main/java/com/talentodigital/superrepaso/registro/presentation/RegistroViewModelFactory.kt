package com.talentodigital.superrepaso.registro.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.talentodigital.superrepaso.registro.domain.RegistraUsuarioUseCase

class RegistroViewModelFactory (
    private val registroUsuarioUseCase: RegistraUsuarioUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RegistraUsuarioUseCase::class.java)
            .newInstance(registroUsuarioUseCase)
    }

}