import Bit.ONE
import Bit.ZERO

val CONTINUATION_BIT_ARRAY = BitArray(arrayOf(ONE, ZERO))
val ONE_MORE_BYTE_FOLLOWS_BIT_ARRAY = BitArray(arrayOf(ONE, ONE, ZERO, ZERO))
val TWO_MORE_BYTES_FOLLOW_BIT_ARRAY = BitArray(arrayOf(ONE, ONE, ONE, ZERO))
val THREE_MORE_BYTES_FOLLOW_BIT_ARRAY = BitArray(arrayOf(ONE, ONE, ONE, ONE))

fun encode(codePoint: Int): ByteArray = when {
    codePoint < 0x80 -> arrayOf(codePoint.toByte()).toByteArray()
    codePoint < 0x800 -> multiByte(codePoint, 2)
    codePoint < 0x10000 -> multiByte(codePoint, 3)
    codePoint < 0x11000 -> multiByte(codePoint, 4)
    else -> TODO()
}

private fun multiByte(codePoint: Int, numberOfBytes: Int): ByteArray {
    val codePointBitArray = BitArray(codePoint).padded(4 + ((numberOfBytes - 1) * 6))

    return (0 until numberOfBytes)
        .fold(Pair(ByteArray(0), codePointBitArray)) { acc: Pair<ByteArray, BitArray>, byteIndex: Int ->
            val numberOfDataBits = if (byteIndex == 0) 4 else 6
            val controlBits = if (byteIndex == 0) firstBytePrefix(numberOfBytes) else CONTINUATION_BIT_ARRAY
            val (mostSignificantData, remainingData) = acc.second.splitMostSignificant(numberOfDataBits)
            Pair(
                acc.first + (controlBits join mostSignificantData).toByte(),
                remainingData
            )
        }.first
}

private fun firstBytePrefix(numberOfBytes: Int) = when (numberOfBytes) {
    2 -> ONE_MORE_BYTE_FOLLOWS_BIT_ARRAY
    3 -> TWO_MORE_BYTES_FOLLOW_BIT_ARRAY
    4 -> THREE_MORE_BYTES_FOLLOW_BIT_ARRAY
    else -> throw RuntimeException()
}
