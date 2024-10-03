import java.util.Scanner

abstract class BaseScreen {
    val scanner = Scanner(System.`in`)

    protected fun getUserInput(prompt: String): String {
        println(prompt)
        return scanner.nextLine()
    }

    protected fun confirmAction(prompt: String): Boolean {
        println(prompt)
        val response = scanner.nextLine()
        return response.equals("да", ignoreCase = true)
    }

    protected fun <T> printList(items: List<T>, itemToString: (T) -> String) {
        items.forEachIndexed { index, item ->
            println("${index + 1}: ${itemToString(item)}")
        }
    }
}

class Archive(val title: String, val content: MutableList<Note> = mutableListOf())

class Note(val title: String, val content: String)