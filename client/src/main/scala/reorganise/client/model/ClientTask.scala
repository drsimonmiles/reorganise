package reorganise.client.model

import reorganise.shared.model.TaskList

case class ClientTask (id: Long, text: String, startDate: String,
                       list: TaskList, recur: Option[Int], completed: Boolean)
