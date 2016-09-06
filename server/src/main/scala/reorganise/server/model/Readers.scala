package reorganise.server.model

import play.api.libs.json._
import play.api.libs.functional.syntax._
import reorganise.server.model.PreviousModel._
import reorganise.shared.model.{NoRestriction, PriorToToday, Derivation, TaskList, Task}

object Readers {
  implicit val taskReads: Reads[Task] = (
    (JsPath \ "id").read[Long] and
    (JsPath \ "text").read[String] and
    (JsPath \ "startDate").read[String] and
    (JsPath \ "list").read[Long] and
    (JsPath \ "recur").readNullable[Int] and
    (JsPath \ "completed").read[Boolean]
  ) (Task.apply _)

  implicit val derivationReads = new Reads[Derivation] {
    def reads (json: JsValue): JsResult[Derivation] = {
      val formula = json.as [String]
      if (formula == "NoRestriction") JsSuccess (NoRestriction)
      else if (formula.startsWith ("PriorToToday:")) JsSuccess (PriorToToday (formula.drop (13).toInt))
      else JsError ()
    }
  }

  implicit val taskListReads: Reads[TaskList] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "name").read[String] and
      (JsPath \ "order").read[Vector[Long]] and
      (JsPath \ "derivation").readNullable[Derivation]
    ) (TaskList.apply _)

  val tasksDataReadsV04: Reads[TasksData] = (
    (JsPath \ "schema").read[String] and
      (JsPath \ "tasks").read[Vector[Task]] and
      (JsPath \ "lists").read[Vector[TaskList]] and
      (JsPath \ "nextTaskID").read[Long] and
      (JsPath \ "nextListID").read[Long]
    ) (TasksData.apply _)

  implicit val tasksDataReads = new Reads[TasksData] {
    def reads (json: JsValue) =
      (json \ "schema").get match {
        case JsString ("03") => tasksDataReadsV03.reads (json)
        case JsString ("04") => tasksDataReadsV04.reads (json)
        case x => println ("Schema version not recognised: " + x); JsError ()
      }
  }
}
