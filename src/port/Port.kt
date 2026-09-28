package port

class Port(val name: String) {
    private val berths = mutableListOf<Berth>()
    private val chiefs = mutableListOf<BerthChief>()
    private val dispatchers = mutableListOf<Dispatcher>()

    init {
        require(name.isNotBlank()) { "Название порта не может быть пустым" }
    }

    fun addBerth(berth: Berth) { berths.add(berth) }
    fun addChief(chief: BerthChief) { chiefs.add(chief) }
    fun addDispatcher(dispatcher: Dispatcher) { dispatchers.add(dispatcher) }

    fun getBerth(number: Int) = berths.firstOrNull { it.getNumber() == number }
    fun getChief(number: Int) = chiefs.firstOrNull { it.getBerth().getNumber() == number }
    fun getAllBerths() = berths.toList()
    fun getBerthByShip(ship: Ship) = berths.firstOrNull { it.currentShip === ship }
}