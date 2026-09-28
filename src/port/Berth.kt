package port

class Berth(
    private val number: Int,
    val capacity: Double
) {
    var currentShip: Ship? = null
        internal set

    private var state: BerthState = FreeState

    init {
        require(number in 1..10) { "Номер причала: 1–10" }
        require(capacity in 500.0..600000.0) { "Грузоподъёмность: 500–600000 тонн" }
    }

    fun getNumber() = number
    fun getStateName() = state.name
    fun isOccupied() = state === OccupiedState

    internal fun changeState(newState: BerthState) {
        state = newState
    }

    fun canAccept(ship: Ship): Boolean = state.canAccept(this, ship)
    fun moorShip(ship: Ship) = state.moor(this, ship)
    fun release() = state.release(this)

    override fun toString(): String = "Причал №$number (${state.name})"
}