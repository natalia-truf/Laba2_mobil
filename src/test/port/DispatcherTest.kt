package port

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DispatcherTest {

    @Test
    @Order(1)
    fun `диспетчер швартует судно при разрешении`() {
        val port = Port("Тест")
        val berth = Berth(1, 50000.0)
        port.addBerth(berth)
        val dispatcher = Dispatcher("Диспетчер", port)

        val ship = Ferry("Паром-1", 25000.0)
        dispatcher.reportBerthingResult(ship, berth, true)

        assertTrue(berth.isOccupied())
        assertEquals(ship, berth.currentShip)
    }

    @Test
    @Order(2)
    fun `диспетчер не швартует судно при отказе`() {
        val port = Port("Тест")
        val berth = Berth(1, 50000.0)
        port.addBerth(berth)
        val dispatcher = Dispatcher("Диспетчер", port)

        dispatcher.reportBerthingResult(Ferry("Паром-1", 25000.0), berth, false)

        assertFalse(berth.isOccupied())
    }

    @Test
    @Order(3)
    fun `диспетчер передаёт запрос начальнику причала`() {
        val port = Port("Тест")
        val berth = Berth(1, 50000.0)
        port.addBerth(berth)

        val chief = BerthChief(berth)
        val dispatcher = Dispatcher("Диспетчер", port)
        chief.setMediator(dispatcher)
        port.addChief(chief)

        val ship = Ferry("Паром-1", 25000.0)
        dispatcher.requestBerthing(ship, berth)

        assertTrue(berth.isOccupied())
        assertEquals(ship, berth.currentShip)
    }

    @Test
    @Order(4)
    fun `если у причала нет начальника — отказ`() {
        val port = Port("Тест")
        val berth = Berth(1, 50000.0)
        port.addBerth(berth)
        val dispatcher = Dispatcher("Диспетчер", port)

        var received: Boolean? = null
        val ship = object : Ship("Тест", 25000.0, ShipType.FERRY) {
            override fun onDecision(berth: Berth, allowed: Boolean) {
                received = allowed
            }
        }

        dispatcher.requestBerthing(ship, berth)

        assertFalse(berth.isOccupied())
        assertEquals(false, received)
    }
}