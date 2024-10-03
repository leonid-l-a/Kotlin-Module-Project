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
                "1" to { choseItem("архив") { choseArchive() } },
                "2" to { createItem("архив") { createArchive() } }
            )
            menuManager.runMenu(menuItems, actionMap)
        }
    }

    private fun choseArchive() {
        val chosenName = getUserInput("Выберите архив, вписав его имя")
        val selectedArchive = archives.find { it.title == chosenName }
        if (selectedArchive != null) {
            NoteScreen(selectedArchive).run()
        } else {
            createItem("архив", chosenName) { createArchive(chosenName) }
        }
    }

    private fun createArchive(title: String = "") {
        val newArchiveTitle = title.ifBlank { getUserInput("Введите название для нового архива") }
        if (newArchiveTitle.isBlank()) {
            println("Название архива не может быть пустым. Пожалуйста, попробуйте снова.")
            return
        }
        val newArchive = Archive(newArchiveTitle)
        archives.add(newArchive)
        println("Архив '$newArchiveTitle' успешно создан.")
    }

    private fun choseItem(itemType: String, action: () -> Unit) {
        if (archives.isEmpty()) {
            println("Архивы отсутствуют.")
            return
        }
        printList(archives) { archive -> archive.title }
        action()
    }

    private fun createItem(itemType: String, title: String = "", action: (String) -> Unit) {
        val createNew = confirmAction("Такой $itemType не существует. Вы хотите создать новый $itemType с именем '$title'? (да/нет)")
        if (createNew) {
            action(title)
        } else {
            println("Операция отменена.")
        }
    }
}