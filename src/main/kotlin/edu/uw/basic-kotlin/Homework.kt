package edu.uw.basickotlin

// write a "whenFn" that takes an arg of type "Any" and returns a String
fun whenFn(arg: Any): String {
    when (arg) {
        is String -> {
            return if (arg == "Hello") "world" else "Say what?"
        }
        is Int -> {
            when (arg) {
                0 -> return "zero"
                1 -> return "one"
                in 2..10 -> return "low number"
                else -> return "a number"
            }
        }
        else -> return "I don't understand"
    }
}

// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add(val1: Int, val2: Int): Int {
    return val1 + val2
}

// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub(lhs: Int, rhs: Int): Int = lhs - rhs

// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp(lhs: Int, rhs: Int, op: (Int, Int) -> Int) = op(lhs, rhs)

// write a class "Person" with first name, last name and age
class Person(var firstName: String, var lastName: String, var age: Int) {
    val debugString : String
        get() = "[Person firstName:${this.firstName} lastName:${this.lastName} age:${this.age}]"
}

// write a class "Money"
class Money(var amount: Int, var currency: String) {
    init {
        if (amount < 0)
            throw IllegalArgumentException("no negative amounts")
        if (currency !in listOf("USD", "EUR", "CAN", "GBP"))
            throw IllegalArgumentException("must be in USD, EUR, CAN, or GBP")
    }

    fun convert(newCurr: String): Money {
        if (newCurr !in listOf("USD", "GBP", "CAN", "EUR"))
            throw IllegalArgumentException("must be in USD, EUR, CAN, or GBP")
        return if (newCurr === currency)
            Money(amount, currency)
        else when (Pair(currency, newCurr)) {
            Pair("USD", "GBP") -> Money((this.amount * .5).toInt(), "GBP")
            Pair("USD", "EUR") -> Money((this.amount * 1.5).toInt(), "EUR")
            Pair("USD", "CAN") -> Money((this.amount * 1.25).toInt(), "CAN")
            Pair("GBP", "USD") -> Money((this.amount * 2).toInt(), "USD")
            Pair("EUR", "USD") -> Money((this.amount * .75).toInt(), "USD")
            Pair("CAN", "USD") -> Money((this.amount * 5 / 4).toInt(), "USD")
            else -> this.convert("USD").convert(newCurr)
        }
    }

    operator fun plus(other: Money): Money =
        Money(this.amount + (other.convert(this.currency)).amount, this.currency)
}