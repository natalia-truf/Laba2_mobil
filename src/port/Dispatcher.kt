package port

class Dispatcher(
    val name: String,
    private val port: Port
) : PortMediator {

    override fun requestBerthing(ship: Ship, berth: Berth) {
        println("Шаг 2. Диспетчер передаёт запрос начальнику причала №${berth.getNumber()}.")

        val chief = port.getChief(berth.getNumber())
        if (chief == null) {
            println("У причала №${berth.getNumber()} нет начальника.")
            ship.onDecision(berth, false)
            return
        }

        chief.checkBerth(ship)
    }

    override fun reportBerthingResult(ship: Ship, berth: Berth, allowed: Boolean) {
        if (allowed) {
            println("Шаг 4. Диспетчер разрешает швартовку.")
            berth.moorShip(ship)
        } else {
            println("Шаг 4. Диспетчер запрещает швартовку.")
        }

        ship.onDecision(berth, allowed)
    }
}