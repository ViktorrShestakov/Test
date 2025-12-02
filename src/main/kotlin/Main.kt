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
object Auto {

    val cars: MutableMap<CarModel, MutableSet<Car>> = mutableMapOf(
        CarModel.AUDI to mutableSetOf(
            Car(CarModel.AUDI, "Audi RS7", 27500000, "WAUZZZ4G7FN123456", "Самая лучшая машина в мире!"),
            Car(CarModel.AUDI, "Audi A6", 4500000, "WAUZZZ4G7FN654321", "Мой вариант, если Илья не возьмет меня на работу"),
        ),
        CarModel.BMW to mutableSetOf(
            Car(CarModel.BMW, "BMW M5", 6700000, "GVMESP3V8FN123456", "Говно"),
            Car(CarModel.BMW, "BMW X5", 5200000, "GVMESP3V8FN999999", "Забава"),
        ),
        CarModel.MERCEDES to mutableSetOf(
            Car(CarModel.MERCEDES, "Mercedes E200", 12500000, "HDGFDF1E7FN123456", "Говно"),
            Car(CarModel.MERCEDES, "Mercedes S500", 18500000, "HDGFDF1E7FN555555", "Сойдет"),
        ),
        CarModel.VOLKSWAGEN to mutableSetOf(
            Car(CarModel.VOLKSWAGEN, "Volkswagen Golf 2", 50000, "RWETYU5G7FN123456", "Красненькая"),
            Car(CarModel.VOLKSWAGEN, "Volkswagen Polo", 980000, "RWETYU5G7FN777777", "..."),
        )
    )
}

fun addCar(args: List<String>) {
    val model = CarModel.valueOf(args[1].uppercase())
    val name = args[2]
    val price = args[3].toInt()
    val vin = args[4]
    val description = args.getOrElse(5) { "Описание_отсутствует" }

    Auto.cars.getOrPut(model) { mutableSetOf() }.add(Car(model, name, price, vin, description))
    println("Авто добавлено")
}

fun deleteCar(args: List<String>) {
    val model = CarModel.valueOf(args[1].uppercase())
    val vin = args[2]

    Auto.cars.getOrPut(model) { mutableSetOf() }.removeIf { it.vin == vin }

    println("Авто удалено")
}

fun updateCar(args: List<String>) {
    val model = CarModel.valueOf(args[1].uppercase())
    val vin = args[2]
    val newPrice = args[3].toInt()

    Auto.cars.getOrPut(model) { mutableSetOf() }
        .find { it.vin == vin }!!.price = newPrice

    println("Цена обновлена")
}

fun showCars(args: List<String>) {
    val model = CarModel.valueOf(args[1].uppercase())
    Auto.cars.getOrPut(model) { mutableSetOf() }.sortedBy { it.price }.forEach { println(it) }
}

fun showRange(args: List<String>) {
    val min = args[1].toInt()
    val max = args[2].toInt()

    Auto.cars.values.flatten().filter { it.price in min..max }.forEach { println(it) }
}

fun main() {
    var command: String=""
    println("""
        Команды: 
        add модель название цена вин_номер описание 
        del модель вин_номер
        update моедль вин_номер новая_цена 
        show модель 
        range минимум максимум 
        exit
        """.trimIndent())

    do {
        val input = readln().trim()
        if (input.isEmpty()) continue

        val args = input.split(" ")
        command = args[0].lowercase()

        when (command) {
            "add" -> addCar(args)
            "del" -> deleteCar(args)
            "update" -> updateCar(args)
            "show" -> showCars(args)
            "range" -> showRange(args)
            "exit" -> println("Завершение работы")
            else -> println("Неизвестная команда.")
        }

    } while (command != "exit")
}
