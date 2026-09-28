package port

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BerthStateTest {

    @Test
    @Order(1)
    fun `новый причал в состоянии Свободен`() {
        val berth = Berth(1, 50000.0)
        assertFalse(berth.isOccupied())
        assertEquals("Свободен", berth.getStateName())
    }

    @Test
    @Order(2)
    fun `свободный причал принимает судно по водоизмещению`() {
        val berth = Berth(1, 50000.0)
        assertTrue(berth.canAccept(Ferry("Паром-1", 25000.0)))
    }

    @Test
    @Order(3)
    fun `свободный причал не принимает слишком тяжёлое судно`() {
        val berth = Berth(3, 8000.0)
        assertFalse(berth.canAccept(Tanker("Танкер-1", 120000.0)))
    }

    @Test
    @Order(4)
    fun `швартовка переводит причал в состояние Занят`() {
        val berth = Berth(1, 50000.0)
        val ship = Ferry("Паром-1", 25000.0)
        berth.moorShip(ship)
        assertTrue(berth.isOccupied())
        assertEquals("Занят", berth.getStateName())
        assertEquals(ship, berth.currentShip)
    }

    @Test
    @Order(5)
    fun `занятый причал не принимает другое судно`() {
        val berth = Berth(1, 50000.0)
        berth.moorShip(Ferry("Паром-1", 25000.0))
        assertFalse(berth.canAccept(Ferry("Паром-2", 25000.0)))
    }

    @Test
    @Order(6)
    fun `освобождение возвращает причал в состояние Свободен`() {
        val berth = Berth(1, 50000.0)
        berth.moorShip(Ferry("Паром-1", 25000.0))
        berth.release()
        assertFalse(berth.isOccupied())
        assertEquals("Свободен", berth.getStateName())
    }
}