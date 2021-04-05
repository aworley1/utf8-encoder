import Bit.ONE
import Bit.ZERO
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class BitArrayTest {
    @Test
    fun `should convert binary to integer`() {
        val result = BitArray(arrayOf(ONE, ONE)).toByte()

        assertThat(result).isEqualTo(3)
    }

    @Test
    fun `should split array`() {
        val (mostSignificant, leastSignificant) = BitArray(arrayOf(ONE, ZERO, ONE, ZERO, ONE, ZERO)).splitMostSignificant(2)

        assertThat(mostSignificant).isEqualTo(BitArray(arrayOf(ONE, ZERO)))
        assertThat(leastSignificant).isEqualTo(BitArray(arrayOf(ONE, ZERO, ONE, ZERO)))
    }

    @Test
    fun `should pad array`() {
        val result = BitArray(arrayOf(ONE, ZERO)).padded(4)

        assertThat(result).isEqualTo(BitArray(arrayOf(ZERO, ZERO, ONE, ZERO)))
    }

    @Test
    fun `should pad array to original length`() {
        val result = BitArray(arrayOf(ONE, ZERO)).padded(2)

        assertThat(result).isEqualTo(BitArray(arrayOf(ONE, ZERO)))
    }
}