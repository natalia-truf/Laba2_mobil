package port

interface PortMediator {
    fun requestBerthing(ship: Ship, berth: Berth)
    fun reportBerthingResult(ship: Ship, berth: Berth, allowed: Boolean)
}