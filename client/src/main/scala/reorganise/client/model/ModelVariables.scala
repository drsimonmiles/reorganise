package reorganise.client.model

import reorganise.shared.model.{TasksView, VisibleTasks, Derivation, TaskList, PriorToToday, Task}

object ModelVariables {
  def setTaskCompleted (task: Task, completed: Boolean) = UpdateTask (task.copy (completed = completed))
  def setTaskText (task: Task, text: String) = UpdateTask (task.copy (text = text))
  def setTaskList (task: Task, list: TaskList) = UpdateTask (task.copy (list = list.id))
  def setTaskStart (task: Task, startDate: String) = UpdateTask (task.copy (startDate = startDate))
  def setTaskRecur (task: Task, recur: Option[Int]) = UpdateTask (task.copy (recur = recur))

  def setListName (list: TaskList, name: String) = UpdateList (list.copy (name = name))
  def setListOrder (list: TaskList, order: Vector[(Long, Boolean)]) = UpdateList (list.copy (order = order.map (_._1)))
  def setListDerivation (list: TaskList, derivation: Derivation) = UpdateList (list.copy (derivation = Some (derivation)))
  def setListPriorDays (list: TaskList, days: Int) = UpdateList (list.copy (derivation = Some (PriorToToday (days))))

  def setAllListsOrder (visible: VisibleTasks, order: Vector[(Long, Boolean)]) = UpdateListOrder (order.map (_._1))
}
