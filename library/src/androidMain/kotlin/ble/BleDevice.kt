package ble

import android.Manifest
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BleDevice(
    private val device: android.bluetooth.BluetoothDevice,
    override val name: String,
    override val address: String
) : BleDeviceInterface {

    private val gattCallbackDelegator = GattCallbackDelegator()

    private var bluetoothGatt: BluetoothGatt? = null

    @RequiresPermission(
        allOf = [
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        ]
    )
    override fun connect(): Flow<BleConnectionStatus> = callbackFlow {
        val connectCallback = object : BluetoothGattCallback() {
            override fun onConnectionStateChange(
                gatt: BluetoothGatt?,
                status: Int,
                newState: Int
            ) {
                super.onConnectionStateChange(gatt, status, newState)
                if (status != BluetoothGatt.GATT_SUCCESS) {
                    trySend(BleConnectionStatus.DISCONNECTED)
                } else {
                    trySend(BleConnectionStatus.CONNECTED)
                }
            }
        }

        gattCallbackDelegator.registerGattCallback(connectCallback)
        bluetoothGatt = device.connectGatt(null, false, gattCallbackDelegator)
        awaitClose {
            gattCallbackDelegator.deregisterGattCallback(connectCallback)
            bluetoothGatt?.disconnect()
            bluetoothGatt?.close()
        }
    }
}