package port

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ShipFactoryTest {

    @Test
    @Order(1)
    fun `фабрика создаёт паром`() {
        val ship = ShipFactory.createShip(ShipType.FERRY, "Паром-1", 25000.0)
        assertTrue(ship is Ferry)
    }

    @Test
    @Order(2)
    fun `фабрика создаёт танкер`() {
        val ship = ShipFactory.createShip(ShipType.TANKER, "Танкер-1", 120000.0)
        assertTrue(ship is Tanker)
    }

    @Test
    @Order(3)
    fun `фабрика создаёт траулер`() {
        val ship = ShipFactory.createShip(ShipType.TRAWLER, "Траулер-1", 5000.0)
        assertTrue(ship is Trawler)
    }
}