package port

object ShipFactory {
    fun createShip(type: ShipType, name: String, displacement: Double): Ship {
        return when (type) {
            ShipType.FERRY -> Ferry(name, displacement)
            ShipType.TANKER -> Tanker(name, displacement)
            ShipType.TRAWLER -> Trawler(name, displacement)
        }
    }
}