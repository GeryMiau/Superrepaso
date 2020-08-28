package com.talentodigital.superrepaso.utils.extensions

import androidx.appcompat.widget.AppCompatEditText
import com.talentodigital.superrepaso.utils.validator.EmailValidator
import com.talentodigital.superrepaso.utils.validator.NameValidator
import com.talentodigital.superrepaso.utils.validator.PassValidator
import com.talentodigital.superrepaso.utils.validator.RutValidator


fun AppCompatEditText.isValidNameInput(message: String) : Boolean{
    val result = NameValidator.validate(text.toString())
    if(!result){
        this.error = message
        requestFocus()
    }
    return result
}

fun AppCompatEditText.isValidRutInput(message: String) : Boolean{
    val result = RutValidator.validate(text.toString())
    if(!result){
        error = message
        requestFocus()
    }
    return result
}

fun AppCompatEditText.isValidEmailInput(message: String) : Boolean{
    val result = EmailValidator.validate(text.toString())
    if(!result){
        error = message
        requestFocus()
    }
    return result
}

fun AppCompatEditText.isValidPassInput(message: String) : Boolean{
    val result = PassValidator.validate(text.toString())
    if(!result){
        error = message
        requestFocus()
    }
    return result
}