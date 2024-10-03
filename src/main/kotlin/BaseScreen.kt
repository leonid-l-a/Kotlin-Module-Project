import java.util.Scanner

open class BaseScreen {
    protected val scanner = Scanner(System.`in`)

    protected fun getUserInput(prompt: String): String {
        println(prompt)
        return scanner.nextLine()
    }

    protected fun confirmAction(prompt: String): Boolean {
        while (true) {
            println(prompt)
            val input = scanner.nextLine().trim().lowercase()
            if (input == "да") return true
            if (input == "нет") return false
            println("Пожалуйста, введите 'да' или 'нет'.")
        }
    }

    protected fun <T> printList(items: List<T>, itemToString: (T) -> String) {
        items.forEachIndexed { index, item ->
            println("${index + 1} - ${itemToString(item)}")
        }
    }
}

data class Archive(val title: String, val content: MutableList<Note> = mutableListOf())

data class Note(val title: String, val content: String)