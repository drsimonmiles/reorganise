package reorganise.server.model

import play.api.libs.json._
import play.api.libs.functional.syntax._
import reorganise.shared.model.Task

object Writers {
  implicit val taskWrites: Writes[Task] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "text").write[String] and
    (JsPath \ "startDate").write[String] and
    (JsPath \ "list").write[String] and
    (JsPath \ "recur").writeNullable[Int] and
    (JsPath \ "completed").write[Boolean]
  ) (unlift (Task.unapply))

  implicit val tasksDataWrites: Writes[TasksData] = (
    (JsPath \ "schema").write[String] and
    (JsPath \ "tasks").write[Vector[Task]] and
    (JsPath \ "nextID").write[Long]
  ) (unlift (TasksData.unapply))
}
