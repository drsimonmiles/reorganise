package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FAIcon.{close, cut, plusSquare}
import reorganise.client.components.GenericComponents._
import reorganise.client.model.ModelController.dispatchCB
import reorganise.client.model.ModelVariables._
import reorganise.client.model._
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{NoTasks, TaskList}
import scalacss.ScalaCssReact._

object ViewedItemsTable {
  @inline private def bss = bootstrapStyles

  val emptyList = TaskList (-1, "Select or add new list to start", Vector [Long](), Some (NoTasks))

  val taskFeatures = Vector (StartFeature, RecurFeature, LabelFeature, OrderFeature)

  val listFeatures = Vector (OrderFeature, DerivationFeature, PriorDaysFeature)

  val component = ReactComponentB[ClientState] ("ViewedItemsTable")
    .render_P { p =>
      panel (p.viewedList.map (_.name).getOrElse ("Lists"),
          p.viewedList match {
            case Some (list) =>
              val backupList = p.lists.find (_.id != list.id).getOrElse (emptyList)
              val deleteCurrentList: CallbackTo[Unit] =
                dispatchCB (DeleteList (list.id)) >> dispatchCB (ChangeView (Some (p.view.get.copy (list = backupList))))
              val includeCompleted = p.view.get.includeCompleted
              val toggleCompleted: Callback =
                dispatchCB (ChangeView (p.view.map (_.copy (includeCompleted = !includeCompleted))))

              <.div (bss.listGroup.listGroup,
                <.div (bss.row, compact,
                  <.div (bss.columns (10), <.span ("")),
                  <.div (bss.columns (2),
                    dropdown[TaskFeature] ("feature", _.label, warning, taskFeatures).
                      apply (ChangeTaskFeature.from (p.taskFeature)))),
                  list.order.filter (taskID => p.task (taskID).isDefined).
                    map (taskID => TaskRow (p, p.task (taskID).get, p.taskFeature)),
                list.derivation.isEmpty ?= buttonItem (dispatchCB (CreateTask), Seq (plusSquare (), " New task"), "new"),
                button (deleteCurrentList, Seq (close (), " Delete list")),
                button (toggleCompleted, Seq (cut (), if (includeCompleted) " Hide completed" else " Show completed"))
              )
            case None =>
              <.div (bss.listGroup.listGroup,
                <.div (bss.row, compact,
                  <.div (bss.columns (10), <.span ("")),
                  <.div (bss.columns (2),
                    dropdown[ListFeature] ("feature", _.label, warning, listFeatures).
                      apply (ChangeListFeature.from (p.listFeature)))),
                p.lists.map (list => ListEditRow (p.listFeature, p, list)),
                button (dispatchCB (CreateList (false)), Seq (plusSquare (), " New label list")),
                button (dispatchCB (CreateList (true)), Seq (plusSquare (), " New derived list"))
              )
          }
      )
    }.build

  def apply (data: ClientState): ReactElement =
    component (data)
}