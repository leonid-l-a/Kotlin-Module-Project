class NoteScreen(private val archive: Archive) : BaseScreen() {
    private val menuManager = MenuManager(scanner)

    fun run(isInArchiveMenu: Boolean) {
        val menuItems = listOf(
            "выбрать заметку",
            "создать заметку",
            "выход"
        )
        val actionMap = mapOf(
            "1" to { choseNote() },
            "2" to { createNote() }
        )
        menuManager.runMenu(menuItems, actionMap, isInArchiveMenu)
    }

    private fun choseNote() {
        if (archive.content.isEmpty()) {
            println("Заметки отсутствуют.")
            return
        }

        printList(archive.content) { note -> "${note.title}: ${note.content}" }

        val chosenTitle = getUserInput("Выберите заметку, вписав её название")

        val selectedNote = archive.content.find { it.title == chosenTitle }
        if (selectedNote != null) {
            println("Содержимое заметки '${selectedNote.title}':")
            println(selectedNote.content)
        } else {
            val createNew = confirmAction("Такой заметки не существует. Вы хотите создать новую заметку с названием '$chosenTitle'? (да/нет)")
            if (createNew) {
                createNote(chosenTitle)
            } else {
                println("Операция отменена.")
            }
        }
    }

    private fun createNote(title: String = "") {
        val newNoteTitle = title.ifBlank {
            getUserInput("Введите название для новой заметки")
        }

        if (newNoteTitle.isBlank()) {
            println("Название заметки не может быть пустым. Пожалуйста, попробуйте снова.")
            return
        }

        var newNoteContent = getUserInput("Введите содержимое для новой заметки")

        while (newNoteContent.isBlank()) {
            println("Содержимое заметки не может быть пустым. Пожалуйста, попробуйте снова.")
            newNoteContent = getUserInput("Введите содержимое для новой заметки")
        }

        val newNote = Note(newNoteTitle, newNoteContent)
        archive.content.add(newNote)
        println("Заметка '$newNoteTitle' успешно создана.")
    }
}