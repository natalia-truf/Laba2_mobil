package port

class BerthChief(
    private val berth: Berth,
    private var mediator: PortMediator? = null
) {
    fun getBerth() = berth

    fun setMediator(mediator: PortMediator) {
        this.mediator = mediator
    }

    fun checkBerth(ship: Ship) {
        val allowed = berth.canAccept(ship)
        val answer = if (allowed) "можно" else "нельзя"
        println("Шаг 3. Начальник причала №${berth.getNumber()}: $answer.")
        mediator?.reportBerthingResult(ship, berth, allowed)
    }
}