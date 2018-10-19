package com.mfeldsztejn.ualatest.events

import android.view.View
import androidx.fragment.app.Fragment

data class ShowFragmentEvent(val fragment: Fragment, val sharedViews: List<View>?)