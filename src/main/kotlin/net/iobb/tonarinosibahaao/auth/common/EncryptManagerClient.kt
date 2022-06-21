package net.iobb.tonarinosibahaao.auth.common

import kotlin.Throws
import java.security.NoSuchAlgorithmException
import javax.crypto.NoSuchPaddingException
import javax.crypto.IllegalBlockSizeException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import net.iobb.tonarinosibahaao.auth.common.EncryptManagerClient
import java.security.InvalidKeyException
import java.sql.Timestamp
import java.util.*

object EncryptManagerClient {
    private const val KEY = "aLbAjQ5BNSwFHQTY"
    private const val ALGORITHM = "AES"

    private fun encrypt(source: String, key: String, algorithm: String): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key.toByteArray(), algorithm))
        return String(Base64.getEncoder().encode(cipher.doFinal(source.toByteArray())))
    }

    fun addTimeStamp(encryptedUsername: String): String {
        val timestamp = Timestamp(Date().time).toString()
        val timestampStr = timestamp.replace("[^0-9]".toRegex(), "").substring(0, 8)
        return encryptedUsername + timestampStr
    }

    fun encryptUsername(username: String): String {
        return encrypt(addTimeStamp(username), KEY, ALGORITHM)
    }
}