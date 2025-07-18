package ble

import kotlinx.coroutines.flow.Flow

interface BleDeviceInterface {
    val name: String
    val address: String

    fun connect(): Flow<BleConnectionStatus>
}