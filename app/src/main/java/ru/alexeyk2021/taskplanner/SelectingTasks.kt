package ru.alexeyk2021.taskplanner

import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.TaskStatus


class SelectingTasks() {
    companion object {
        private var selectingTasks: SelectingTasks? = null

        fun getInstance(): SelectingTasks {
            if (selectingTasks == null)
                selectingTasks = SelectingTasks()
            return selectingTasks!!
        }
    }

    var typeTasksToShow: TaskStatus = TaskStatus.NOT_STARTED
    var allTasks: List<Task> = mutableListOf()

    fun generateList(): List<Task> {
        val tasks = mutableListOf<Task>()
        allTasks.forEach {
            if (it.status == typeTasksToShow.ordinal) tasks.add(it)
        }
        return tasks
    }
}