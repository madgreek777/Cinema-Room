const val bigPrice = 10
const val smallPrice = 8

object dataClass {
    var ticketСounter = 0
    var currentIncome = 0
}

fun generateCinemaRoom(): MutableList<MutableList<String>> {
    print("Enter the number of rows:\n> ")
    val row = readln().toInt()
    print("Enter the number of seats in each row:\n> ")
    val seats = readln().toInt()
    return MutableList(row) {MutableList(seats) { "S"} }
}

fun printCinemaRoom(mut: MutableList<MutableList<String>>) {
    print("\nCinema:\n ")
    for (i in 1..mut[0].size) print(if (i == mut[0].size) " $i\n" else " $i")
    for (i in 1..mut.size) println("$i ${mut[i - 1].joinToString(" ")}")
}

fun seatChoise(cinemaRoom: MutableList<MutableList<String>>) {
    print("\nEnter a row number:\n> ")
    val myRow = readln().toInt() - 1
    print("Enter a seat number in that row:\n> ")
    val myPlace = readln().toInt() - 1

    if (myRow + 1 > cinemaRoom[0].size || myPlace + 1 > cinemaRoom.size) {
        println("\nWrong input!")
        seatChoise(cinemaRoom)
    } else {
        if (cinemaRoom[myRow][myPlace] == "B") {
            println("\nThat ticket has already been purchased!")
            seatChoise(cinemaRoom)
        } else {
            cinemaRoom[myRow][myPlace] = "B"
            val totalSeats = cinemaRoom.size * cinemaRoom[0].size
            val ticket = if (totalSeats <= 60) { bigPrice
            } else { if (myRow + 1 > cinemaRoom.size / 2) smallPrice else bigPrice }
            println("Ticket price: $$ticket")
            dataClass.ticketСounter++
            dataClass.currentIncome += ticket
        }
    }
}

fun statisticsCinema(cinemaRoom: MutableList<MutableList<String>>) {
    val totalSeats = cinemaRoom.size * cinemaRoom[0].size
    val row = cinemaRoom.size
    val seats = cinemaRoom[0].size
    val totalIncome = (if (totalSeats <= 60) totalSeats * 10 else {
        ((row / 2 * seats) * bigPrice) + (row - row/2) * seats * smallPrice
    })

    println("\nNumber of purchased tickets: ${dataClass.ticketСounter}")
    println("Percentage: ${"%.2f".format((dataClass.ticketСounter * 100.00 / totalSeats.toDouble()))}%")
    println("Current income: \$${dataClass.currentIncome}")
    println("Total income: \$$totalIncome")
}

fun menuChoise(cinemaRoom: MutableList<MutableList<String>>) {
    println("\n" +
            "1. Show the seats\n" +
            "2. Buy a ticket\n" +
            "3. Statistics\n" +
            "0. Exit")

    when (readln().first()) {
        '1' -> {
            printCinemaRoom(cinemaRoom)
            menuChoise(cinemaRoom)
        }
        '2' -> {
            seatChoise(cinemaRoom)
            menuChoise(cinemaRoom)
        }
        '3' -> {
            statisticsCinema(cinemaRoom)
            menuChoise(cinemaRoom)
        }
        '0' -> return
    }
}


fun main() {
    val cinemaRoom = generateCinemaRoom()
    menuChoise(cinemaRoom)
}
