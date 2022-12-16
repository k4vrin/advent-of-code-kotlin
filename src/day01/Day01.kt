package day01

import readInput
import java.util.PriorityQueue

fun main() {
    fun part1(input: String): Int {
        return sumElfCalories(input).max()
    }

    fun part2(input: String): Int {
        return sumElfCalories(input) caloriesOfTop 3
    }

    val testInput = readInput("day01", "Day01_test")
    val input = readInput("day01", "Day01")
    check(part1(testInput) == 24000)
    println(part1(testInput))
    println(part1(input))

    check(part2(testInput) == 45000)
    println(part2(testInput))
    println(part2(input))

}

fun sumElfCalories(elvesCalories: String) =
    elvesCalories.split("${System.lineSeparator()}${System.lineSeparator()}")
        .map { elf ->
            elf.lines()
                .sumOf { it.toInt() }
        }


infix fun List<Int>.caloriesOfTop(n: Int) =
// O(NLogN)
    this.sortedDescending()
        .take(n)
        .sum()

// O(size * log size) -> O(size * log n)
infix fun List<Int>.lowerComplexityCaloriesOfTop(n: Int): Int {
    // PriorityQueue for when we have two equal element
    val best = PriorityQueue<Int>()
    for (calories in this) {
        best.add(calories)
        if (best.size > n)
            best.poll()
    }
    return best.sum()
}

fun List<Int>.linearCaloriesOfTop(n: Int): Int {
    fun findTopN(n: Int, element: List<Int>): List<Int> {
        if (element.size == n) return element
        val x = element.random()
        val small = element.filter { it < x }
        val equal = element.filter { it == x }
        val big = element.filter { it > x }
        if (big.size >= n) return findTopN(n, big)
        if (equal.size + big.size >= n) return (equal + big).takeLast(n)
        return findTopN(n - equal.size - big.size, small) + equal + big
    }
    return findTopN(n, this).sum()
}