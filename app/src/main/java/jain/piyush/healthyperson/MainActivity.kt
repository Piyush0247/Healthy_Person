package jain.piyush.healthyperson

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import jain.piyush.healthyperson.presentation.GoogleSigninAuth
import jain.piyush.healthyperson.presentation.SignInScreen
import jain.piyush.healthyperson.presentation.SignInViewModel
import jain.piyush.healthyperson.profile.DashBoard
import jain.piyush.healthyperson.profile.ProfileScreen
import jain.piyush.healthyperson.ui.theme.HealthyPersonTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val googleSignInAuth by lazy {
        GoogleSigninAuth(
            context = applicationContext,
            onTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            HealthyPersonTheme {
                val auth = Firebase.auth
                if (auth.currentUser != null) {
                    Toast.makeText(applicationContext, "Sign in successful", Toast.LENGTH_SHORT).show()
                    NavHost(navController = navController, startDestination = Screen.ProfileScreen.route) {
                        composable(Screen.SignIn.route) {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleSignInAuth.getSigninRequest(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )
                            LaunchedEffect(key1 = state.isSignIsSuccessfull) {
                                if (state.isSignIsSuccessfull) {
                                    Toast.makeText(applicationContext, "Sign in successful", Toast.LENGTH_SHORT).show()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInCLick = {
                                    lifecycleScope.launch {
                                        val signInIntent = googleSignInAuth.signin()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(signInIntent ?: return@launch).build()
                                        )
                                    }
                                }
                            )
                        }

                        composable(Screen.ProfileScreen.route) {
                            ProfileScreen(
                                onSave = { name, age, height, weight ->
                                    navController.navigate(Screen.DashBoard.createRoute(name,age,height,weight))
                                },
                                navController = navController
                            )
                        }

                        composable(Screen.DashBoard.route,
                            arguments = listOf(
                                navArgument("name"){type = NavType.StringType},
                                navArgument("age"){type = NavType.IntType},
                                navArgument("height"){type = NavType.FloatType},
                                navArgument("weight"){type = NavType.FloatType}
                            )
                        ) {backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name") ?: ""
                            val age = backStackEntry.arguments?.getInt("age") ?: 0
                            val height = backStackEntry.arguments?.getFloat("height") ?: 0f
                            val weight = backStackEntry.arguments?.getFloat("weight") ?: 0f
                            DashBoard(name = name , age = age , height = height , weight = weight)

                        }
                    }
                } else {
                    NavHost(navController = navController, startDestination = Screen.SignIn.route) {
                        composable(Screen.SignIn.route) {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleSignInAuth.getSigninRequest(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )
                            LaunchedEffect(key1 = state.isSignIsSuccessfull) {
                                if (state.isSignIsSuccessfull) {
                                    Toast.makeText(applicationContext, "Sign in successful", Toast.LENGTH_SHORT).show()
                                    navController.navigate(Screen.ProfileScreen.route) {
                                        popUpTo(Screen.SignIn.route) { inclusive = true }
                                    }
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInCLick = {
                                    lifecycleScope.launch {
                                        val signInIntent = googleSignInAuth.signin()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(signInIntent ?: return@launch).build()
                                        )
                                    }
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}
