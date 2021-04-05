import java.math.BigInteger
import kotlin.math.pow

class BitArray(private val array: Array<Bit>) {
    constructor(int: Int) : this(
        BigInteger
            .valueOf(int.toLong())
            .toString(2)
            .map { Bit.fromCode(it) }
            .toTypedArray()
    )

    fun splitMostSignificant(numberOfBits: Int): Pair<BitArray, BitArray> =
        Pair(
            BitArray(array.slice(0 until numberOfBits).toTypedArray()),
            BitArray(array.slice(numberOfBits until array.size).toTypedArray()),
        )

    fun padded(length: Int): BitArray {
        if (length < array.size) throw RuntimeException("Array is already smaller than length")

        val noLeadingZeros = length - array.size - 1

        val leadingZeros = (0..noLeadingZeros).map { Bit.ZERO }

        return BitArray(leadingZeros.toTypedArray() + array)
    }

    fun toByte() = array
        .reversed()
        .mapIndexed { index, bit ->
            (2.0.pow(index) * bit.value).toInt()
        }.sum().toByte()

    override fun equals(other: Any?): Boolean {
        if (other !is BitArray) return false
        return array.toList() == other.array.toList()
    }

    override fun toString(): String {
        return "[ ${array.joinToString { it.code.toString() }} ]"
    }

    infix fun join(other: BitArray): BitArray =
        BitArray(array + other.array)
}

enum class Bit(val code: Char, val value: Int) {
    ONE('1', 1), ZERO('0', 0);

    companion object {
        fun fromCode(char: Char) = values().single { it.code == char }
    }
}