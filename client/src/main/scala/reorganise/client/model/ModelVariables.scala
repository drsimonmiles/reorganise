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
    attribute[TaskList, Int] (_.derivation.asInstanceOf[Option[PriorToToday]].get.days, (l, v) => UpdateList (l.copy (derivation = Some (PriorToToday (v)))))

  val SetAllListsOrder: VariableCreator[Vector[Long], Vector[(Long, Boolean)]] =
    attribute[Vector[Long], Vector[(Long, Boolean)]] (_.map (l => (l, true)), (l, v) => UpdateListOrder (v.map (_._1)))
}
