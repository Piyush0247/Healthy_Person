package jain.piyush.healthyperson.profile

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import jain.piyush.healthyperson.R
import jain.piyush.healthyperson.Screen
import jain.piyush.healthyperson.presentation.SignInViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onSave: (String, Int, Float, Float) -> Unit,
    navController: NavController,
    viewModel: SignInViewModel = viewModel()
) {

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

   TopAppBar(title = { Text(text = "Sign in")

   },
       actions = {
           IconButton(onClick = {
              viewModel.resetState()
               navController.navigate(Screen.SignIn.route){
                   popUpTo(Screen.ProfileScreen.route){inclusive = true}
               }
           }) {
               Icon(painterResource(id = R.drawable.ic_sign_out) ,
                   contentDescription = null)
           }
       }

   )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Profile",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileForm(
                name = name,
                age = age,
                gender = gender,
                dob = dob,
                height = height,
                weight = weight,
                onNameChange = { name = it },
                onAgeChange = { age = it },
                onGenderChange = { gender = it },
                onDobChange = { dob = it },
                onHeightChange = { height = it },
                onWeightChange = { weight = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    val ageInt = age.toIntOrNull()
                    val heightFloat = height.toFloatOrNull()
                    val weightFloat = weight.toFloatOrNull()

                    if (ageInt == null || heightFloat == null || weightFloat == null) {
                        errorMessage = "Please enter valid numeric values for age, height, and weight."
                    } else {
                        errorMessage = null
                        onSave(name, ageInt, heightFloat, weightFloat)
                        navController.navigate(Screen.DashBoard.createRoute(name,ageInt,heightFloat,weightFloat))
                    }
                },
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Save", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }






@Composable
fun ProfileForm(
    name: String,
    age: String,
    gender: String,
    dob: String,
    height: String,
    weight: String,
    onNameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onDobChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit
) {

    var expand by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = age,
                    onValueChange = onAgeChange,
                    label = { Text("Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { expand = true },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)
                ) {
                    Text(text = gender.ifEmpty { "Select Gender" }, fontSize = 18.sp)
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = expand,
                    onDismissRequest = { expand = false },
                    modifier = Modifier
                        .wrapContentSize()
                        .weight(1f)
                ) {
                    DropdownMenuItem(text = { Text(text = "Male") }, onClick = {
                        onGenderChange("Male")
                        expand = false
                    })
                    DropdownMenuItem(text = { Text(text = "Female") }, onClick = {
                        onGenderChange("Female")
                        expand = false
                    })
                }
                Spacer(modifier = Modifier.height(6.dp))
                DatePicker(label = dob) { selectedDate ->
                    onDobChange(selectedDate)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = height,
                    onValueChange = onHeightChange,
                    label = { Text("Height (cm)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = weight,
                    onValueChange = onWeightChange,
                    label = { Text("Weight (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    }
}

@Composable
fun DatePicker(
    label: String,
    onDateSelect: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    var selectedDate by remember { mutableStateOf(label) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = dateFormat.format(calendar.time)
            onDateSelect(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Text(
        text = selectedDate.ifEmpty { "Select Date of Birth" } ,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() }
            .padding(8.dp)
    )
}
