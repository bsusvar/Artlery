package com.example.artlery.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artlery.R
import com.example.artlery.ui.components.MedHeaderComp
import com.example.artlery.ui.components.StandardTextComp

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.about_app_title))
        AboutContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun AboutContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current


    val version = try {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName
    } catch (e: Exception) {
        "1.1"
    }

    val description = stringResource(id = R.string.app_description)
    val appDescription = String.format(description, version)
    val emailSubject = stringResource(R.string.email_subject)
    val sendEmailPrompt = stringResource(R.string.send_email_prompt)


    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.a_icono),
            contentDescription = stringResource(R.string.logo_desc),
            modifier = Modifier
                .size(100.dp)
                .padding(top = 16.dp)
        )

        StandardTextComp(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            text = appDescription.trimIndent(),
            style = MaterialTheme.typography.bodyLarge,
        )

        val emailRecipient = "contacto@artleryapp.com"
        Box(
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "message/rfc822"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(emailRecipient))
                        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                        putExtra(Intent.EXTRA_TEXT, appDescription.trimIndent())
                    }
                    context.startActivity(
                        Intent.createChooser(
                            intent,
                            sendEmailPrompt
                        )
                    )
                }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    imageVector = Icons.Default.Email,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(R.string.send_email_desc),
                )
                Text(
                    text = emailRecipient,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}