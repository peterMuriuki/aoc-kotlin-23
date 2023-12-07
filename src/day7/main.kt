package day7

import println
import readInput

enum class HandWeight(val rankNum: Int) {
    FIVEKIND(7),
    FOURKIND(6),
    FULLHOUSE(5),
    THREEKIND(4),
    TWOPAIR(3),
    ONEPAIR(2),
    HIGHCARD(1),
}



class Hand(val card: String): Comparable<Hand> {
    companion object{
        val cardHeavinessSequence = mapOf("A" to 14, "K" to 13, "Q" to 12, "J" to 11, "T" to 10, "9" to 9, "8" to 8, "7" to 7, "6" to 6, "5" to 5, "4" to 4, "3" to 3, "2" to 2)
    }
    val cardSet = card.toSet()

    override fun toString(): String {
        return "<Hand ${card}; ${getRank().rankNum}>"
    }

    override fun compareTo(other: Hand): Int {
        if(this.getRank().rankNum == other.getRank().rankNum){
            for (i in 0 until this.card.length){
                val thisCardscharStrength = cardHeavinessSequence.get(this.card[i].toString())!!
                val otherCardsCharStrength = cardHeavinessSequence.get(other.card[i].toString())!!
                val compared = thisCardscharStrength.compareTo(otherCardsCharStrength)
                if (compared == 0){
                    continue
                }else{
                    return compared
                }
            }
        }
        return this.getRank().rankNum.compareTo(other.getRank().rankNum)
    }

    fun getRank(): HandWeight {
        val weight = when (cardSet.size) {
            1 -> HandWeight.FIVEKIND
            5 -> HandWeight.HIGHCARD
            2 -> {
                val oneOf = cardSet.first()
                val oneOfAppearances = card.count { it == oneOf }
                if (listOf(1, 4).contains(oneOfAppearances)) {
                    HandWeight.FOURKIND
                } else {
                    HandWeight.FULLHOUSE
                }
            }

            3 -> {
                // three of a kind, two pair,
                // if one has 3 -> three of a kind.
                val isThreeKind = cardSet.any { fromSet -> card.count { it == fromSet } == 3 }
                if (isThreeKind) {
                    HandWeight.THREEKIND
                } else {
                    HandWeight.TWOPAIR
                }
            }

            else -> HandWeight.ONEPAIR
        }
        return weight
    }
}

class PartTwoHand(val card: String): Comparable<PartTwoHand>{
    val cardSet = card.toSet()
    val internalHand = createInterimHand()
    val internalHandSet = internalHand.toSet()

    companion object{
        val card2HeavinessSequence = mapOf("A" to 14, "K" to 13, "Q" to 12, "T" to 10, "9" to 9, "8" to 8, "7" to 7, "6" to 6, "5" to 5, "4" to 4, "3" to 3, "2" to 2, "J" to 1)
    }

    private fun createInterimHand(): String{
        val mostOcurringCard = cardSet.maxBy { pick-> if(pick == 'J') 0 else card.count{it == pick} }
        val interimHand = card.replace('J', mostOcurringCard)
        return interimHand
    }

    override fun toString(): String {
        return "<Part2Hand ${card};{${internalHand}}; ${getRank().name}>"
    }

    override fun compareTo(other: PartTwoHand): Int {
        if(this.getRank().rankNum == other.getRank().rankNum){
            for (i in 0 until this.card.length){
                val thisCardscharStrength = card2HeavinessSequence.get(card[i].toString())!!
                val otherCardsCharStrength = card2HeavinessSequence.get(other.card[i].toString())!!
                val compared = thisCardscharStrength.compareTo(otherCardsCharStrength)
                if (compared == 0){
                    continue
                }else{
                    return compared
                }
            }
        }
        return this.getRank().rankNum.compareTo(other.getRank().rankNum)
    }

    private fun getRank(): HandWeight {
        val weight = when ( internalHandSet.size) {
            1 -> HandWeight.FIVEKIND
            5 -> HandWeight.HIGHCARD
            2 -> {
                val oneOf = cardSet.first()
                val oneOfAppearances = internalHand.count { it == oneOf }
                if (listOf(1, 4).contains(oneOfAppearances)) {
                    HandWeight.FOURKIND
                } else {
                    HandWeight.FULLHOUSE
                }
            }
            3 -> {
                // three of a kind, two pair,
                // if one has 3 -> three of a kind.
                val isThreeKind = cardSet.any { fromSet -> internalHand.count { it == fromSet } == 3 }
                if (isThreeKind) {
                    HandWeight.THREEKIND
                } else {
                    HandWeight.TWOPAIR
                }
            }

            else -> HandWeight.ONEPAIR
        }
        return weight
    }
}

fun main() {
    fun part1(lines: List<String>): Int {
        val parsed = lines.map { line ->
            val parts = line.split(" ").map { it.trim() }
            val hand = Hand(parts.first())
            val bid = parts.last().toInt()
            Pair(hand, bid)
        }.sortedBy { it.first }
        return parsed.foldIndexed(0) { index, acc, pair ->  acc + ((index+1) * pair.second) }
    }

    fun part2(lines: List<String>): Int {
        val parsed = lines.map { line ->
            val parts = line.split(" ").map { it.trim() }
            val hand = PartTwoHand(parts.first())
            val bid = parts.last().toInt()
            Pair(hand, bid)
        }.sortedBy { it.first }
        parsed.println()
        return parsed.foldIndexed(0) { index, acc, pair ->  acc + ((index+1) * pair.second) }
    }

    val input = readInput("day7/part")
    part2(input).println()
}
