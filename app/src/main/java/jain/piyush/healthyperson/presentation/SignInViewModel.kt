package jain.piyush.healthyperson.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel(){
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result : SigninResult){
         _state.update { it.copy(
             isSignIsSuccessfull = result.data != null,
             signInError = result.errorMessage
         ) }
    }
    fun resetState(){
        _state.update { SignInState() }
    }
}