package jain.piyush.healthyperson.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import jain.piyush.healthyperson.R
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleSigninAuth(
    private val context : Context,
    private val onTapClient : SignInClient
) {
    private val auth = Firebase.auth
    suspend fun signin() : IntentSender?{
        val result = try {
            onTapClient.beginSignIn(
             beginSingInRequest()
            ).await()

        }catch (e : Exception){
            e.printStackTrace()
            null
        }
        return result?.pendingIntent?.intentSender
    }
    suspend fun getSigninRequest(intent : Intent) : SigninResult{
        val credentials = onTapClient.getSignInCredentialFromIntent(intent)
        val googelIdToken = credentials.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googelIdToken,null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SigninResult(
                data = user?.run {
                    UserData(
                        userId = uid ,
                        name = displayName ,
                        profilePicture = photoUrl?.toString()
                    )
                } ,
                errorMessage = null
            )

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SigninResult(
                data = null ,
                errorMessage = e.message
            )

        }

    }
    suspend fun signOut(){
        try {
          onTapClient.signOut().await()
            auth.signOut()
        }catch (e : Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }
    fun getSignInUser():UserData? = auth.currentUser?.run {
        UserData(
            uid,
            displayName,
            photoUrl?.toString()
        )
    }
    private fun beginSingInRequest() : BeginSignInRequest{
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}