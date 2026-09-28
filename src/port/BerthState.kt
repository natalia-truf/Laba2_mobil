package port

interface BerthState {
    val name: String
    fun canAccept(berth: Berth, ship: Ship): Boolean
    fun moor(berth: Berth, ship: Ship)
    fun release(berth: Berth)
}

object FreeState : BerthState {
    override val name: String = "Свободен"

    override fun canAccept(berth: Berth, ship: Ship): Boolean {
        return ship.displacement <= berth.capacity
    }

    override fun moor(berth: Berth, ship: Ship) {
        berth.currentShip = ship
        berth.changeState(OccupiedState)
        println("  Причал №${berth.getNumber()}: судно '${ship.name}' пришвартовано")
    }

    override fun release(berth: Berth) {
        println("  Причал №${berth.getNumber()} уже свободен")
    }
}

object OccupiedState : BerthState {
    override val name: String = "Занят"

    override fun canAccept(berth: Berth, ship: Ship): Boolean = false

    override fun moor(berth: Berth, ship: Ship) {
        println("  Причал №${berth.getNumber()} занят, швартовка невозможна")
    }

    override fun release(berth: Berth) {
        println("  Причал №${berth.getNumber()}: судно '${berth.currentShip?.name}' покинуло причал")
        berth.currentShip = null
        berth.changeState(FreeState)
    }
}