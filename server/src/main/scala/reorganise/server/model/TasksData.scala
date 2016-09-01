package reorganise.server.model

import reorganise.shared.model.{TaskList, Task}

case class TasksData (schema: String, tasks: Vector[Task], lists: Vector[TaskList],
                      nextTaskID: Long, nextListID: Long)
