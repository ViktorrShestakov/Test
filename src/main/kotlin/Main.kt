enum class CarModel {
    AUDI,
    BMW,
    MERCEDES,
    VOLKSWAGEN
}

data class Car(
    val model: CarModel,
    val fullName: String,
    var price: Int,
    val vin: String,
    val description: String = "Описание отсутствует"
)

fun main() {

    val cars: MutableMap<CarModel, MutableSet<Car>> = mutableMapOf(
        CarModel.AUDI to mutableSetOf(
            Car(CarModel.AUDI, "Audi RS7", 27500000, "WAUZZZ4G7FN123456", "Самая лучшая машина в мире!")
        ),
        CarModel.BMW to mutableSetOf(
            Car(CarModel.BMW, "BMW M5", 6700000, "GVMESP3V8FN123456", "Говно")
        ),
        CarModel.MERCEDES to mutableSetOf(
            Car(CarModel.MERCEDES, "Mercedes E200", 12500000, "HDGFDF1E7FN123456", "Говно")
        ),
        CarModel.VOLKSWAGEN to mutableSetOf(
            Car(CarModel.VOLKSWAGEN, "Volkswagen Golf 2", 50000, "RWETYU5G7FN123456", "Красненькая")
        )
    )

    var input: Int

    do {
        println(
            """
            Выберите действие:
            1 — Добавить авто
            2 — Удалить авто
            3 — Изменить цену авто
            4 — Показать авто по марке (сортировка по цене)
            5 — Показать авто в диапазоне цен
            6 — Выход
        """.trimIndent()
        )

        print("Введите число: ")
        input = readln().toInt()

        when (input) {

            1 -> {
                println("Введите модель (AUDI, BMW, MERCEDES, VOLKSWAGEN):")
                val model = CarModel.valueOf(readln().uppercase())
                println("Введите полное имя авто:")
                val name = readln()
                println("Введите цену:")
                val price = readln().toInt()
                println("Введите VIN номер:")
                val vin = readln()
                println("Введите описание (если нужно):")
                val description = readln()

                val car = Car(model, name, price, vin, description)
                cars.getOrPut(model) { mutableSetOf() }.add(car)

                println("Авто добавлено")
            }

            2 -> {
                println("Введите модель:")
                val model = CarModel.valueOf(readln().uppercase())
                println("Введите VIN номер авто:")
                val vin = readln()
                val set = cars.getOrPut(model) { mutableSetOf() }
                val removed = set.removeIf { it.vin == vin }
                if (removed) println("Авто успешно удалено.")
                else println("Авто с таким VIN номером не найдено.")
            }

            3 -> {
                println("Введите модель:")
                val model = CarModel.valueOf(readln().uppercase())
                println("Введите VIN номер:")
                val vin = readln()
                val set = cars.getOrPut(model) { mutableSetOf() }
                val car = set.find { it.vin == vin }
                if (car == null) println("Авто не найдено.")
                else {
                    println("Введите новую цену:")
                    car.price = readln().toInt()
                    println("Цена обновлена!")
                }
            }

            4 -> {
                println("Введите модель:")
                val model = CarModel.valueOf(readln().uppercase())
                val list = cars.getOrPut(model) { mutableSetOf() }.sortedBy { it.price }
                list.forEach { println(it) }
            }

            5 -> {
                println("Введите минимальную цену:")
                val min = readln().toInt()
                println("Введите максимальную цену:")
                val max = readln().toInt()
                val list = cars.values.flatten().filter { it.price in min..max }
                list.forEach { println(it) }
            }

            6 -> println("Выход из программы")

            else -> println("Команда введена неверно. Попробуйте снова.")
        }

    } while (input != 6)


}
