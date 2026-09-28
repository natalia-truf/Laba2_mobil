package port

abstract class Ship(
    val name: String,
    val displacement: Double,
    val type: ShipType
) {
    init {
        require(name.isNotBlank()) { "Имя судна не может быть пустым" }
        require(displacement in 100.0..500000.0) {
            "Водоизмещение должно быть в диапазоне 100–500000 тонн"
        }
    }

    fun requestBerthing(mediator: PortMediator, berth: Berth) {
        println("Шаг 1. Судно '$name' запрашивает разрешение у диспетчера.")
        mediator.requestBerthing(this, berth)
    }

    open fun onDecision(berth: Berth, allowed: Boolean) {
        if (!allowed) {
            println("Судно '$name' не пришвартовано.")
        }
    }

    override fun toString(): String = "$name (${type.name.lowercase()})"
}