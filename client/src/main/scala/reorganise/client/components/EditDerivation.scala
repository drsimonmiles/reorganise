package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.{Dropdown, Icon}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles
import reorganise.shared.model.{NoRestriction, NoTasks, PriorToToday, TaskList}
import scalacss.ScalaCssReact._

object EditDerivation {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (list: TaskList, update: TaskList => Callback)

  class Backend (scope: BackendScope[Props, Int]) {
    def render (model: Props, days: Int): ReactElement = {
      val noTasks = "No tasks"
      val noRestriction = "All tasks"
      val priorToToday = "Prior to today"
      val options = Vector (noTasks, noRestriction, priorToToday)
      def setDerivation (newDerivationName: String) = {
        val newDerivation =
          if (newDerivationName == priorToToday) PriorToToday (days)
          else if (newDerivationName == noRestriction) NoRestriction
          else NoTasks
        model.update (model.list.copy (derivation = Some (newDerivation)))
      }
      def setDays (newDays: Int) =
        model.list.derivation.map { _ match {
          case PriorToToday (oldDays) =>
            model.update (model.list.copy (derivation = Some (PriorToToday (newDays))))
          case _ =>
            Callback.empty
        }}.getOrElse (Callback.empty)
      val current = model.list.derivation.map { _ match {
        case NoRestriction => noRestriction
        case NoTasks => noTasks
        case PriorToToday (_) => priorToToday
      }}.getOrElse ("No derivation set")

      <.div (bss.row,
        <.div (bss.columns (1),
          <.label (^.`for` := "derivation", "Derivation")
        ),
        <.div (bss.columns (9),
          Dropdown[String] ("derivation", current, primary, options, s => s, setDerivation)
        ),
        <.div (bss.columns (2),
          <.div (bss.inputGroup.inputGroup,
            <.span (bss.inputGroup.button,
              <.button (^.tpe := "button", bss.buttonOpt (default), Icon.minus,
                ^.onClick --> { val newDays = (days - 1).max (0); scope.setState (newDays) >> setDays (newDays) }
              )
            ),
            <.input (^.tpe := "text", ^.name := "days", bss.formControl, bss.inputNumber, ^.value := days),
            <.span (bss.inputGroup.button,
              <.button (^.tpe := "button", bss.buttonOpt (default), Icon.plus,
                ^.onClick --> { val newDays = days + 1; scope.setState (newDays) >> setDays (newDays) }
              )
            )
          )
        )
      )
    }
  }

  private def priorDays (list: TaskList): Int = list.derivation match {
    case Some (PriorToToday (days)) => days
    case _ => 0
  }

  val component = ReactComponentB[Props]("EditDerivation")
    .initialState_P (p => priorDays (p.list))
    .renderBackend[Backend]
    .build

  def apply (list: TaskList, update: TaskList => Callback) =
    component (Props (list, update))
}
