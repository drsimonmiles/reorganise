package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FeatureControls._
import reorganise.client.components.generic.Icon.{close, cut, plusSquare}
import reorganise.client.components.generic.{Button, Dropdown, Panel}
import reorganise.client.model.generic.VariableOps._
import reorganise.client.model.{ChangeListFeature, ChangeTaskFeature, ChangeView, ClientState, CreateList, CreateTask, DeleteList, ListFeature, TaskFeature}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{NoTasks, TaskList}
import scalacss.ScalaCssReact._

object ViewedItemsTable {
  @inline private def bss = bootstrapStyles

  val emptyList = TaskList (-1, "Select or add new list to start", Vector [Long](), Some (NoTasks))

  val component = ReactComponentB[ModelProxy[ClientState]] ("ViewedItemsTable")
    .render_P { p =>
      Panel (p.zoom (_.viewedList.map (_.name).getOrElse ("Lists")),
        <.div (
          p.value.viewedList match {
            case Some (list) =>
              val backupList = p.value.visible.lists.find (_.id != list.id).getOrElse (emptyList)
              val deleteCurrentList: CallbackTo[Unit] =
                p.dispatchCB (DeleteList (list.id)) >> p.dispatchCB (ChangeView (Some (p.value.view.get.copy (list = backupList.id))))
              val includeCompleted = p.value.view.get.includeCompleted
              val toggleCompleted: Callback =
                p.dispatchCB (ChangeView (p.value.view.map (_.copy (includeCompleted = !includeCompleted))))

              <.div (bss.listGroup.listGroup,
                <.div (bss.row, compact,
                  <.div (bss.columns (10), <.span ("")),
                  <.div (bss.columns (2),
                    new Dropdown[TaskFeature] ("feature", _.label, warning, taskFeatures).
                      apply (p.zoom (_.taskFeature).variable (ChangeTaskFeature)))),
                  list.order.filter (taskID => p.value.visible.task (taskID).isDefined).
                    map (taskID => TaskRow (p, p.zoom (_.visible.task (taskID).get), p.value.taskFeature)),
                list.derivation.isEmpty ?= Button (Button.Props (p.dispatchCB (CreateTask)), plusSquare, " New task"),
                Button (Button.Props (deleteCurrentList), close, " Delete list"),
                Button (Button.Props (toggleCompleted), cut, if (includeCompleted) " Hide completed" else " Show completed")
              )
            case None =>
              <.div (bss.listGroup.listGroup,
                <.div (bss.row, compact,
                  <.div (bss.columns (10), <.span ("")),
                  <.div (bss.columns (2),
                    new Dropdown[ListFeature] ("feature", _.label, warning, listFeatures).
                      apply (p.zoom (_.listFeature).variable (ChangeListFeature)))),
                p.value.visible.lists.map (list =>
                  ListEditRow (p.value.listFeature, p.zoom (_.visible), p.zoom (_.visible.list (list.id).get))),
                Button (Button.Props (p.dispatchCB (CreateList (false))), plusSquare, " New label list"),
                Button (Button.Props (p.dispatchCB (CreateList (true))), plusSquare, " New derived list")
              )
          }
        )
      )
    }.build

  def apply (data: ModelProxy[ClientState]) =
    component (data)
}