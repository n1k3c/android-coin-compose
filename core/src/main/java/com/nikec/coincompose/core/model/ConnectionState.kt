package com.nikec.coincompose.core.model

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
