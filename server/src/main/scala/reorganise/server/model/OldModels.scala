package reorganise.server.model

import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._
import reorganise.shared.model.{TaskList, Task}

object OldModels {
  case class Task01 (id: Long, text: String, startDate: String, list: String, recur: Option[Int], completed: Boolean)

  implicit val taskReadsV01: Reads[Task01] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "text").read[String] and
      (JsPath \ "startDate").read[String] and
      (JsPath \ "list").read[String] and
      (JsPath \ "recur").readNullable[Int] and
      (JsPath \ "completed").read[Boolean]
    ) (Task01.apply _)

  object TasksData01 {
    def apply (schema: String, tasks: Vector[Task01], nextTaskID: Long): TasksData = {
      val lists = tasks.map (_.list).distinct.sorted.zipWithIndex.map (list => TaskList (list._2, list._1))
      def listID (name: String) = lists.find (_.name == name).map (_.id).getOrElse (0l)
      val newTasks = tasks.map (old => Task (old.id, old.text, old.startDate, listID (old.list), old.recur, old.completed))
      TasksData (Writers.currentTasksSchema, newTasks, lists, nextTaskID, lists.length)
    }
  }

  object TasksData02 {
    def apply (schema: String, tasks: Vector[Task01], lists: Vector[String], nextTaskID: Long): TasksData = {
      val newLists = lists.zipWithIndex.map (list => TaskList (list._2, list._1))
      def listID (name: String) = newLists.find (_.name == name).map (_.id).getOrElse (0l)
      val newTasks = tasks.map (old => Task (old.id, old.text, old.startDate, listID (old.list), old.recur, old.completed))
      TasksData (Writers.currentTasksSchema, newTasks, newLists, nextTaskID, lists.length)
    }
  }

  val tasksDataReadsV01: Reads[TasksData] = (
    (JsPath \ "schema").read[String] and
      (JsPath \ "tasks").read[Vector[Task01]] and
      (JsPath \ "nextID").read[Long]
    ) (TasksData01.apply _)

  val tasksDataReadsV02: Reads[TasksData] = (
    (JsPath \ "schema").read[String] and
      (JsPath \ "tasks").read[Vector[Task01]] and
      (JsPath \ "lists").read[Vector[String]] and
      (JsPath \ "nextID").read[Long]
    ) (TasksData02.apply _)
}
