import Bit.ONE
import Bit.ZERO

val CONTINUATION_CODE = BitArray.of(ONE, ZERO)
val ONE_MORE_BYTE_FOLLOWS_CODE = BitArray.of(ONE, ONE, ZERO, ZERO)
val TWO_MORE_BYTES_FOLLOW_CODE = BitArray.of(ONE, ONE, ONE, ZERO)
val THREE_MORE_BYTES_FOLLOW_CODE = BitArray.of(ONE, ONE, ONE, ONE)

fun encode(codePoint: Int): ByteArray = when {
    codePoint < 0x80 -> arrayOf(codePoint.toByte()).toByteArray()
    codePoint < 0x800 -> multiByte(codePoint, 2)
    codePoint < 0x10000 -> multiByte(codePoint, 3)
    codePoint < 0x11000 -> multiByte(codePoint, 4)
    else -> TODO()
}

private fun multiByte(codePoint: Int, numberOfBytes: Int): ByteArray {
    val dataLength = 4 + (6 * (numberOfBytes - 1))
    val codePointBitArray = BitArray(codePoint).padded(dataLength)

    data class Accumulator(val outputBytes: ByteArray, val remainingData: BitArray)

    return (0 until numberOfBytes)
        .fold(Accumulator(ByteArray(0), codePointBitArray)) { acc: Accumulator, byteIndex: Int ->
            val numberOfDataBits = if (byteIndex == 0) 4 else 6
            val controlBits = if (byteIndex == 0) firstBytePrefix(numberOfBytes) else CONTINUATION_CODE
            val (mostSignificantData, remainingData) = acc.remainingData.splitMostSignificant(numberOfDataBits)

            Accumulator(
                outputBytes = acc.outputBytes + (controlBits join mostSignificantData).toByte(),
                remainingData = remainingData
            )
        }.outputBytes
}

private fun firstBytePrefix(numberOfBytes: Int) = when (numberOfBytes) {
    2 -> ONE_MORE_BYTE_FOLLOWS_CODE
    3 -> TWO_MORE_BYTES_FOLLOW_CODE
    4 -> THREE_MORE_BYTES_FOLLOW_CODE
    else -> throw RuntimeException()
}
