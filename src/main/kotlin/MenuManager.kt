import java.util.Scanner

class MenuManager(private val scanner: Scanner) {
    fun runMenu(menuItems: List<String>, actionMap: Map<String, () -> Unit>) {
        while (true) {
            printMenu(menuItems)
            val command = scanner.nextLine()
            if (command == (menuItems.size).toString()) {
                break
            }
            actionMap[command]?.invoke() ?: println("Пожалуйста, введите корректную команду")
        }
    }

    private fun printMenu(menuItems: List<String>) {
        menuItems.forEachIndexed { index, item ->
            println("${index + 1} - $item")
        }
    }
}