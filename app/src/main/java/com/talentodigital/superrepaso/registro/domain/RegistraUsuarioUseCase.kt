package com.talentodigital.superrepaso.registro.domain

class RegistraUsuarioUseCase (
    private val registroRepository: RegistroRepository
) {
    //Ejemplo como se hacía en Java
    /*suspend fun execute (registroUsuario: RegistroUsuario): Boolean {
       return registroRepository.registrarUsuario(registroUsuario)
    } */
    //Forma bonita de Kotlin, pero se reitera el tipo de retorno
    suspend fun execute (registroUsuario: RegistroUsuario) = registroRepository.registrarUsuario(registroUsuario)


}

