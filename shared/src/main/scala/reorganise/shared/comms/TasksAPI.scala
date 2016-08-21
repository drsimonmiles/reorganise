package reorganise.shared.comms

import reorganise.shared.model.{VisibleTasks, TasksView, Task}

trait TasksAPI {
  def createTask (view: TasksView): VisibleTasks
  def loadTasks (view: TasksView): VisibleTasks
  def updateTask (task: Task, view: TasksView): VisibleTasks
  def deleteTask (taskID: Long, view: TasksView): VisibleTasks
}
