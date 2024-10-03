class NoteScreen(private val archive: Archive) : BaseScreen() {
    private val menuManager = MenuManager(scanner)

    fun run() {
        val menuItems = listOf(
            "выбрать заметку",
            "создать заметку",
            "выход"
        )
        val actionMap = mapOf(
            "1" to { choseItem("заметку") { choseNote() } },
            "2" to { createItem("заметку") { createNote() } }
        )
        menuManager.runMenu(menuItems, actionMap)
    }

    private fun choseNote() {
        val chosenTitle = getUserInput("Выберите заметку, вписав её название")
        val selectedNote = archive.content.find { it.title == chosenTitle }
        if (selectedNote != null) {
            println("Содержимое заметки '${selectedNote.title}':")
            println(selectedNote.content)
        } else {
            createItem("заметку", chosenTitle) { createNote(chosenTitle) }
        }
    }

    private fun createNote(title: String = "") {
        val newNoteTitle = title.ifBlank { getUserInput("Введите название для новой заметки") }
        if (newNoteTitle.isBlank()) {
            println("Название заметки не может быть пустым. Пожалуйста, попробуйте снова.")
            return
        }
        val newNoteContent = getUserInput("Введите содержимое для новой заметки")
        val newNote = Note(newNoteTitle, newNoteContent)
        archive.content.add(newNote)
        println("Заметка '$newNoteTitle' успешно создана.")
    }

    private fun choseItem(itemType: String, action: () -> Unit) {
        if (archive.content.isEmpty()) {
            println("Заметки отсутствуют.")
            return
        }
        printList(archive.content) { note -> "${note.title}: ${note.content}" }
        action()
    }

    private fun createItem(itemType: String, title: String = "", action: (String) -> Unit) {
        val createNew = confirmAction("Такой $itemType не существует. Вы хотите создать новую $itemType с названием '$title'? (да/нет)")
        if (createNew) {
            action(title)
        } else {
            println("Операция отменена.")
        }
    }
}