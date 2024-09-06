package com.example.logopogo

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val aliasList = listOf(
        "com.example.logopogo.MainActivity",
        "com.example.logopogo.PersonActivityAlias",
        "com.example.logopogo.BubbleActivityAlias",
        "com.example.logopogo.RabbitActivityAlias"
    )

    private var currentAlias by mutableStateOf(aliasList[0])

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogoSelectionScreen(
                aliasList = aliasList,
                currentAlias = currentAlias,
                onLogoSelected = { newAlias ->
                    updateLogo(newAlias)
                    currentAlias = newAlias
                }
            )
        }
    }

    private fun updateLogo(enabledAlias: String) {
        aliasList.forEach { alias ->
            packageManager.setComponentEnabledSetting(
                ComponentName(this, alias),
                if (alias == enabledAlias) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }
}

@Composable
fun LogoSelectionScreen(
    aliasList: List<String>,
    currentAlias: String,
    onLogoSelected: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        aliasList.forEach { alias ->
            Button(
                onClick = { onLogoSelected(alias) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                enabled = alias != currentAlias
            ) {
                Text(text = alias.split(".").last())
            }
        }
    }
}
