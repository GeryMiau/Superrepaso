package com.talentodigital.superrepaso.registro.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase
import com.talentodigital.superrepaso.registro.domain.RegistraUsuarioUseCase
import com.talentodigital.superrepaso.registro.domain.RegistroUsuario
import kotlinx.coroutines.launch
import java.lang.Exception

class RegistroUsuarioViewModel (
    private val registroUsuarioUseCase: RegistraUsuarioUseCase
) : ViewModel() {
    //Es importante quee livedata sea privada para que no se pueda cambiar el estado de cualquier parte
    private val liveData = MutableLiveData<RegistroUiState>()
    //Esta es la función con el modificador de acceso
    fun getLiveData() = liveData
    fun registroUsuario (registroUsuario: RegistroUsuario) {
        liveData.postValue(RegistroUiState.LoadingRegistroUiState)
        viewModelScope.launch {
            try {
                val result = registroUsuarioUseCase.execute(registroUsuario)
                handleResult(result)
            } catch (exception : Exception) {
                handleError (exception)
            }
        }
    }

    private fun handleResult(result: Boolean) {
        if (result) {
            liveData.postValue(RegistroUiState.SuccessRegistroUiState)
        } else {
            liveData.postValue(RegistroUiState.InvalidEmailRegistroUiState)
        }
    }

    private fun handleError(exception: Exception) {
        if (exception is FirebaseAuthUserCollisionException) {
            liveData.postValue(RegistroUiState.InvalidEmailRegistroUiState)
        } else {
            liveData.postValue(RegistroUiState.ErrorRegistroUiState(exception))
        }
    }
}