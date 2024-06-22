package jain.piyush.healthyperson.presentation


data class SigninResult(
    val data : UserData?,
    val errorMessage : String?
)
data class UserData(
    val userId : String,
    val name : String? ,
    val profilePicture : String?

)