package reorganise.server.model

import play.api.libs.json._
import play.api.libs.functional.syntax._
import reorganise.shared.model.Task

object Readers {
  implicit val taskReadsV01: Reads[Task] = (
    (JsPath \ "id").read[Long] and
    (JsPath \ "text").read[String] and
    (JsPath \ "startDate").read[String] and
    (JsPath \ "list").read[String] and
    (JsPath \ "recur").readNullable[Int] and
    (JsPath \ "completed").read[Boolean]
  ) (Task.apply _)

  val tasksDataReadsV01: Reads[TasksData] = (
    (JsPath \ "schema").read[String] and
    (JsPath \ "tasks").read[Vector[Task]] and
    (JsPath \ "nextID").read[Long]
  ) (TasksData.apply _)

  implicit val tasksDataReads = new Reads[TasksData] {
    def reads (json: JsValue) =
      (json \ "schema").get match {
        case JsString ("01") => tasksDataReadsV01.reads (json)
        case x => println ("Schema version not recognised: " + x); JsError ()
      }
  }
}
