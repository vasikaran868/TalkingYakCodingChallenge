
fun main() {
    println("Enter Upper right coordinates:")
    val inp1 = getMaxXY()

    val inputList = inp1.split(" ").map { it.toInt() }
    val maxX = inputList[0]
    val maxY = inputList[1]

    println("Enter Robot Position: ")
    val inp2 = getRobotPosition(maxX, maxY)

    val position = inp2.split(" ")
    var x = position[0].toInt()
    var y = position[1].toInt()
    var direction = position[2].single()

    println("Enter Instructions: ")
    val inp3 = getInstructions()
    for (instruction in inp3) {
        when (instruction) {
            'L' -> direction = turnLeft(direction)
            'R' -> direction = turnRight(direction)
            'M' -> {
                val (newX, newY) = moveForward(x, y, direction)
                if (isValidPosition(newX, newY, maxX, maxY)) {
                    x = newX
                    y = newY
                }
            }
        }
    }

    println("$x $y $direction")
}

fun getInstructions(): String {
    var instruction: String? = readLine()

    while (instruction == null || !isValidInstruction(instruction)) {
        println("Enter a valid Instruction: ")
        instruction = readLine()
    }

    return instruction

}

fun isValidInstruction(instruction: String): Boolean {
    val validChars = setOf('M', 'L', 'R')
    return instruction.all { it in validChars }
}

fun getRobotPosition(maxX: Int, maxY: Int): String {
    var posTemp: String? = readLine()

    while (posTemp == null || !isValidPosition(posTemp, maxX, maxY)) {
        println("Enter a valid Robot position (format: X Y Z): ")
        posTemp = readLine()
    }
    return posTemp
}

fun isValidPosition(input: String, maxX: Int, maxY: Int): Boolean {
    val coordinates = input.split(" ")
    if (coordinates.size != 3) return false
    return try {
        val x = coordinates[0].toInt()
        val y = coordinates[1].toInt()
        if (coordinates[2].single() !in setOf('N', 'E', 'W', 'S')){
            throw Exception()
        }
        if (!isValidPosition(x, y, maxX, maxY)){
            throw Exception()
        }
        true
    } catch (e: Exception) {
        false
    }
}

fun getMaxXY(): String {
    var maxTemp: String? = readLine()

    while (maxTemp == null || !isValidCoordinates(maxTemp)) {
        println("Enter a valid upper right coordinates (format: X Y): ")
        maxTemp = readLine()
    }

    return maxTemp
}

fun isValidCoordinates(input: String): Boolean {
    val coordinates = input.split(" ")
    if (coordinates.size != 2) return false
    return try {
        val x = coordinates[0].toInt()
        val y = coordinates[1].toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun turnLeft(currentDirection: Char): Char {
    return when (currentDirection) {
        'N' -> 'W'
        'W' -> 'S'
        'S' -> 'E'
        'E' -> 'N'
        else -> currentDirection
    }
}

fun turnRight(currentDirection: Char): Char {
    return when (currentDirection) {
        'N' -> 'E'
        'E' -> 'S'
        'S' -> 'W'
        'W' -> 'N'
        else -> currentDirection
    }
}

fun moveForward(x: Int, y: Int, direction: Char): Pair<Int, Int> {
    return when (direction) {
        'N' -> Pair(x, y + 1)
        'E' -> Pair(x + 1, y)
        'S' -> Pair(x, y - 1)
        'W' -> Pair(x - 1, y)
        else -> Pair(x, y)
    }
}

fun isValidPosition(x: Int, y: Int, maxX: Int, maxY: Int): Boolean {
    return x >= 0 && x <= maxX && y >= 0 && y <= maxY
}