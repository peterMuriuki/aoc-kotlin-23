package day6

import println
import readInput
import java.lang.Double.min
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.sqrt
import kotlin.text.Regex


fun main() {
    fun getFractPart(digit: Double) = digit.toString().split((".")).last()

    fun quadraticFormula(a: Double, b: Double, c: Double): Pair<Double, Double> {
        val firstRoot = (-b + sqrt(Math.pow(b, 2.0) - 4.0 * a * c)) / 2.0
        val secondRoot = (-b - sqrt(Math.pow(b, 2.0) - 4.0 * a * c)) / 2.0
        return Pair(firstRoot, secondRoot)
    }

    fun getIntRoots(time: Long, distance: Long): Pair<Long, Long> {
        val a = 1.toDouble()
        val b = -time.toDouble()
        val c = distance.toDouble()
        val (firstRoot, secondRoot) = quadraticFormula(a, b, c)
        var biggerRoot = max(firstRoot, secondRoot)
        var smallerRoot = min(firstRoot, secondRoot)
        if (getFractPart(smallerRoot) == "0") {
            smallerRoot += 1.0
        }
        if (getFractPart((biggerRoot)) == "0") {
            biggerRoot -= 1.0
        }
        biggerRoot = floor(biggerRoot)
        smallerRoot = ceil(smallerRoot)
        return Pair(smallerRoot.toLong(), biggerRoot.toLong())

    }

    fun getIntRoots(time: Int, distance: Int): Pair<Int, Int> {
        val a = 1.toDouble()
        val b = -time.toDouble()
        val c = distance.toDouble()
        val (firstRoot, secondRoot) = quadraticFormula(a, b, c)
        var biggerRoot = max(firstRoot, secondRoot)
        var smallerRoot = min(firstRoot, secondRoot)
        if (getFractPart(smallerRoot) == "0") {
            smallerRoot += 1.0
        }
        if (getFractPart((biggerRoot)) == "0") {
            biggerRoot -= 1.0
        }
        biggerRoot = floor(biggerRoot)
        smallerRoot = ceil(smallerRoot)
        return Pair(smallerRoot.toInt(), biggerRoot.toInt())
    }

    fun part1(lines: List<String>): Int {
        val digitRegex = Regex("\\d+")
        val inputs = lines.map { line ->
            val digitsPart = line.split(":").last()
            digitRegex.findAll(digitsPart).toList().map { it.value.toInt() }
        }

        val timeToDistance = inputs.first() zip inputs.last()
        val possibleWays = timeToDistance.map {
            val (time, distance) = it
            val (smallerRoot, largerRoot) = getIntRoots(time, distance)
            (largerRoot - smallerRoot) + 1
        }
        return possibleWays.fold(1) { acc, i ->
            acc * i
        }
    }

    fun part2(lines: List<String>): Long {
        val digitRegex = Regex("\\d+")
        val inputs = lines.map { line ->
            val digitsPart = line.split(":").last()
            digitRegex.findAll(digitsPart).toList().fold("") { acc, i -> acc + i.value }.toLong()
        }
        val time = inputs.first()
        val distance = inputs.last()
        val (smallerRoot, largerRoot) = getIntRoots(time, distance)
        return (largerRoot - smallerRoot) + 1
    }

    val input = readInput("day6/part")
    part2(input).println()
}
