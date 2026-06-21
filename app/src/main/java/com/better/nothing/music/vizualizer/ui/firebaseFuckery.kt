package com.better.nothing.music.vizualizer.ui

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

// Move this to your Application class or a central initialization setup
object FirebaseFuckery {
    fun init() {
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        } catch (e: Exception) {
            // Handle logging properly if needed
        }
    }
}

@Composable
fun launchGoogleSignIn(
    viewModel: Unit // Pass your actual ViewModel instance here
) {
    val context = LocalContext.current

    // 1. Define the launcher inside the Composable lifecycle
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result when it returns
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(Exception::class.java)
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                viewModel.linkWithCredential(credential)
            } catch (e: Exception) {
                Toast.makeText(context, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 2. Wrap the configuration and launch trigger in a memoized callback or function
    val launchGoogleSignIn = remember {
        {
            // Fetch the web client ID string using the context
            val webClientId = context.getString(context.resources.getIdentifier("default_web_client_id", "string", context.packageName))
            // Note: If R.string.default_web_client_id is imported, you can just use context.getString(R.string.default_web_client_id)

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }
    }

    // 3. UI Element triggering the flow
    Button(onClick = { launchGoogleSignIn() }) {
        Text(text = "Sign in with Google")
    }
}