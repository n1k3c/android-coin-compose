package com.nikec.coincompose.data.model

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
