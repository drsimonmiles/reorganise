package reorganise.server.model

import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._
import reorganise.shared.model.{TaskList, Task}
import reorganise.server.model.Readers.taskReads

object PreviousModel {
  case class OldTaskList (id: Long, name: String)

  implicit val taskListReadsOld: Reads[OldTaskList] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "name").read[String]
    ) (OldTaskList.apply _)

  object OldTasksData {
    def apply (schema: String, tasks: Vector[Task], lists: Vector[OldTaskList], nextTaskID: Long, nextListID: Long): TasksData = {
      val order = tasks.map (_.id)
      val newLists = lists.map (old => TaskList (old.id, old.name, order, None))
      TasksData (Writers.currentTasksSchema, tasks, newLists, nextTaskID, nextListID)
    }
  }

  val tasksDataReadsV03: Reads[TasksData] = (
    (JsPath \ "schema").read[String] and
      (JsPath \ "tasks").read[Vector[Task]] and
      (JsPath \ "lists").read[Vector[OldTaskList]] and
      (JsPath \ "nextTaskID").read[Long] and
      (JsPath \ "nextListID").read[Long]
    ) (OldTasksData.apply _)
}
