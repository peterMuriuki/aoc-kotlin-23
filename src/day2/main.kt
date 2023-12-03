package day2

import println
import readInput
import kotlin.math.max


fun main() {
    fun splitGameDraw(line: String): Map<String, Int>{
        val drawMap: MutableMap<String, Int> = mutableMapOf()
        for (eachDraw in line.split(";")){
            for (pick in eachDraw.split(",").map {it.trim()}){
                val entry = pick.split(" ")
                    val color = entry.last().trim()
                    val num = entry.first().trim().toInt()
                    drawMap.set(color, maxOf(drawMap.getOrDefault(color, 0), num))
            }
        }
        return drawMap
    }
    fun part1(lines: List<String>): Int {
        val rtvMap:MutableMap<String, Map<String, Int>> = mutableMapOf()
        for (line in lines){
            val gameParts = line.split(":")
            val gameNumStr = gameParts[0].split(" ").map{it.trim()}.last()
            // move to parsing the cube draw.
            val rawCubeDraw = gameParts.last()
            val drawMap = splitGameDraw(rawCubeDraw)
            rtvMap.set(gameNumStr, splitGameDraw(rawCubeDraw))
        }
        return rtvMap.filterKeys { k ->
            val value = rtvMap.get(k)
            val check = mapOf("red" to 12, "green" to 13, "blue" to 14)
            check.keys.all { key ->
                (value?.getOrDefault(key, 0) ?: 0) <= check.get(key)!!
            }
        }.keys.sumOf { it.toInt() }
    }


    fun part2(lines: List<String>): Int {
        val rtvMap:MutableMap<String, Map<String, Int>> = mutableMapOf()
        for (line in lines){
            val gameParts = line.split(":")
            val gameNumStr = gameParts[0].split(" ").map{it.trim()}.last()
            // move to parsing the cube draw.
            val rawCubeDraw = gameParts.last()
            val drawMap = splitGameDraw(rawCubeDraw)
            println("${gameNumStr}: ${drawMap}")
            rtvMap.set(gameNumStr, splitGameDraw(rawCubeDraw))
        }
        val store: MutableList<Int> = mutableListOf()
        return rtvMap.values.map { it -> it.values.reduce { acc, i -> acc * i } }.sum()
    }


    val input = readInput("day2/part")
    part2(input).println()
}
