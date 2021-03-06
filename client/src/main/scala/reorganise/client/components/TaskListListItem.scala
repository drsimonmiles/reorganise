package reorganise.client.components

import reorganise.client.model.ClientTasksView
import reorganise.client.styles.BootstrapAlertStyles.AlertStyle
import reorganise.shared.model.TasksView

case class TaskListListItem (label: String, view: Option[ClientTasksView], style: AlertStyle)
