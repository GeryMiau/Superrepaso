package com.talentodigital.superrepaso.registro.data.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.talentodigital.superrepaso.registro.domain.RegistroRepository
import com.talentodigital.superrepaso.registro.domain.RegistroUsuario
import kotlinx.coroutines.tasks.await

//Aquí se debe implementar la interfaz tal como se declaró en la interfaz creada

class FirebaseRegistroRepository (
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDataBase: FirebaseDatabase
): RegistroRepository {

    //Se implementa con ayuda del IDE
    override suspend fun registrarUsuario(registroUsuario: RegistroUsuario): Boolean {
        val result = crearUsuarioEnFirebase(registroUsuario.email, registroUsuario.pass)
        guardarNombreEnFirebase(registroUsuario.nombre)
        guardaValoresEnBaseDeDatosFirebase(registroUsuario.nombre, registroUsuario.rut, registroUsuario.email)
        return result.user?.email?.isNotEmpty() ?:false
    }

    //Aquí no sé lo que está haciendo
    private suspend fun guardaValoresEnBaseDeDatosFirebase(nombre: String, rut:String, email: String) {
        //El replace hace que el "." lo remplace por nada, quede sólo con números o letras
        val dataBase = firebaseDataBase.getReference("usuarios/${rut.replace(".", "")}")
        val registroUsuarioFirebase = RegistroUsuarioFirebase(
            nombre,
            rut,
            email
        )
        dataBase.setValue(registroUsuarioFirebase).await()
    }

    private suspend fun crearUsuarioEnFirebase(email: String, pass: String): AuthResult {
        val result = firebaseAuth
            .createUserWithEmailAndPassword(email, pass)
            .await()
        return result
    }

    private suspend fun guardarNombreEnFirebase(nombre: String) {
        val user = firebaseAuth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(nombre)
            .build()
        user?.updateProfile(profileUpdates)?.await()
    }
}
