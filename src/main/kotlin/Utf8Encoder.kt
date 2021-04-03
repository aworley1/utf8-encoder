const val CONTINUATION = 0b10000000
const val ONE_MORE_BYTE_FOLLOWS = 0b11000000
const val TWO_MORE_BYTES_FOLLOW = 0b11100000
const val THREE_MORE_BYTES_FOLLOW = 0b11110000

fun encode(codePoint: Int): ByteArray {
    val bytes = when {
        codePoint < 0x80 -> arrayOf(codePoint)
        codePoint < 0x800 -> twoBytes(codePoint)
        codePoint < 0x10000 -> threeBytes(codePoint)
        codePoint < 0x11000 -> fourBytes(codePoint)
        else -> TODO()
    }

    return bytes.map { it.toByte() }.toByteArray()
}

private fun twoBytes(codePoint: Int): Array<Int> {
    val first4 = codePoint shr 6

    val mask = 0b0000111111
    val last6 = codePoint and mask

    return arrayOf((ONE_MORE_BYTE_FOLLOWS + first4), (CONTINUATION + last6))
}

private fun threeBytes(codePoint: Int): Array<Int> {
    val first4 = codePoint shr 12

    val middle6mask = 0b0000111111000000
    val middle6 = (codePoint and middle6mask) shr 6

    val las6mask = 0b0000111111
    val last6 = codePoint and las6mask

    return arrayOf(
        (TWO_MORE_BYTES_FOLLOW + first4),
        (CONTINUATION + middle6),
        (CONTINUATION + last6)
    )
}

private fun fourBytes(codePoint: Int): Array<Int> {
    val first4 = codePoint shr 18

    val second6mask = 0b0000111111000000000000
    val second6 = (codePoint and second6mask) shr 12

    val third6mask = 0b0000000000111111000000
    val third6 = (codePoint and third6mask) shr 6

    val las6mask = 0b0000111111
    val last6 = codePoint and las6mask

    return arrayOf(
        (THREE_MORE_BYTES_FOLLOW + first4),
        (CONTINUATION + second6),
        (CONTINUATION + third6),
        (CONTINUATION + last6)
    )
}
