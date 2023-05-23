package com.peterfarlow.healthleaksampleapplication

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.PermissionController
import com.peterfarlow.healthleaksampleapplication.ui.theme.HealthLeakSampleApplicationTheme

class MainActivity : ComponentActivity() {

    private val requestPermissionActivityContract =
        PermissionController.createRequestPermissionResultContract()
    private val healthConnectPermissionsLauncher = registerForActivityResult(requestPermissionActivityContract) {
        println("got health connect permissions ${it.joinToString()}")
    }

    private val notificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        println("notification permission granted? $it")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alreadyHasNotifPermission = Build.VERSION.SDK_INT < 33
        setContent {
            HealthLeakSampleApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Button(
                            onClick = {
                                println("click1")
                                healthConnectPermissionsLauncher.launch(HealthLeakSampleApplication.permissions)
                            }
                        ) {
                            Text(text = "Grant health connect permissions")
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        if (!alreadyHasNotifPermission) {
                            Button(
                                onClick = {
                                    println("click2")
                                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }
                            ) {
                                Text(text = "Grant notification permission")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthLeakSampleApplicationTheme {
        Greeting("Android")
    }
}
