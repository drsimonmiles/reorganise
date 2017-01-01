package reorganise.client.model

import reorganise.shared.model.TaskList

case class ClientTasksView (includeCompleted: Boolean, list: TaskList)
