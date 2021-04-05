import Bit.ONE
import Bit.ZERO
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class BitArrayTest {
    @Test
    fun `should convert binary to integer`() {
        val result = BitArray.of(ONE, ONE).toByte()

        assertThat(result).isEqualTo(3)
    }

    @Test
    fun `should split array`() {
        val (mostSignificant, leastSignificant) = BitArray.of(ONE, ZERO, ONE, ZERO, ONE, ZERO).splitMostSignificant(2)

        assertThat(mostSignificant).isEqualTo(BitArray.of(ONE, ZERO))
        assertThat(leastSignificant).isEqualTo(BitArray.of(ONE, ZERO, ONE, ZERO))
    }

    @Test
    fun `should pad array`() {
        val result = BitArray.of(ONE, ZERO).padded(4)

        assertThat(result).isEqualTo(BitArray.of(ZERO, ZERO, ONE, ZERO))
    }

    @Test
    fun `should pad array to original length`() {
        val result = BitArray.of(ONE, ZERO).padded(2)

        assertThat(result).isEqualTo(BitArray.of(ONE, ZERO))
    }

    @Test
    fun `should construct from an Int`() {
        val result = BitArray(6)

        assertThat(result).isEqualTo(BitArray.of(ONE, ONE, ZERO))
    }
}