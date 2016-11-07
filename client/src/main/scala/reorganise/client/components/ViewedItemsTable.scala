package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FeatureControls._
import reorganise.client.components.generic.Icon.{close, cut, plusSquare}
import reorganise.client.components.generic.{Button, FixedDropdown, Panel}
import reorganise.client.model.{ChangeListFeature, ChangeTaskFeature, ChangeView, ClientState, CreateList, CreateTask, DeleteList, ListFeature, LoadableModel, ModelPoint, TaskFeature}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{NoTasks, TaskList}
import scalacss.ScalaCssReact._

object ViewedItemsTable {
  @inline private def bss = bootstrapStyles

  val emptyList = TaskList (-1, "Select or add new list to start", Vector [Long](), Some (NoTasks))

  val component = ReactComponentB[ModelPoint[LoadableModel, ClientState]] ("ViewedItemsTable")
    .render_P { p =>
      Panel (p.zoom (_.viewedList.map (_.name).getOrElse ("Lists")),
        <.div (
          p.value.viewedList match {
            case Some (list) =>
              val rowReader = p.zoom (_.taskFeature, _.visible.lists, _.viewedList.get)
              val backupList = p.value.visible.lists.find (_.id != list.id).getOrElse (emptyList)
              val deleteCurrentList: CallbackTo[Unit] =
                p.dispatch (DeleteList (list.id)) >> p.dispatch (ChangeView (Some (p.value.view.get.copy (list = backupList.id))))
              val includeCompleted = p.value.view.get.includeCompleted
              val toggleCompleted: Callback =
                p.dispatch (ChangeView (p.value.view.map (_.copy (includeCompleted = !includeCompleted))))

              <.div (bss.listGroup.listGroup,
                <.div (bss.row, compact,
                  <.div (bss.columns (10), <.span ("")),
                  <.div (bss.columns (2),
                    new FixedDropdown[LoadableModel, TaskFeature] ("feature", _.label, warning, taskFeatures).
                      apply (p.zoom (_.taskFeature).variable (ChangeTaskFeature)))),
                list.order.map (taskID => TaskRow (rowReader.zip (p.zoom (_.visible.task (taskID))))),
                list.derivation.isEmpty ?= Button (Button.Props (p.dispatch (CreateTask)), plusSquare, " New task"),
                Button (Button.Props (deleteCurrentList), close, " Delete list"),
                Button (Button.Props (toggleCompleted), cut, if (includeCompleted) " Hide completed" else " Show completed")
              )
            case None =>
              val rowReader = p.zoom (_.listFeature, _.visible)

              <.div (bss.listGroup.listGroup,
                <.div (bss.row, compact,
                  <.div (bss.columns (10), <.span ("")),
                  <.div (bss.columns (2),
                    new FixedDropdown[LoadableModel, ListFeature] ("feature", _.label, warning, listFeatures).
                      apply (p.zoom (_.listFeature).variable (ChangeListFeature)))),
                p.value.visible.lists.map (list => ListEditRow (rowReader.zip (p.zoom (_.visible.list (list.id))))),
                Button (Button.Props (p.dispatch (CreateList (false))), plusSquare, " New label list"),
                Button (Button.Props (p.dispatch (CreateList (true))), plusSquare, " New derived list")
              )
          }
        )
      )
    }.build

  def apply (data: ModelPoint[LoadableModel, ClientState]) =
    data.createReader (component)
}