package jain.piyush.healthyperson

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HealthViewModel : ViewModel() {
  var _healthHeight by mutableStateOf(0f)
    var _healthWeight by mutableStateOf(0f)

fun updateHealthHeight(height : Float){
    _healthHeight = height
}
    fun updateHealthWeight(weight : Float){
        _healthHeight = weight
    }
}