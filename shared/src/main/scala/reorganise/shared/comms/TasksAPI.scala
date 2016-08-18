package reorganise.shared.comms

import reorganise.shared.model.{TasksView, Task}

trait TasksAPI {
  def loadTasks (view: TasksView): Vector[Task]
  def updateTask (task: Task, view: TasksView): Vector[Task]
  def deleteTask (taskID: Long, view: TasksView): Vector[Task]
}
