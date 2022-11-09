package com.anovosvit.anymaster.presentation

import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.anovosvit.anymaster.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onContextMenuClosed(menu: Menu) {
        (supportFragmentManager.findFragmentById(R.id.appNavHostFragment)
            ?.childFragmentManager?.fragments?.get(0) as ChatFragment).onContextMenuClosed()
        super.onContextMenuClosed(menu)
    }

}