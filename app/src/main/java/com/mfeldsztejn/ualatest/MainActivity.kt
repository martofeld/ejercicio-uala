package com.mfeldsztejn.ualatest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.mfeldsztejn.ualatest.events.ShowFragmentEvent
import com.mfeldsztejn.ualatest.ui.list.ListFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance())
                    .commitNow()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount >= 1)
        }

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onShowFragmentEvent(event: ShowFragmentEvent) {
        val transaction = supportFragmentManager.beginTransaction()
                .replace(R.id.container, event.fragment)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(null)

        event.sharedViews?.forEach {
            transaction.addSharedElement(it, ViewCompat.getTransitionName(it)!!)
        }

        transaction.commit()
    }
}
