package reorganise.client.components

import diode.data.{Pot, Ready}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.Icon.{banned, chevronCircleDown, chevronCircleUp, longArrowUp, longArrowDown, refresh}
import reorganise.client.components.generic.{Button, DatePicker, Dropdown}
import reorganise.client.model.{ListFeature, OrderFeature, RecurFeature, StartFeature, TaskFeature, UpdateTask}
import reorganise.client.styles.BootstrapAlertStyles.primary
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{Task, TaskList, VisibleTasks}
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = bootstrapStyles

  case class Props (visible: ModelProxy[Pot[VisibleTasks]], task: Task, feature: ModelProxy[TaskFeature],
                    listLookup: Long => Option[TaskList], stateChange: Task => Callback,
                    moveUpAll: Callback, moveUpOne: Callback, moveDownOne: Callback, moveDownAll: Callback)

  class Backend (t: BackendScope[Props, Task]) {
    def listDropdown (p: Props) = {
      def setList (list: TaskList) = p.visible.dispatch (UpdateTask (p.task.copy (list = list.id)))
      p.visible.value match {
        case Ready (tasksData) =>
          val listLabel: String = p.listLookup (p.task.list).map (_.name).getOrElse ("Unknown")
          Dropdown[TaskList] (listLabel, primary, tasksData.lists.filter (_.derivation.isEmpty), _.name, setList)
        case _ =>
          Dropdown[TaskList] ("Unknown", primary, Vector[TaskList] (), _.name, setList)
      }
    }

    def render (p: Props, s: Task): ReactElement = {
      <.div (bss.row,
        <.div (bss.columns (10),
          <.div (bss.columns (1),
            <.input.checkbox (^.checked := s.completed,
              ^.onChange --> p.stateChange (s.copy (completed = !s.completed)))),
          <.div (bss.columns (11), bss.formGroup, compact,
              <.input.text (bss.formControl, ^.id := "description", ^.value := s.text,
                ^.placeholder := "write task description",
                ^.onChange ==> { e: ReactEventI => t.setState (s.copy (text = e.target.value)) },
                ^.onKeyPress ==> { e: ReactKeyboardEventI =>
                  Callback (if (e.charCode == 13) e.currentTarget.blur ())
                },
                ^.onBlur ==> { e: ReactEventI => p.stateChange (s) }
              ))),
        <.div (bss.columns (2), p.feature.value match {
          case ListFeature => <.span (listDropdown (p))
          case StartFeature => DatePicker (s.startDate)
          case RecurFeature => s.recur match {
            case Some (days) => <.span (refresh, days.toString)
            case None => banned ("refresh")
          }
          case OrderFeature =>
            <.div (
              <.span (Button (Button.Props (onClick = p.moveUpAll, addStyles = Seq (bss.buttonSmall)), longArrowUp)),
              <.span (Button (Button.Props (onClick = p.moveUpOne, addStyles = Seq (bss.buttonSmall)), chevronCircleUp)),
              <.span (Button (Button.Props (onClick = p.moveDownOne, addStyles = Seq (bss.buttonSmall)), chevronCircleDown)),
              <.span (Button (Button.Props (onClick = p.moveDownAll, addStyles = Seq (bss.buttonSmall)), longArrowDown))
            )
        })
      )
    }
  }

  val component = ReactComponentB[Props]("TaskRow")
    .initialState_P (p => p.task)
    .renderBackend[Backend]
    .build

  def apply (visible: ModelProxy[Pot[VisibleTasks]], task: Task, feature: ModelProxy[TaskFeature],
             listLookup: Long => Option[TaskList], dispatcher: ModelProxy[_],
             moveUpAll: Callback, moveUpOne: Callback, moveDownOne: Callback, moveDownAll: Callback) =
    component (Props (visible, task, feature, listLookup, task => dispatcher.dispatch (UpdateTask (task)),
      moveUpAll, moveUpOne, moveDownOne, moveDownAll))
}
