package port

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

private class TestMediator : PortMediator {
    var calls = 0
    var lastAllowed: Boolean? = null

    override fun requestBerthing(ship: Ship, berth: Berth) { }

    override fun reportBerthingResult(ship: Ship, berth: Berth, allowed: Boolean) {
        calls++
        lastAllowed = allowed
    }
}

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BerthChiefTest {

    @Test
    @Order(1)
    fun `уведомление посредника о разрешении`() {
        val berth = Berth(1, 50000.0)
        val chief = BerthChief(berth)
        val mediator = TestMediator()
        chief.setMediator(mediator)

        chief.checkBerth(Ferry("Паром-1", 25000.0))

        assertEquals(1, mediator.calls)
        assertEquals(true, mediator.lastAllowed)
    }

    @Test
    @Order(2)
    fun `уведомление посредника об отказе`() {
        val berth = Berth(3, 8000.0)
        val chief = BerthChief(berth)
        val mediator = TestMediator()
        chief.setMediator(mediator)

        chief.checkBerth(Tanker("Танкер-1", 120000.0))

        assertEquals(1, mediator.calls)
        assertEquals(false, mediator.lastAllowed)
    }

    @Test
    @Order(3)
    fun `без посредника вызов не падает`() {
        val berth = Berth(1, 50000.0)
        val chief = BerthChief(berth)
        chief.checkBerth(Ferry("Паром-1", 25000.0))
    }
}