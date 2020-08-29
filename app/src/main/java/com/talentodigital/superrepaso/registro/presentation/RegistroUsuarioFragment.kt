package com.talentodigital.superrepaso.registro.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.talentodigital.superrepaso.R
import com.talentodigital.superrepaso.databinding.FragmentRegistroBinding
import com.talentodigital.superrepaso.registro.data.remote.FirebaseRegistroRepository
import com.talentodigital.superrepaso.registro.domain.RegistraUsuarioUseCase
import com.talentodigital.superrepaso.registro.domain.RegistroUsuario
import com.talentodigital.superrepaso.utils.extensions.*

class RegistroUsuarioFragment : Fragment(R.layout.fragment_registro) {
    lateinit var binding: FragmentRegistroBinding
    lateinit var viewModel: RegistroUsuarioViewModel
    lateinit var viewModelFactory: RegistroViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDependencies()
        binding = FragmentRegistroBinding.bind(view)
        setupLiveData()
        setupListener()
    }

    private fun setupDependencies() {
        viewModelFactory =
            RegistroViewModelFactory(
                RegistraUsuarioUseCase(
                    FirebaseRegistroRepository(FirebaseAuth.getInstance(),
                    FirebaseDatabase.getInstance())
                )
            )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroUsuarioViewModel::class.java)
    }


    private fun setupLiveData() {
        //Llama al viewmodel que tieen los datos del Firebase
        viewModel.getLiveData().observe(
            viewLifecycleOwner, // -> Administra el ciclo de vida de la función
            Observer { state ->
                state?.let { handleState(it) }
            }
        )
    }

    //Método handleState que arroja los mensajes de error
    private fun handleState(state : RegistroUiState) {
        //EL when te indica en qué momento está la app
        when (state) {
            is RegistroUiState.LoadingRegistroUiState -> showLoading()
            is RegistroUiState.ErrorRegistroUiState -> showError()
            is RegistroUiState.InvalidEmailRegistroUiState -> repeatedEmail()
            is RegistroUiState.SuccessRegistroUiState -> successRegister()
        }
    }

    private fun showLoading() {
        alert("Cargando...")
    }

    private fun showError() {
        alert("Error del servidor...")
    }

    private fun repeatedEmail() {
        alert("El email ya está siendo usado")
    }

    private fun successRegister() {
        alert("Registro exitoso")
    }

    private fun setupListener() {
        binding.apply {
            btnRegistrar.setOnClickListener {
                if (validarValoresDeEditText()) {
                    viewModel.registroUsuario(obtenerValoresDeEditText())
                }
            }
            btnVolver.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun validarValoresDeEditText(): Boolean {
        binding.apply {
            return etConfirmarContrasena.isValidConfirmPassInput(
                "Las contraseñas deben coincidir",
                etContrasena.text.toString()) ||
                    etContrasena.isValidPassInput("Ingrese una contraseña válida") ||
                    etEmail.isValidEmailInput("Ingrese un correo válido") ||
                    etRut.isValidRutInput("Ingrese un RUN válido") ||
                    etNombre.isValidNameInput("Ingrese un nombre y apellido válidos")
        }
    }

    //Agarra los valores del edit text para enviarlos a la base de datos
    private fun obtenerValoresDeEditText(): RegistroUsuario {
        binding.apply {
            return RegistroUsuario(
                etNombre.text.toString(), //cambia el valor de fragment a un String
                etRut.text.toString(),
                etEmail.text.toString(),
                etContrasena.text.toString()
            )
        }
    }
}
