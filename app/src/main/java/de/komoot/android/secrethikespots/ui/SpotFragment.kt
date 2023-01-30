package de.komoot.android.secrethikespots.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mapbox.mapboxsdk.geometry.LatLng
import de.komoot.android.secrethikespots.ImageGenerator.imageFile
import de.komoot.android.secrethikespots.ImageGenerator.loadPreviewImage
import de.komoot.android.secrethikespots.R
import de.komoot.android.secrethikespots.model.NewSpot
import de.komoot.android.secrethikespots.model.SecretSpot
import de.komoot.android.secrethikespots.model.UpdateSpot
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SpotViewModel : ViewModel() {
    private val _newSpots = MutableSharedFlow<NewSpot?>()
    val newSpots = _newSpots.asSharedFlow()
    suspend fun addSpot(name: String, location: LatLng, image: String) {
        _newSpots.emit(NewSpot(name, location, image))
    }

    private val _updateSpot = MutableSharedFlow<UpdateSpot>()
    val updateSpot = _updateSpot.asSharedFlow()
    suspend fun updateSpot(spot: SecretSpot, name: String) {
        _updateSpot.emit(UpdateSpot(spot, name))
    }
}

/**
 * Show some details of a Secret Spot,
 * either the spot already exists, or we're about to create a new one
 */
class SpotFragment : DialogFragment() {

    companion object {
        const val ARG_LOCATION = "arg.location"
        const val ARG_SPOT = "arg.spot"

        fun showCreate(fragMan: FragmentManager, location: LatLng) = SpotFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_LOCATION, location)
            }
            show(fragMan, null)
        }

        fun showExisting(fragMan: FragmentManager, spot: SecretSpot) = SpotFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_SPOT, spot)
            }
            show(fragMan, null)
        }
    }

    val viewModel by lazy { ViewModelProvider(requireActivity())[SpotViewModel::class.java] }
    lateinit var nameET: EditText
    val spot by lazy { arguments!!.getParcelable<SecretSpot>(ARG_SPOT) }
    val location by lazy { arguments!!.getParcelable(ARG_LOCATION) ?: spot?.location!! }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.fragment_name, null, false)
        view.findViewById<TextView>(R.id.location).apply {
            text = location.toString()
        }
        nameET = view.findViewById<EditText>(R.id.name).apply {
            setText(spot?.name ?: "")
        }
        loadPreviewImage(requireActivity().lifecycleScope, view.findViewById(R.id.preview), location)

        val builder = AlertDialog.Builder(requireContext(), theme)
            .setCancelable(true)
            .setView(view)
        addButton(builder)
        return builder.create()
    }

    private fun getName() = nameET.text?.toString() ?: ""

    private fun addButton(builder: AlertDialog.Builder) {
        spot.let {
            if (it == null) {
                builder.setPositiveButton(R.string.create) { dialogInterface, _ ->
                    val name = getName()
                    lifecycleScope.launchWhenStarted {
                        viewModel.addSpot(
                            name,
                            location,
                            imageFile(requireContext(), location).absolutePath
                        )
                        dialogInterface.dismiss()
                    }
                }
            }
            else {
                builder.setPositiveButton(R.string.save) { dialogInterface, i ->
                    val name = getName()
                    lifecycleScope.launchWhenStarted {
                        viewModel.updateSpot(it, name)
                        dialogInterface.dismiss()
                    }
                }
            }
        }
    }
}