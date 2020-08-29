package com.talentodigital.superrepaso.registro.presentation

//Define comportamiento de los datos
sealed class RegistroUiState (
    open val result: Boolean? = null, //Se usa nulo para que se pueda inicializar, luego se reescribe para darle un valor
    open val error: Throwable? = null
) {
    object LoadingRegistroUiState : RegistroUiState()
    object SuccessRegistroUiState : RegistroUiState(result = true)
    object InvalidEmailRegistroUiState : RegistroUiState(result = false)
    data class ErrorRegistroUiState(override val error: Throwable) : RegistroUiState(error = error)

}