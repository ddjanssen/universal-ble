package ble

import kotlinx.coroutines.flow.Flow

interface BleManagerInterface {
    fun scan(): Flow<BleDeviceInterface>
    fun scanConnectedDevices(): Flow<BleDeviceInterface>
    fun scanAll(): Flow<BleDeviceInterface>
}
