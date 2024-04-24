package com.example.myedutarcset1chongmingli

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myedutarcset1chongmingli.ui.theme.Myedutarcset1ChongMingLiTheme

enum class ScoreCard() {
    Start,
    Contact
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Myedutarcset1ChongMingLiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudentScore()
                }
            }
        }
    }
}

@Composable
fun StudentScore() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScoreCard.Start.name
    ) {
        composable(route = ScoreCard.Start.name){
            Scores(onContactClicked = { navController.navigate(ScoreCard.Contact.name) })
        }
        composable(route = ScoreCard.Contact.name){
            Contact(onBackClicked = { navController.navigateUp() })
        }
    }
}

@Composable
fun Scores(
    onContactClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("Name") }
    var studentID by remember { mutableStateOf("Student ID") }
    var courseCode by remember { mutableStateOf("Course Code") }
    var score by remember { mutableStateOf("0") }
    var grade by remember { mutableStateOf("N/A") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(modifier = Modifier.padding(top = 50.dp)) {
            TextField(
                label = { Text(text = "Name") },
                value = name,
                onValueChange = { name = it }
            )
            TextField(
                label = { Text(text = "Student ID") },
                value = studentID,
                onValueChange = { studentID = it }
            )
            TextField(
                label = { Text(text = "Course Code") },
                value = courseCode,
                onValueChange = { courseCode = it }
            )
            TextField(
                label = { Text(text = "Score") },
                value = score,
                onValueChange = { score = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    grade = calculateScore(score.toDoubleOrNull() ?: 0.0)
                          },
                modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Submit")
            }
        }
        Spacer(Modifier.height(50.dp))
        Card() {
            Text(text = "Name: $name", modifier = Modifier.padding(5.dp))
            Text(text = "Student ID: $studentID", modifier = Modifier.padding(5.dp))
            Text(text = "Course Code: $courseCode", modifier = Modifier.padding(5.dp))
            Text(text = "Score: $score", modifier = Modifier.padding(5.dp))
            Text(text = "Grade: $grade", modifier = Modifier.padding(5.dp))
        }
        Button(onClick = onContactClicked) {
            Text(text = "Contact Us")
        }
    }
}

@Composable
fun Contact(onBackClicked: () -> Unit) {
    val telNo = "+0123456789"
    val context = LocalContext.current
    val email = "exampleemail@gmail.com"
    Column() {
        Button(onClick = onBackClicked) {
            Text(text = "Back")
        }
        Text(text = telNo)
        Text(text = email)
        Button(onClick = { callIntent(context, telNo) }
        ) {
            Text(text = "Call")
        }
        Button(onClick = { emailIntent(context, email) } ) {
            Text(text = "Email")
        }
    }
}

fun callIntent(context: Context, telNo: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$telNo")
    }
    context.startActivity(intent)
}

fun emailIntent(context: Context, email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, email)
    }
    context.startActivity(intent)
}

fun calculateScore(score: Double): String {
    if(score in 80.0..100.0) {
        return "A"
    }
    else if(score in 75.0..79.0) {
        return "A-"
    }
    else if(score in 70.0..75.0) {
        return "B"
    }
    else if(score in 65.0..69.0) {
        return "B"
    }
    else if(score in 60.0..64.0) {
        return "B-"
    }
    else if(score in 55.0..59.0) {
        return "C+"
    }
    else if(score in 50.0..54.0) {
        return "C"
    }
    else {
        return "D"
    }
}

@Preview(showBackground = true)
@Composable
fun ScoresPreview() {
    Myedutarcset1ChongMingLiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        }
    }
}