package ble

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.os.Build
import androidx.annotation.RequiresApi

class GattCallbackDelegator : BluetoothGattCallback() {

    private val gattCallbacks: MutableList<BluetoothGattCallback> = mutableListOf()

    fun registerGattCallback(callback: BluetoothGattCallback) {
        gattCallbacks.add(callback)
    }

    fun deregisterGattCallback(callback: BluetoothGattCallback) {
        gattCallbacks.remove(callback)
    }


    override fun onConnectionStateChange(gatt: android.bluetooth.BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        gattCallbacks.forEach { it.onConnectionStateChange(gatt, status, newState) }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCharacteristicChanged(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        value: ByteArray
    ) {
        super.onCharacteristicChanged(gatt, characteristic, value)
        gattCallbacks.forEach { it.onCharacteristicChanged(gatt, characteristic, value) }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCharacteristicRead(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        value: ByteArray,
        status: Int
    ) {
        super.onCharacteristicRead(gatt, characteristic, value, status)
        gattCallbacks.forEach { it.onCharacteristicRead(gatt, characteristic, value, status) }
    }

    override fun onCharacteristicWrite(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic?,
        status: Int
    ) {
        super.onCharacteristicWrite(gatt, characteristic, status)
        gattCallbacks.forEach { it.onCharacteristicWrite(gatt, characteristic, status) }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onDescriptorRead(
        gatt: BluetoothGatt,
        descriptor: BluetoothGattDescriptor,
        status: Int,
        value: ByteArray
    ) {
        super.onDescriptorRead(gatt, descriptor, status, value)
        gattCallbacks.forEach { it.onDescriptorRead(gatt, descriptor, status, value) }

    }

    override fun onDescriptorWrite(
        gatt: BluetoothGatt?,
        descriptor: BluetoothGattDescriptor?,
        status: Int
    ) {
        super.onDescriptorWrite(gatt, descriptor, status)
        gattCallbacks.forEach { it.onDescriptorWrite(gatt, descriptor, status) }

    }

    override fun onMtuChanged(
        gatt: BluetoothGatt?,
        mtu: Int,
        status: Int
    ) {
        super.onMtuChanged(gatt, mtu, status)
        gattCallbacks.forEach { it.onMtuChanged(gatt, mtu, status) }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPhyRead(
        gatt: BluetoothGatt?,
        txPhy: Int,
        rxPhy: Int,
        status: Int
    ) {
        super.onPhyRead(gatt, txPhy, rxPhy, status)
        gattCallbacks.forEach { it.onPhyRead(gatt, txPhy, rxPhy, status) }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPhyUpdate(
        gatt: BluetoothGatt?,
        txPhy: Int,
        rxPhy: Int,
        status: Int
    ) {
        super.onPhyUpdate(gatt, txPhy, rxPhy, status)
        gattCallbacks.forEach { it.onPhyUpdate(gatt, txPhy, rxPhy, status) }

    }

    override fun onReadRemoteRssi(
        gatt: BluetoothGatt?,
        rssi: Int,
        status: Int
    ) {
        super.onReadRemoteRssi(gatt, rssi, status)
        gattCallbacks.forEach { it.onReadRemoteRssi(gatt, rssi, status) }

    }

    override fun onReliableWriteCompleted(
        gatt: BluetoothGatt?,
        status: Int
    ) {
        super.onReliableWriteCompleted(gatt, status)
        gattCallbacks.forEach { it.onReliableWriteCompleted(gatt, status) }

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onServiceChanged(gatt: BluetoothGatt) {
        super.onServiceChanged(gatt)
        gattCallbacks.forEach { it.onServiceChanged(gatt) }

    }

    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        super.onServicesDiscovered(gatt, status)
        gattCallbacks.forEach { it.onServicesDiscovered(gatt, status) }

    }
}
