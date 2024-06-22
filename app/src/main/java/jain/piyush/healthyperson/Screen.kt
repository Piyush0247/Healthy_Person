package jain.piyush.healthyperson

sealed class Screen (val route: String){
    object SignIn : Screen("sign_in")
   object ProfileScreen : Screen("profile_screen")
    object DashBoard : Screen("dash_board/{name}/{age}/{height}/{weight}")
    fun createRoute(name: String , age: Int , height: Float , weight: Float) =
        "dash_board/$name/$age/$height/$weight"

}
