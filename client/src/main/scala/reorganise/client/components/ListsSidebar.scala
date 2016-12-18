package reorganise.client.components

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.LoadableModel
import reorganise.client.model.generic.Variable
import reorganise.client.styles.BootstrapAlertStyles.{danger, info, success}
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{TaskList, TasksView}
import scalacss.ScalaCssReact._

object ListsSidebar {
  @inline private def bss = bootstrapStyles

  case class Props (lists: Vector[TaskList], view: Variable[LoadableModel, Option[TasksView]])

  val component = ReactComponentB[Props] ("ListsSidebar")
    .render_P { p =>
      val listItems: Vector[TaskListListItem] =
        TaskListListItem ("Lists", None, success) +:
          p.lists.map (list => TaskListListItem (list.name,
            Some (TasksView (includeCompleted = p.view.value.exists (_.includeCompleted), list.id)),
            if (list.derivation.isDefined) info else danger))

      <.ul (bss.listGroup.listGroup)(
        listItems.map { item => ListRow (item, p.view.value match {
          case None => item.view.isEmpty
          case Some (TasksView (_, list)) => item.view.exists (_.list == list)
        }, p.view.set)
        }
      )
    }.build

  def apply (lists: Vector[TaskList], view: Variable[LoadableModel, Option[TasksView]]) =
    component (Props (lists, view))
}
