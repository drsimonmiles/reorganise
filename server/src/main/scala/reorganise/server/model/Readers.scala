package reorganise.server.model

import play.api.libs.json._
import play.api.libs.functional.syntax._
import reorganise.server.model.OldModels._
import reorganise.shared.model.{TaskList, Task}

object Readers {
  implicit val taskReads: Reads[Task] = (
    (JsPath \ "id").read[Long] and
    (JsPath \ "text").read[String] and
    (JsPath \ "startDate").read[String] and
    (JsPath \ "list").read[Long] and
    (JsPath \ "recur").readNullable[Int] and
    (JsPath \ "completed").read[Boolean]
  ) (Task.apply _)

  implicit val taskListReads: Reads[TaskList] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "name").read[String]
    ) (TaskList.apply _)

  val tasksDataReadsV03: Reads[TasksData] = (
    (JsPath \ "schema").read[String] and
    (JsPath \ "tasks").read[Vector[Task]] and
    (JsPath \ "lists").read[Vector[TaskList]] and
    (JsPath \ "nextTaskID").read[Long] and
    (JsPath \ "nextListID").read[Long]
  ) (TasksData.apply _)

  implicit val tasksDataReads = new Reads[TasksData] {
    def reads (json: JsValue) =
      (json \ "schema").get match {
        case JsString ("01") => tasksDataReadsV01.reads (json)
        case JsString ("02") => tasksDataReadsV02.reads (json)
        case JsString ("03") => tasksDataReadsV03.reads (json)
        case x => println ("Schema version not recognised: " + x); JsError ()
      }
  }
}
