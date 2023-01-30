package de.komoot.android.secrethikespots.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import de.komoot.android.secrethikespots.R
import de.komoot.android.secrethikespots.appScope
import de.komoot.android.secrethikespots.model.SecretSpotRepo
import kotlinx.coroutines.launch

class AddSpotActivity : FragmentActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, AddSpotActivity::class.java)
    }

    val addViewModel : AddSpotViewModel by viewModels()
    val spotViewModel : SpotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val repo = SecretSpotRepo(this)
        val frag = MapFragment()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, frag)
            commit()
        }

        lifecycleScope.launchWhenStarted {
            addViewModel.selectedLocation.collect {
                it?.let {
                    SpotFragment.showCreate(supportFragmentManager, it)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            spotViewModel.newSpots.collect {
                it?.let {
                    applicationContext.appScope().launch {
                        repo.add(it)
                    }
                    finish()
                }
            }
        }
    }
}
