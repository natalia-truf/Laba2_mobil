package port

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BerthTest {

    @Test
    @Order(1)
    fun `неверный номер причала вызывает ошибку`() {
        assertThrows(IllegalArgumentException::class.java) {
            Berth(0, 50000.0)
        }
        assertThrows(IllegalArgumentException::class.java) {
            Berth(11, 50000.0)
        }
    }

    @Test
    @Order(2)
    fun `неверная грузоподъёмность вызывает ошибку`() {
        assertThrows(IllegalArgumentException::class.java) {
            Berth(1, 100.0)
        }
        assertThrows(IllegalArgumentException::class.java) {
            Berth(1, 700000.0)
        }
    }
}