# Introduction to Unicode and UTF8

## Introduction

Character encodings are one of the computer abstractions that we use most often. These conversions happen silently every time we deal with text in computer memory, write a file in
a text-based format to disk, transfer textual data over a network and during many other operations.

This article attempts to concisely explain the basic concepts and how Unicode/UTF-8 solve the problems of varying international character sets and enable things like emoji.

## Definitions

Code page
: A mapping of characters/control characters, each to a unique numeric identifier

Code point
: A single numeric value from a code page

Character encoding
: An algorithm that encodes the code point into a series of bits

## A simple example: ASCII

The original ASCII code page was developed in the 1960s and was based on 128 code points. 0-31 were reserved as control characters for devices (e.g. printers) and 32-127 were
the printable characters: upper and lower case letters, numbers and a few common special characters.

The character encoding can be simple - converting the decimal number to binary directly can encode all values with 7 bits per character. When dealing with 8 bit bytes, this can
simply be padded with a leading zero.

### Example character:
```
Character: a (Lower case a)
Code point (decimal): 97
Code point (hex): 61
Binary encoding: 01100001
```

The problem with ASCII becomes apparent as soon as you want to represent a character outside the 128 code points. An example would be the British Pound Sign: `£`.
Multiple extended-ASCII code pages were developed to help with this problem, however it was not entirely satisfactory as you would need to know which code page
was being used to decode the data, and a single code page may not have all the required characters.

## Unicode and UTF-8

Unicode is a standard that attempts to solve the previously mentioned problems, providing a code page large enough to cover the scripts of all of the world languages,
and character encodings to support this.

### UTF-8
UTF-8 is the most popular of the Unicode character encodings. It has a number of advantages over the other variants:
* It is backwards compatible with the ASCII character encoding described in the previous section
* It can be space efficient - for US-ASCII, only 1 byte is used per character, 2 bytes will encode characters of almost all Latin-script alphabets and others including Greek and
  Hebrew. 3 bytes are required to encode Chinese/Japanese characters. This is also a criticism that Unicode/UTF-8 are western-centric.

#### Algorithm description

For code points less than `0x80` (128) the code point value is simply encoded into natural binary and padded with leading zeros to 8 bits. As the code points are the same as
ASCII in this range, and the character encoding is the same, this supports backwards compatibility with ASCII.

For characters which need more than 1 byte to encode, the first byte is split as follows:

```
First 4 bits: A code which represents how many bytes follow for this character
Remaining 4 bits: The first 4 bits of the binary representation of the code point
```
Where the first 4 bits are one of the following values:
```
1100 - One more byte follows (code points < 0x800)
1110 - Two more bytes follow (code points < 0x10000)
1111 - Three more bytes follow (code points < 0x11000)
```

Then one or more continuation bytes are required, which are split as follows:
```
First 2 bits: 10 (continuation marker)
Remaining 6 bits: The next 6 bits of the binary representation of the code point
```

#### An example

`€` - code point `0x20ac` - taken from the Wikipedia UTF-8 article

`0x800 < 0x20ac < 0x10000` therefore 3 bytes are required

`0x20ac` converted to binary is `10000010101100`, but we need to pad this to 16 bits (4 bits in byte 1, 6 in byte 2 and 6 in byte 3):
`0010000010101100`

Then we take this and split it into the three parts for the three bytes:
```
0010
000010
101100
```

and prepend the relevant codes to these to create the three bytes:
```
11100010 (prepend 1110 - 2 more bytes follow)
10000010 (prepend 10 - the continuation marker)
10101100 (prepend 10 - the continuation marker)
```

#### An implementation in Kotlin

I've written a (not production ready) implementation of the encoding algorithm in Kotlin for learning purposes. You can see the code at:
https://github.com/aworley1/utf8-encoder/blob/master/src/main/kotlin/Utf8Encoder.kt