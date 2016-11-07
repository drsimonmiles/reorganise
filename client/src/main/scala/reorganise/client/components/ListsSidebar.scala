package reorganise.client.components

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.LoadableModel
import reorganise.client.model.generic.ScopedVariable
import reorganise.client.styles.BootstrapAlertStyles.{danger, info, success}
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{TaskList, TasksView}
import scalacss.ScalaCssReact._

object ListsSidebar {
  @inline private def bss = bootstrapStyles

  val component = ReactComponentB[ScopedVariable[LoadableModel, Vector[TaskList], Option[TasksView]]] ("ListsSidebar")
    .render_P { p =>
      val listItems: Vector[TaskListListItem] =
        TaskListListItem ("Lists", None, success) +:
          p.scope.map (list => TaskListListItem (list.name,
            Some (TasksView (includeCompleted = p.value.exists (_.includeCompleted), list.id)),
            if (list.derivation.isDefined) info else danger))

      <.ul (bss.listGroup.listGroup)(
        listItems.map { item => ListRow (item, p.value match {
          case None => item.view.isEmpty
          case Some (TasksView (_, list)) => item.view.exists (_.list == list)
        }, p.set)
        }
      )
    }.build

  def apply (data: ScopedVariable[LoadableModel, Vector[TaskList], Option[TasksView]]) =
    data.createEditor (component)
}
