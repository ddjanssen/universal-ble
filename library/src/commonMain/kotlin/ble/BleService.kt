package ble

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BleService {
    val name: String
    val uuid: String

    val characteristics: StateFlow<BleCharacteristic>
}