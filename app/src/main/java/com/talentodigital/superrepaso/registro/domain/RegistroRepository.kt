package com.talentodigital.superrepaso.registro.domain

interface RegistroRepository {
    /*Hilo de la interfaz, se usa sobre todo para llamar al repositorio*/
    suspend fun registrarUsuario(registroUsuario: RegistroUsuario): Boolean
}