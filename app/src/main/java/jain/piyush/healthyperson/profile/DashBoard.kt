package jain.piyush.healthyperson.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import jain.piyush.healthyperson.R

@Composable
fun DashBoard(name : String,age : Int,height : Float,weight : Float) {
    val bmi = remember { calculateBMI(height , weight) }
    val bmiCategory = remember { getBMICategory(bmi) }


    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painterResource(id = R.drawable.ic_background) , contentDescription = null ,
            contentScale = ContentScale.Crop
        )


    }
    Column(
        modifier = Modifier.fillMaxWidth() ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth() ,
            shape = RoundedCornerShape(16.dp) ,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(colorResource(id = R.color.orange) , Color.White)
                        ) ,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row {
                        Image(
                            painterResource(id = R.drawable.ic_profile_picture) ,
                            contentDescription = null ,
                            contentScale = ContentScale.FillWidth ,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Text(text = "Name : $name" , style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Age : $age" , style = MaterialTheme.typography.headlineSmall)


                }
            }


        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier.fillMaxWidth() ,
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                modifier = Modifier.clickable {

                }
                    .padding(16.dp)
                    .wrapContentSize() ,
                shape = RoundedCornerShape(16.dp) ,
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.White ,
                                    colorResource(id = R.color.orange)
                                )
                            ) ,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Row {
                            Image(
                                painterResource(id = R.drawable.ic_height_icon) ,
                                contentDescription = null ,
                                Modifier.size(50.dp)
                            )
                            Text(
                                text = "Height : $height cm" ,
                                style = MaterialTheme.typography.headlineSmall ,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }


            }

        }
        Column(
            modifier = Modifier.fillMaxWidth() ,
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                modifier = Modifier.clickable {

                }
                    .padding(16.dp)
                    .wrapContentSize() ,
                shape = RoundedCornerShape(16.dp) ,
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.White ,
                                    colorResource(id = R.color.orange)
                                )
                            ) ,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Row {
                            Image(
                                painterResource(id = R.drawable.ic_weight) ,
                                contentDescription = null ,
                                Modifier.size(50.dp)
                            )
                            Text(
                                text = "Weight : $weight Kg" ,
                                style = MaterialTheme.typography.headlineSmall ,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }
            }// aache gradient color dhundna hai jo theme ko suit kre


        }
        Box {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp) ,
                colors = CardDefaults.cardColors(bmiCategory.color)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "BMI: $bmi (${bmiCategory.name})")
                }
            }
        }
    }
}

    fun calculateBMI(height: Float, weight: Float): Float {
        return weight / ((height / 100) * (height / 100))
    }
data class BMICategory(val name: String, val color: Color)

fun getBMICategory(bmi: Float): BMICategory {
    return when {
        bmi < 18.5 -> BMICategory("Underweight", Color.Blue)
        bmi < 24.9 -> BMICategory("Normal weight", Color.Green)
        bmi < 29.9 -> BMICategory("Overweight", Color.Yellow)
        else -> BMICategory("Obesity", Color.Red)
    }
}




