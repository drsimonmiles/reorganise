package reorganise.server

import java.io.{FileWriter, PrintWriter, File}
import java.time.LocalDate
import play.api.libs.json.{JsError, JsSuccess, Json}
import reorganise.server.model.TasksData
import reorganise.server.model.Readers._
import reorganise.server.model.Writers._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.{NoTasks, PriorToToday, NoRestriction, TaskList, VisibleTasks, TasksView, Task}
import scala.io.Source

class TasksService (tasksFile: String) extends TasksAPI {
  val emptySharedTasksData = VisibleTasks (Vector[Task] (), Vector[TaskList] ())
  val emptyDatabase = TasksData (currentTasksSchema, Vector[Task] (), Vector[TaskList] (), 0l, 0l)
  val listOrdering = new CorrespondingOrdering[Task, Long] (_.id, _: Seq[Long])
  val emptyList = TaskList (-1, "Select or add new list to start", Vector[Long] (), Some (NoTasks))

  // Data in cache, including loaded data and derived/dependent in-memory data
  var cache: Option[TasksData] = None
  var view = TasksView (includeCompleted = false, -1)

  private def retrieveTasksData: Option[TasksData] = {
    if (cache.isEmpty)
      cache = {
        val file = new File (tasksFile)
        if (file.exists) {
          val source = Source.fromFile (file).getLines.mkString
          Json.parse (source).validate [TasksData] match {
            case s: JsSuccess[TasksData] => Some (s.get)
            case e: JsError => println ("Unable to load tasks: " + e.errors); None
          }
        } else Some (emptyDatabase)
      }
    cache
  }

  private def lookupList (id: Long): TaskList =
    retrieveTasksData match {
      case Some (data) => data.lists.find (_.id == id).getOrElse (emptyList)
      case None => emptyList
    }

  private def storeTasksData (data: TasksData): VisibleTasks = {
    cache = Some (data)
    val out = new PrintWriter (new FileWriter (tasksFile))
    val json = Json.toJson (data)
    out.println (json)
    out.close ()
    loadTasks ()
  }

  private def startLocalDate (task: Task) =
    LocalDate.parse (task.startDate)

  private def upToDate (tasks: Vector[Task], toDate: LocalDate): Vector[Task] =
    tasks.filter (task => toDate.isAfter (startLocalDate (task)) || toDate.isEqual (startLocalDate (task)))

  def createTask (): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        val task = Task (data.nextTaskID, "", LocalDate.now.toString, view.list, None, completed = false)
        storeTasksData (data.copy (
          tasks = data.tasks :+ task, nextTaskID = data.nextTaskID + 1,
          lists = data.lists.map (old => old.copy (order = old.order :+ task.id))
        ))
      case None =>
        emptySharedTasksData
    }

  def createList (isDerived: Boolean): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        val nextID = data.nextListID
        val newList = TaskList (nextID, "New list", data.tasks.map (_.id),
          if (isDerived) Some (NoRestriction) else None
        )
        storeTasksData (data.copy (lists = data.lists :+ newList, nextListID = nextID + 1))
      case None =>
        emptySharedTasksData
    }

  def loadTasks (): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        val list = lookupList (view.list)
        val unordered = list.derivation match {
          case None => data.tasks.filter (_.list == view.list)
          case Some (NoTasks) => Vector[Task] ()
          case Some (NoRestriction) => data.tasks
          case Some (PriorToToday (days)) => upToDate (data.tasks, LocalDate.now.plusDays (days))
        }
        val visibleTasks = unordered.
          filter (view.includeCompleted || !_.completed).
          sorted (listOrdering (list.order))
        VisibleTasks (visibleTasks, data.lists)
      case None => emptySharedTasksData
    }

  def updateTask (task: Task): VisibleTasks = {
    retrieveTasksData match {
      case Some (data) =>
        val newTaskData =
          if (data.tasks.exists (_.id == task.id))
            data.copy (tasks = data.tasks.collect {
              case i if i.id == task.id => task
              case i => i
            })
          else data
        storeTasksData (newTaskData)
      case None => emptySharedTasksData
    }
  }

  def updateList (list: TaskList): VisibleTasks = {
    retrieveTasksData match {
      case Some (data) =>
        val newTaskData =
          if (data.lists.exists (_.id == list.id))
            data.copy (lists = data.lists.collect {
              case i if i.id == list.id => list
              case i => i
            })
          else
            data
        storeTasksData (newTaskData)
      case None => emptySharedTasksData
    }
  }

  def deleteTask (taskID: Long): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        storeTasksData (data.copy (tasks = data.tasks.filterNot (_.id == taskID)))
      case None => emptySharedTasksData
    }

  def deleteList (listID: Long): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        storeTasksData (data.copy (lists = data.lists.filterNot (_.id == listID)))
      case None => emptySharedTasksData
    }

  def setView (newView: TasksView): VisibleTasks = {
    view = newView
    loadTasks ()
  }
}
