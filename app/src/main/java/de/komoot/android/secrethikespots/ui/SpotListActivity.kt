package de.komoot.android.secrethikespots.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.mapbox.mapboxsdk.geometry.LatLng
import de.komoot.android.secrethikespots.appScope
import de.komoot.android.secrethikespots.model.SecretSpot
import de.komoot.android.secrethikespots.model.SecretSpotRepo
import kotlinx.coroutines.launch
import java.io.File

class SpotListActivity : FragmentActivity() {

    val viewModel : SpotViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo = SecretSpotRepo(this)

        lifecycleScope.launchWhenCreated {
            viewModel.updateSpot.collect {
                appScope().launch {
                    repo.update(it)
                }
            }
        }

        setContent {
            SecretHikeSpotsTheme {
                Scaffold(
                    modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                    },
                    floatingActionButton = {
                        AddSpotActionButton {
                            startActivity(AddSpotActivity.intent(this))
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) { padding ->
                    SecretSpots(
                        padding,
                        repo.getAll().collectAsState(initial = emptyList()),
                        ::editSpot, ::navigate
                    ) {
                        appScope().launch {
                            repo.delete(it.id)
                        }
                    }
                }
            }
        }
    }

    private fun editSpot(spot: SecretSpot) {
        SpotFragment.showExisting(
            supportFragmentManager,
            spot
        )
    }

    private fun navigate(spot: SecretSpot) {
        val lat = spot.location.latitude
        val lon = spot.location.longitude
        val uri = "geo: $lat,$lon ?q= $lat,$lon"
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
    }
}

@Composable
fun AddSpotActionButton(onClick : () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = Color.Yellow,
            modifier = Modifier.testTag("addSpot")
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SecretSpotItem(spot: SecretSpot, onDismiss: (SecretSpot) -> Unit, onEdit: (SecretSpot) -> Unit, onNavigate: (SecretSpot) -> Unit) {
    Box(
        Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        val dismissState = rememberDismissState()

        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
            onDismiss(spot)
        }

        SwipeToDismiss(
            state = dismissState,
            background = {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            },
            directions = setOf(DismissDirection.EndToStart)
        ) {
            Card {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val file = File(spot.image)
                    if (file.exists()) {
                        AsyncImage(
                            model = file,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    onNavigate(spot)
                                }
                                .align(Alignment.CenterEnd)
                                .padding(16.dp)
                                .height(128.dp)
                                .testTag("spotImage")
                        )
                    }
                    Text(
                        text = spot.name,
                        color = Color.Yellow,
                        modifier = Modifier
                            .clickable {
                                onEdit(spot)
                            }
                            .fillMaxHeight(1f)
                            .padding(16.dp)
                            .testTag("spotName")
                    )
                }
            }
        }
    }
}

@Composable
fun SecretSpots(
    padding: PaddingValues,
    spots: State<List<SecretSpot>>,
    onEdit: (SecretSpot) -> Unit,
    onNavigate: (SecretSpot) -> Unit,
    onDismiss: (SecretSpot) -> Unit,
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(padding),
        userScrollEnabled = false
    ) {
        items(spots.value, { it.id }) {
            SecretSpotItem(it, onDismiss, onEdit, onNavigate)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val flow = remember {
        derivedStateOf {
            listOf(
                SecretSpot(0, "name1", LatLng(0.0, 2.0), ""),
                SecretSpot(1, "name2", LatLng(0.0, 2.0), ""),
                SecretSpot(2, "name3", LatLng(0.0, 2.0), ""),
                SecretSpot(3, "name4", LatLng(0.0, 2.0), "")
            )
        }
    }
    SecretHikeSpotsTheme {
        SecretSpots(PaddingValues(0.dp), flow, {}, {}) {}
    }
}