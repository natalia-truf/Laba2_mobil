package port

class Ferry(name: String, displacement: Double) : Ship(name, displacement, ShipType.FERRY)

class Tanker(name: String, displacement: Double) : Ship(name, displacement, ShipType.TANKER)

class Trawler(name: String, displacement: Double) : Ship(name, displacement, ShipType.TRAWLER)