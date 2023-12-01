package day1

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val digitsInLine = it.toCharArray().filter { it -> it.isDigit() }
            val firstDigit = digitsInLine[0].digitToInt()
            val lastDigit = digitsInLine[digitsInLine.size - 1].digitToInt()
            firstDigit * 10 + lastDigit
        }.sum()
    }

    val numberWordLookup = mapOf(
        "one" to '1',
        "two" to '2',
        "three" to '3',
        "four" to '4',
        "five" to '5',
        "six" to '6',
        "seven" to '7',
        "eight" to '8',
        "nine" to '9'
    )

    fun part2(input: List<String>): Int {

        val interim = input.map { line ->
            var firstDigit: Char? = null
            var lastDigit: Char? = null

            var chars = line.toCharArray()
            for ((index, char) in chars.withIndex()) {
                if (char.isDigit() && firstDigit == null) {
                    firstDigit = char
                    break
                } else {
                    if (index + 2 < line.length) {
                        val next3Chars = chars.slice(index..index + 2).joinToString("")
                        when (next3Chars) {
                            "one", "two", "six" -> {
                                firstDigit = numberWordLookup[next3Chars]
                                break
                            }
                        }
                    }
                    if (index + 3 < line.length) {
                        val next4Chars = chars.slice(index..index + 3).joinToString("")
                        when (next4Chars) {
                            "four", "five", "nine" -> {
                                firstDigit = numberWordLookup[next4Chars]
                                break
                            }
                        }
                    }
                    if (index + 4 < line.length) {
                        val next5Chars = chars.slice(index..index + 4).joinToString("")
                        when (next5Chars) {
                            "three", "eight", "seven" -> {
                                firstDigit = numberWordLookup[next5Chars]
                                break
                            }
                        }
                    }
                }
            }
            chars = line.reversed().toCharArray()
            for ((index, char) in chars.withIndex()) {
                if (char.isDigit() && lastDigit == null) {
                    lastDigit = char
                    break
                } else {
                    if (index + 2 < line.length) {
                        val next3Chars = chars.slice(index..index + 2).joinToString("")
                        when (next3Chars) {
                            "eno", "owt", "xis" -> {
                                lastDigit = numberWordLookup[next3Chars.reversed()]
                                break
                            }
                        }
                    }
                    if (index + 3 < line.length) {
                        val next4Chars = chars.slice(index..index + 3).joinToString("")
                        when (next4Chars) {
                            "ruof", "evif", "enin" -> {
                                lastDigit = numberWordLookup[next4Chars.reversed()]
                                break
                            }
                        }
                    }
                    if (index + 4 < line.length) {
                        val next5Chars = chars.slice(index..index + 4).joinToString("")
                        when (next5Chars) {
                            "eerht", "thgie", "neves" -> {
                                lastDigit = numberWordLookup[next5Chars.reversed()]
                                break
                            }
                        }
                    }
                }
            }
            var firstDig = firstDigit?.digitToInt()
            var lastDig = lastDigit?.digitToInt()
            val rtv = (firstDig as Int) * 10 + lastDig as Int
            println("${line}: ${firstDigit}, ${lastDigit}; ${rtv}")
            rtv

        }
        return interim.sum()
    }


    val input = readInput("day1/part")
//    part1(input).println()
    part2(input).println()
}
