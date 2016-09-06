package reorganise.server.model

import play.api.libs.json._
import play.api.libs.functional.syntax._
import reorganise.shared.model.{PriorToToday, NoRestriction, Derivation, TaskList, Task}

object Writers {
  val currentTasksSchema = "04"

  implicit val taskWrites: Writes[Task] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "text").write[String] and
    (JsPath \ "startDate").write[String] and
    (JsPath \ "list").write[Long] and
    (JsPath \ "recur").writeNullable[Int] and
    (JsPath \ "completed").write[Boolean]
  ) (unlift (Task.unapply))

  implicit val derivationWrites = new Writes[Derivation] {
    def writes (derivation: Derivation): JsValue =
      derivation match {
        case NoRestriction => JsString ("NoRestriction")
        case PriorToToday (days) => JsString ("PriorToToday:" + days)
      }
  }

  implicit val taskListWrites: Writes[TaskList] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "name").write[String] and
    (JsPath \ "order").write[Vector[Long]] and
    (JsPath \ "derivation").writeNullable[Derivation]
  ) (unlift (TaskList.unapply))

  implicit val tasksDataWrites: Writes[TasksData] = (
    (JsPath \ "schema").write[String] and
    (JsPath \ "tasks").write[Vector[Task]] and
    (JsPath \ "lists").write[Vector[TaskList]] and
    (JsPath \ "nextTaskID").write[Long] and
    (JsPath \ "nextListID").write[Long]
  ) (unlift (TasksData.unapply))
}
