class ArchiveScreen : BaseScreen() {
    private val archives: MutableList<Archive> = mutableListOf()
    private val menuManager = MenuManager(scanner)

    fun run() {
        while (true) {
            println("Вы находитесь в меню архивов. Выберите, что вы хотите сделать:")
            val menuItems = listOf(
                "выбрать архив",
                "создать архив",
                "выход"
            )
            val actionMap = mapOf(
                "1" to { choseArchive() },
                "2" to { createArchive() },
            )
            menuManager.runMenu(menuItems, actionMap, true)
        }
    }

    private fun choseArchive() {
        if (archives.isEmpty()) {
            println("Архивы отсутствуют.")
            return
        }

        printList(archives) { archive -> archive.title }

        val chosenName = getUserInput("Выберите архив, вписав его имя")

        val selectedArchive = archives.find { it.title == chosenName }
        if (selectedArchive != null) {
            if (selectedArchive.content.isEmpty()) {
                println("Архив '${selectedArchive.title}' пуст. Выберите, что вы хотите с ним сделать:")
                NoteScreen(selectedArchive).run(false)
            } else {
                println("Содержимое архива '${selectedArchive.title}':")
                printList(selectedArchive.content) { note -> "${note.title}: ${note.content}" }
                NoteScreen(selectedArchive).run(false)
            }
        } else {
            val createNew = confirmAction("Такого архива не существует. Вы хотите создать новый архив с именем '$chosenName'? (да/нет)")
            if (createNew) {
                createArchive(chosenName)
            } else {
                println("Операция отменена.")
            }
        }
    }

    private fun createArchive(title: String = "") {
        val newArchiveTitle = title.ifBlank {
            getUserInput("Введите название для нового архива")
        }

        if (newArchiveTitle.isBlank()) {
            println("Название архива не может быть пустым. Пожалуйста, попробуйте снова.")
            return
        }

        val newArchive = Archive(newArchiveTitle)
        archives.add(newArchive)
        println("Архив '$newArchiveTitle' успешно создан.")
    }
}