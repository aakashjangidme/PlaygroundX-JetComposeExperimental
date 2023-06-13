package com.example.playgroundx.feature.feed

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.playgroundx.R
import com.example.playgroundx.domain.model.DataDummy
import com.example.playgroundx.feature.authentication.AuthViewModel
import com.example.playgroundx.feature.components.BottomNavigationItem
import com.example.playgroundx.feature.components.BottomNavigationMenu


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedsScreen(
    navController: NavController,
    onClickSignOut: () -> Unit,
    onClickProfile: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val signOutState by viewModel.signOutState

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Instagram") }, navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.android),
                    contentDescription = "logo",
                    tint = Color.Unspecified
                )
            }
        }, actions = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.android),
                    contentDescription = null,
                    Modifier.size(30.dp),
                    tint = Color.Black
                )
            }
        })
    }, content = {
        InstagramHomeContent()
    }, bottomBar = {
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.FEED, navController = navController
        )
    })

    /* Box(
         modifier = Modifier
             .statusBarsPadding()
             .fillMaxSize()
     ) {

         Button(
             onClick = onClickProfile,
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(16.dp),
         ) {
             Text(
                 text = "Go to profile", style = MaterialTheme.typography.titleMedium
             )
         }

     }
 */
}


@Composable
fun InstagramHomeContent() {
    Column {
        Divider()
        InstagramStories()
        Divider()
        InstagramPostsList()
    }
}

@Composable
fun InstagramPostsList() {
    val posts = remember { DataDummy.storyList.filter { it.storyImageId != 0 } }

    LazyColumn {
        items(items = posts, itemContent = {
            InstagramListItem(post = it)
        })
    }
}


