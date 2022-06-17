package ru.alexeyk2021.taskplanner

import ru.alexeyk2021.taskplanner.activityLogic.MainActivity
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.TaskStatus


class SelectingTasks(private val mainActivity: MainActivity) {

    var typeTasksToShow: TaskStatus = TaskStatus.WORKING
        set(value) {
            field = value
            mainActivity.updateList()
        }

    fun generateList(allTasks: List<Task>, email: String): List<Task> {
        val tasks = mutableListOf<Task>()
        allTasks.forEach {
            if (it.status == typeTasksToShow.ordinal && it.usersEmails.contains(email)) tasks.add(it)
        }
        return tasks
    }
}