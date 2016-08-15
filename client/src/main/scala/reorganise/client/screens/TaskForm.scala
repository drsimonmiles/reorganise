package reorganise.client.screens

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.{Button, Icon, Modal}
import reorganise.client.styles.GlobalStyles
import reorganise.shared.model.Task
import scala.scalajs.js.Date
import scalacss.ScalaCssReact._

object TaskForm {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (task: Option[Task], submitHandler: (Task, Boolean) => Callback)

  case class State (task: Task, cancelled: Boolean = true)

  class Backend (t: BackendScope[Props, State]) {
    def submitForm (): Callback =
      // mark it as NOT cancelled (which is the default)
      t.modState (s => s.copy (cancelled = false))

    def formClosed (state: State, props: Props): Callback =
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler (state.task, state.cancelled)

    def updateDescription (e: ReactEventI) = {
      val text = e.target.value
      // update TodoItem content
      t.modState (s => s.copy (task = s.task.copy (text = text)))
    }

    def render (p: Props, s: State) = {
      val headerText = if (s.task.id == -1) "Add new todo" else "Edit todo"
      Modal (Modal.Props (
        // header contains a cancel button (X)
        header = hide => <.span (<.button (^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4 (headerText)),
        // footer has the OK button that submits the form before hiding it
        footer = hide => <.span (Button (Button.Props (submitForm () >> hide), "OK")),
        // this is called after the modal has been hidden (animation is completed)
        closed = formClosed (s, p)),
        <.div (bss.formGroup,
          <.label (^.`for` := "description", "Description"),
          <.input.text (bss.formControl, ^.id := "description", ^.value := s.task.text,
            ^.placeholder := "write description", ^.onChange ==> updateDescription))
        )
    }
  }

  def currentDate = {
    def twoDigits (value: Int) = if (value > 9) value.toString else "0" + value
    val date = new Date ()
    date.getFullYear () + "-" + twoDigits (date.getMonth ()) + "-" + twoDigits (date.getDay ())
  }

  val component = ReactComponentB[Props]("TaskForm")
    .initialState_P (p => State (p.task.getOrElse (
      //Task (id = -1, text = "", currentDate, list = "Miscellaneous", recur = -1, completed = false))))
      Task (id = -1, text = "", currentDate, list = "Miscellaneous", recur = None, completed = false))))
    .renderBackend[Backend]
    .build

  def apply (props: Props) = component(props)
}