package reorganise.shared.comms

import reorganise.shared.model.Task

trait TasksAPI {
  def loadTasks (): Vector[Task]
  def updateTask (task: Task): Vector[Task]
  def deleteTask (taskID: Long): Vector[Task]
}
