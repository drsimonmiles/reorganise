package reorganise.shared.comms

import reorganise.shared.model.{TaskList, VisibleTasks, TasksView, Task}

trait TasksAPI {
  def createList (isDerived: Boolean): VisibleTasks
  def createTask (): VisibleTasks
  def loadTasks (): VisibleTasks
  def updateTask (task: Task): VisibleTasks
  def updateList (list: TaskList): VisibleTasks
  def deleteTask (taskID: Long): VisibleTasks
  def deleteList (listID: Long): VisibleTasks
  def setView (view: TasksView): VisibleTasks
}
