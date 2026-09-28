package port

class Menu {
    private val port = Port("Мурманский порт")
    private val ships = mutableListOf<Ship>()
    private lateinit var dispatcher: Dispatcher

    fun run() {
        initPort()

        println("Добро пожаловать в систему управления морским портом!")

        var choice = 0
        while (choice != 6) {
            printMenu()
            val input = readlnOrNull() ?: return
            choice = input.trim().toIntOrNull() ?: 0

            when (choice) {
                1 -> addShip()
                2 -> showShips()
                3 -> showBerths()
                4 -> requestBerthing()
                5 -> releaseBerth()
                6 -> println("Завершение работы.")
                else -> println("Неверный выбор. Попробуйте снова.")
            }
        }
    }

    private fun initPort() {
        port.addBerth(Berth(1, 50000.0))
        port.addBerth(Berth(2, 150000.0))
        port.addBerth(Berth(3, 8000.0))

        dispatcher = Dispatcher("Диспетчер", port)
        port.addDispatcher(dispatcher)

        val chief1 = BerthChief(port.getBerth(1)!!)
        val chief2 = BerthChief(port.getBerth(2)!!)
        val chief3 = BerthChief(port.getBerth(3)!!)

        chief1.setMediator(dispatcher)
        chief2.setMediator(dispatcher)
        chief3.setMediator(dispatcher)

        port.addChief(chief1)
        port.addChief(chief2)
        port.addChief(chief3)
    }

    private fun printMenu() {
        println()
        println("Меню:")
        println("1. Добавить судно")
        println("2. Показать список судов")
        println("3. Показать список причалов")
        println("4. Запросить швартовку")
        println("5. Освободить причал")
        println("6. Выйти")
    }

    private fun addShip() {
        println("Введите имя судна:")
        val name = readlnOrNull()?.trim().orEmpty()
        if (name.isEmpty()) {
            println("Имя не может быть пустым.")
            return
        }
        if (ships.any { it.name == name }) {
            println("Судно с таким именем уже существует.")
            return
        }

        println("Выберите тип судна:")
        println("1. Паром")
        println("2. Танкер")
        println("3. Траулер")
        val type = when (readlnOrNull()?.toIntOrNull()) {
            1 -> ShipType.FERRY
            2 -> ShipType.TANKER
            3 -> ShipType.TRAWLER
            else -> {
                println("Неверный ввод, выбран паром по умолчанию.")
                ShipType.FERRY
            }
        }

        println("Введите водоизмещение (100–500000 тонн):")
        val displacement = readlnOrNull()?.trim()?.toDoubleOrNull()
        if (displacement == null || displacement !in 100.0..500000.0) {
            println("Неверное водоизмещение.")
            return
        }

        val ship = ShipFactory.createShip(type, name, displacement)
        ships.add(ship)
        println("Судно '$name' (${type.name.lowercase()}) добавлено.")
    }

    private fun showShips() {
        if (ships.isEmpty()) {
            println("Список судов пуст.")
            return
        }
        println("    Список судов    ")
        ships.forEachIndexed { index, ship ->
            println("${index + 1}. ${ship.name} | тип: ${ship.type.name.lowercase()} | водоизмещение: ${ship.displacement}")
        }
    }

    private fun showBerths() {
        val berths = port.getAllBerths()
        if (berths.isEmpty()) {
            println("Список причалов пуст.")
            return
        }
        println("    Список причалов    ")
        berths.forEach { berth ->
            val shipInfo = berth.currentShip?.let { " | судно: ${it.name}" } ?: ""
            println("Причал №${berth.getNumber()} | состояние: ${berth.getStateName()} | " +
                    "грузоподъёмность: ${berth.capacity}$shipInfo")
        }
    }

    private fun requestBerthing() {
        if (ships.isEmpty()) {
            println("Список судов пуст, сначала добавьте судно.")
            return
        }

        println("Введите имя судна:")
        val shipName = readlnOrNull()?.trim().orEmpty()
        val ship = ships.find { it.name == shipName }
        if (ship == null) {
            println("Судно не найдено.")
            return
        }

        val oldBerth = port.getBerthByShip(ship)
        if (oldBerth != null) {
            println("Судно '$shipName' покидает причал №${oldBerth.getNumber()}")
            oldBerth.release()
        }

        println("Введите номер причала:")
        val berthNumber = readlnOrNull()?.trim()?.toIntOrNull()
        if (berthNumber == null) {
            println("Неверный номер.")
            return
        }

        val berth = port.getBerth(berthNumber)
        if (berth == null) {
            println("Причал не найден.")
            return
        }

        println()
        println("    Швартовка судна '$shipName' к причалу №${berth.getNumber()}    ")
        println("До: причал №${berth.getNumber()} — ${berth.getStateName()}")
        println()
        ship.requestBerthing(dispatcher, berth)
        println()
        println("После: причал №${berth.getNumber()} — ${berth.getStateName()}")
    }

    private fun releaseBerth() {
        println("Введите номер причала:")
        val berthNumber = readlnOrNull()?.trim()?.toIntOrNull()
        if (berthNumber == null) {
            println("Неверный номер.")
            return
        }

        val berth = port.getBerth(berthNumber)
        if (berth == null) {
            println("Причал не найден.")
            return
        }

        if (!berth.isOccupied()) {
            println("Причал №$berthNumber уже свободен.")
            return
        }

        berth.release()
        println("Причал №$berthNumber освобождён.")
    }
}