package port

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ShipTest {

    @Test
    @Order(1)
    fun `создание судна с корректными данными`() {
        val ship = Ferry("Паром-1", 25000.0)
        assertEquals("Паром-1", ship.name)
        assertEquals(25000.0, ship.displacement)
        assertEquals(ShipType.FERRY, ship.type)
    }

    @Test
    @Order(2)
    fun `пустое имя вызывает ошибку`() {
        assertThrows(IllegalArgumentException::class.java) {
            Ferry("", 25000.0)
        }
    }

    @Test
    @Order(3)
    fun `водоизмещение меньше минимума вызывает ошибку`() {
        assertThrows(IllegalArgumentException::class.java) {
            Ferry("Паром-1", 50.0)
        }
    }

    @Test
    @Order(4)
    fun `водоизмещение больше максимума вызывает ошибку`() {
        assertThrows(IllegalArgumentException::class.java) {
            Ferry("Паром-1", 600000.0)
        }
    }

    @Test
    @Order(5)
    fun `типы судов устанавливаются верно`() {
        assertEquals(ShipType.FERRY, Ferry("F", 1000.0).type)
        assertEquals(ShipType.TANKER, Tanker("T", 1000.0).type)
        assertEquals(ShipType.TRAWLER, Trawler("Tr", 1000.0).type)
    }
}