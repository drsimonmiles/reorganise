package reorganise.client.components

import directed.DiodeVariable
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.ClientTasksView
import reorganise.client.styles.BootstrapAlertStyles.{danger, info, success}
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.TaskList
import scalacss.ScalaCssReact._

object ListsSidebar {
  @inline private def bss = bootstrapStyles

  case class Props (lists: Vector[TaskList], view: DiodeVariable[Option[ClientTasksView]])

  val component = ReactComponentB[Props] ("ListsSidebar")
    .render_P { p =>
      val listItems: Vector[TaskListListItem] =
        TaskListListItem ("Lists", None, success) +:
          p.lists.map (list => TaskListListItem (list.name,
            Some (ClientTasksView (includeCompleted = p.view.value.exists (_.includeCompleted), list)),
            if (list.derivation.isDefined) info else danger))

      <.ul (bss.listGroup.listGroup)(
        listItems.map { item => ListRow (item, p.view.value match {
          case None => item.view.isEmpty
          case Some (ClientTasksView (_, list)) => item.view.exists (_.list == list)
        }, p.view.set)
        }
      )
    }.build

  def apply (lists: Vector[TaskList], view: DiodeVariable[Option[ClientTasksView]]) =
    component (Props (lists, view))
}
