package com.mfeldsztejn.ualatest.networking

import org.greenrobot.eventbus.EventBus

class NetworkingBus {
    companion object {
        private val bus = EventBus.builder().build()

        fun register(x: Any) {
            bus.register(x)
        }

        fun unregister(x: Any) {
            bus.unregister(x)
        }

        fun post(x: Any) {
            bus.post(x)
        }
    }
}