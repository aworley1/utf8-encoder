import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Utf8EncoderTest {
    @Test
    fun `should encode single byte character`() {
        val codePoint = 97

        val result = encode(codePoint)

        assertThat(result.decodeToString()).isEqualTo("a")
    }

    @Test
    fun `should encode two byte character`() {
        val codePoint = 163

        val result = encode(codePoint)

        assertThat(result.decodeToString()).isEqualTo("£")
    }

    @Test
    fun `should encode three byte character`() {
        val codePoint = 0x20ac

        val result = encode(codePoint)

        assertThat(result.decodeToString()).isEqualTo("€")
    }

    @Test
    fun `should encode four byte character`() {
        val codePoint = 0x10347

        val result = encode(codePoint)

        assertThat(result.decodeToString()).isEqualTo("\uD800\uDF47")
    }
}