package com.example.mpcwalletdemo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.unboundTech.mpc.MPCException
import com.unboundTech.mpc.Native
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.security.KeyFactory
import java.security.interfaces.ECPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mpcwalletdemo", appContext.packageName)

        com.example.mpcwalletdemo.Test.main(null)
    }

    @Test
    fun test() {
        var handle = 0L
        val outLen = Native.IntRef()
        println("outLen.value = "+ outLen.value)

        MPCException.check(Native.getEcdsaPublic(handle, null, outLen))
        val encoded = ByteArray(outLen.value)
        println("encoded.size = ${encoded.size}")
        println("encoded = ${byteArrayToHexString(encoded)}")

        MPCException.check(Native.getEcdsaPublic(handle, encoded, outLen))

        val spec = X509EncodedKeySpec(encoded)
        val kf = KeyFactory.getInstance("EC")
        val ecPublicKey = kf.generatePublic(spec) as ECPublicKey
    }

    fun byteArrayToHexString(byteArray: ByteArray): String {
        val hexChars = "0123456789ABCDEF"
        val result = StringBuilder(byteArray.size * 2)
        for (byte in byteArray) {
            val highNibble = (byte.toInt() shr 4) and 0x0F
            val lowNibble = byte.toInt() and 0x0F
            result.append(hexChars[highNibble])
            result.append(hexChars[lowNibble])
        }
        return result.toString()
    }

}