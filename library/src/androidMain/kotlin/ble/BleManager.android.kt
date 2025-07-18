package ble

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class BleManager(
    private val context: Context
) : BleManagerInterface {

    private val bluetoothManager: BluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter


    @RequiresPermission(
        allOf = [
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        ]
    )
    override fun scan(): Flow<BleDeviceInterface> = callbackFlow {

        val scanCallback = object : ScanCallback() {

            @RequiresPermission(
                allOf = [
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                ]
            )
            override fun onScanResult(
                callbackType: Int,
                result: ScanResult?
            ) {
                super.onScanResult(callbackType, result)
                if (result == null) return
                trySend(result.device.let(::toBleDevice))
            }
        }

        bluetoothAdapter.bluetoothLeScanner.startScan(scanCallback)

        awaitClose {
            bluetoothAdapter.bluetoothLeScanner.stopScan(scanCallback)
        }
    }

    @RequiresPermission(
        allOf = [
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        ]
    )
    override fun scanConnectedDevices(): Flow<BleDeviceInterface> {
        return bluetoothManager.getConnectedDevices(BluetoothProfile.GATT)
            .asFlow()
            .map(::toBleDevice)
    }

    @RequiresPermission(
        allOf = [
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        ]
    )
    override fun scanAll(): Flow<BleDeviceInterface> {
        return channelFlow {
            launch {
                scan().collect(::trySend)
            }
            launch {
                scanConnectedDevices().collect(::trySend)
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun toBleDevice(device: BluetoothDevice): BleDevice {
        return BleDevice(device,device.name ?: "", device.address)
    }
}
