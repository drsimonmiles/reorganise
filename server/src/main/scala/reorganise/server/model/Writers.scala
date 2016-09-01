package reorganise.server.model

import play.api.libs.json._
import play.api.libs.functional.syntax._
import reorganise.shared.model.{TaskList, Task}

object Writers {
  val currentTasksSchema = "03"

  implicit val taskWrites: Writes[Task] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "text").write[String] and
    (JsPath \ "startDate").write[String] and
    (JsPath \ "list").write[Long] and
    (JsPath \ "recur").writeNullable[Int] and
    (JsPath \ "completed").write[Boolean]
  ) (unlift (Task.unapply))

  implicit val taskListWrites: Writes[TaskList] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "name").write[String]
  ) (unlift (TaskList.unapply))

  implicit val tasksDataWrites: Writes[TasksData] = (
    (JsPath \ "schema").write[String] and
    (JsPath \ "tasks").write[Vector[Task]] and
    (JsPath \ "lists").write[Vector[TaskList]] and
    (JsPath \ "nextTaskID").write[Long] and
    (JsPath \ "nextListID").write[Long]
  ) (unlift (TasksData.unapply))
}
