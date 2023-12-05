package day4

import println
import readInput
import kotlin.text.Regex


fun main() {
    fun part1(lines: List<String>): Int {
        val pointsPerCard =  lines.map { line ->
            val parts = line.split(":")
            val cardNums = parts.last().split("|").map {
                Regex("\\d+").findAll(it).map { it.value }
            }
            val winningNums = cardNums[0]
            val pickedNums = cardNums[1]
            val commonNums = winningNums.toSet().intersect(pickedNums.toSet())
            val hitsNum = commonNums.size
            val points = if (hitsNum <= 1) hitsNum else Math.pow(2.toDouble(), (hitsNum - 1).toDouble()).toInt()
            points
        }
        return pointsPerCard.sum()
    }


    fun part2(lines: List<String>): Int {
        val digitsRegex = Regex("\\d+")
        val cardCopyCount: MutableMap<String, Int> = mutableMapOf()
        lines.forEachIndexed { index, line ->
            val parts = line.split(":")
            val cardNum = digitsRegex.find(parts.first())!!.value
            val cardNums = parts.last().split("|").map {
                digitsRegex.findAll(it).map { it.value }
            }
            val winningNums = cardNums[0]
            val pickedNums = cardNums[1]
            val commonNums = winningNums.toSet().intersect(pickedNums.toSet())
            // get copies for current card numer
            val currentCardCopies = cardCopyCount.getOrDefault(cardNum, 1)
            cardCopyCount.set(cardNum, currentCardCopies)
            // compute card copies.
            repeat(currentCardCopies){
                var nextCardsCursor = 1

                while (nextCardsCursor <= commonNums.size){
                    val nextCardIndex = (nextCardsCursor + index + 1).toString()
                    val nextCardCopyCount = cardCopyCount.getOrDefault(nextCardIndex, 1) + 1
                    cardCopyCount.set(nextCardIndex, nextCardCopyCount )
                    nextCardsCursor += 1
                }
            }
        }
        return cardCopyCount.values.sum()
    }


    val input = readInput("day4/part")
    part2(input).println()
}
