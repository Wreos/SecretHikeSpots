package de.komoot.android.secrethikespots

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.ImageView
import androidx.compose.ui.unit.dp
import coil.load
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.snapshotter.MapSnapshotter
import de.komoot.android.secrethikespots.ui.MapFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object ImageGenerator {

    // need to keep a reference to the snapshotters while they're running to keep them getting GCed
    private val snapshotters = mutableMapOf<LatLng, MapSnapshotter>()

    fun imageFile(context: Context, location: LatLng) = File(
        context.filesDir,
        "spot-${location.latitude}:${location.longitude}.image"
    )

    fun loadPreviewImage(scope: CoroutineScope, imageView: ImageView, location: LatLng) {
        val context = imageView.context
        val size = 128.dp.value.toInt()
        val options = MapSnapshotter.Options(size, size)
            .withStyleBuilder(Style.Builder().fromUri(MapFragment.MAP_STYLE))
            .withCameraPosition(CameraPosition.Builder().target(location).zoom(16.0).build())
        val snapshotter = MapSnapshotter(context, options)

        val file = imageFile(context, location)
        if (!file.exists()) {
            scope.launch(Dispatchers.IO) {
                file.parentFile?.mkdirs()
                file.createNewFile()
                snapshotters[location] = snapshotter
                withContext(Dispatchers.Main) {
                    snapshotter.start { snap -> // start has to be called on main
                        scope.launch(Dispatchers.IO) {
                            FileOutputStream(file).use { out ->
                                val format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                                    Bitmap.CompressFormat.WEBP_LOSSY
                                else
                                    Bitmap.CompressFormat.JPEG

                                snap.bitmap.compress(format, 85, out)
                            }
                            withContext(Dispatchers.Main) {
                                snapshotters.remove(location)
                                imageView.load(file)
                            }
                        }
                    }
                }
            }
        }
        else {
            imageView.load(file)
        }
    }
}

