package reorganise.server.model

import reorganise.shared.model.Task

case class TasksData (schema: String, tasks: Vector[Task], nextID: Long)