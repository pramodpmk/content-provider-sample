package com.hifx.syncadapterexample

import android.accounts.Account
import android.accounts.AccountManager
import android.content.ContentResolver
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hifx.syncadapterexample.ui.navigation.SetupNavGraph
import com.hifx.syncadapterexample.ui.theme.SyncAdapterExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        account = createSyncAccount()
        ContentResolver.setIsSyncable(account, "com.promode.note.provider", 1)
        ContentResolver.setSyncAutomatically(account, "com.promode.note.provider", true)
        ContentResolver.addPeriodicSync(account, "com.promode.note.provider", Bundle(), 60 * 60)
        // Request immediate sync
        ContentResolver.requestSync(account, "com.promode.note.provider", Bundle())
        setContent {
            SyncAdapterExampleTheme {
                navHostController = rememberNavController()
                SetupNavGraph(navHostController = navHostController)
            }
        }
    }

    private fun createSyncAccount(): Account {
        val accountType = "com.hifx.syncadapterexample"
        val accountName = "DefaultAccount"
        val account = Account(accountName, accountType)
        val accountManager = getSystemService(ACCOUNT_SERVICE) as AccountManager
        if (accountManager.addAccountExplicitly(account, null, null)) {
            // Account created
        } else {
            // Account already exists or error occurred
        }
        return account
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SyncAdapterExampleTheme {
        Greeting("Android")
    }
}
