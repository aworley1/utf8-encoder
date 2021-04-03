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