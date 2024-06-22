package jain.piyush.healthyperson.presentation

import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInCLick:()-> Unit
){

    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError){
        state.signInError.let { error ->
            makeText(context , "$error message" , LENGTH_SHORT).show()
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.Center){
        Button(onClick = { onSignInCLick()}) {
            Text(text = "Sign In")
            
        }
    }

}