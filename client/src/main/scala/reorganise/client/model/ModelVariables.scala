package reorganise.client.model

import directed.{VariableCreator, VariableUpdateModel}
import reorganise.client.model.ModelOps._
import reorganise.shared.model.{Derivation, PriorToToday, TaskList}

object ModelVariables extends VariableUpdateModel (ModelController) {
  val SetTaskCompleted: VariableCreator[ClientTask, Boolean] =
    attribute[ClientTask, Boolean] (_.completed, (t, v) => UpdateTask (t.copy (completed = v)))
  val SetTaskText: VariableCreator[ClientTask, String] =
    attribute[ClientTask, String] (_.text, (t, v) => UpdateTask (t.copy (text = v)))
  val SetTaskList: VariableCreator[ClientTask, TaskList] =
    attribute[ClientTask, TaskList] (_.list, (t, v) => UpdateTask (t.copy (list = v)))
  val SetTaskStart: VariableCreator[ClientTask, String] =
    attribute[ClientTask, String] (_.startDate, (t, v) => UpdateTask (t.copy (startDate = v)))
  val SetTaskRecur: VariableCreator[ClientTask, Option[Int]] =
    attribute[ClientTask, Option[Int]] (_.recur, (t, v) => UpdateTask (t.copy (recur = v)))

  val SetListName: VariableCreator[TaskList, String] =
    attribute[TaskList, String] (_.name, (l, v) => UpdateList (l.copy (name = v)))
  val SetListOrder: VariableCreator[(ClientState, TaskList), Vector[(Long, Boolean)]] =
    attribute[(ClientState, TaskList), Vector[(Long, Boolean)]] (p =>
      orderWithVisibility (p._1, p._2.order), (cl, v) => UpdateList (cl._2.copy (order = v.map (_._1))))
  val SetListDerivation: VariableCreator[TaskList, Derivation] =
    attribute[TaskList, Derivation] (_.derivation.get, (l, v) => UpdateList (l.copy (derivation = Some(v))))
  val SetListPriorDays: VariableCreator[TaskList, Int] =
    attribute[TaskList, Int] (_.derivation.asInstanceOf[PriorToToday].days, (l, v) => UpdateList (l.copy (derivation = Some (PriorToToday (v)))))

  val SetAllListsOrder: VariableCreator[Vector[Long], Vector[(Long, Boolean)]] =
    attribute[Vector[Long], Vector[(Long, Boolean)]] (_.map (l => (l, true)), (l, v) => UpdateListOrder (v.map (_._1)))
/*
  def setTaskCompleted (task: ClientTask, completed: Boolean) = UpdateTask (task.copy (completed = completed))
  def setTaskText (task: ClientTask, text: String) = UpdateTask (task.copy (text = text))
  def setTaskList (task: ClientTask, list: TaskList) = UpdateTask (task.copy (list = list))
  def setTaskStart (task: ClientTask, startDate: String) = UpdateTask (task.copy (startDate = startDate))
  def setTaskRecur (task: ClientTask, recur: Option[Int]) = UpdateTask (task.copy (recur = recur))

  def setListName (list: TaskList, name: String) = UpdateList (list.copy (name = name))
  def setListOrder (list: TaskList, order: Vector[(Long, Boolean)]) = UpdateList (list.copy (order = order.map (_._1)))
  def setListDerivation (list: TaskList, derivation: Derivation) = UpdateList (list.copy (derivation = Some (derivation)))
  def setListPriorDays (list: TaskList, days: Int) = UpdateList (list.copy (derivation = Some (PriorToToday (days))))

  def setAllListsOrder (visible: ClientState, order: Vector[(Long, Boolean)]) = UpdateListOrder (order.map (_._1))*/
}
