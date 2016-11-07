package reorganise.client.components

import reorganise.client.styles.BootstrapAlertStyles
import reorganise.shared.model.TasksView

case class TaskListListItem (label: String, view: Option[TasksView], style: BootstrapAlertStyles.Value)
