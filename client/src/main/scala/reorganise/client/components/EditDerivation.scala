package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.{Dropdown, Icon}
import reorganise.client.model.UpdateList
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles
import reorganise.shared.model.{NoRestriction, NoTasks, PriorToToday, TaskList}
import scalacss.ScalaCssReact._

class EditDerivation {
  @inline private def bss = GlobalStyles.bootstrapStyles

  val component = ReactComponentB[ModelProxy[TaskList]] ("EditDerivation")
    .render_P { model =>
      val noTasks = "No tasks"
      val noRestriction = "All tasks"
      val priorToToday = "Prior to today"
      val options = Vector (noTasks, noRestriction, priorToToday)
      def setDerivation (newDerivationName: String) = {
        val newDerivation =
          if (newDerivationName == priorToToday) PriorToToday (0)//(getDays ())
          else if (newDerivationName == noRestriction) NoRestriction
          else NoTasks
        model.dispatch (UpdateList (model.value.copy (derivation = Some (newDerivation))))
      }
      val current = model.value.derivation.map { _ match {
        case NoRestriction => noRestriction
        case NoTasks => noTasks
        case PriorToToday (days) => priorToToday
      }}.getOrElse ("No derivation set")

      <.div (bss.formGroup,
        <.label (^.`for` := "derivation", "Derivation"),
        Dropdown[String] ("derivation", current, primary, options, s => s, setDerivation),
        <.div (bss.inputGroup.inputGroup,
          <.span (bss.inputGroup.button,
            <.button (^.tpe := "button", bss.buttonOpt (default), bss.buttonNumber, bss.dataType := "minus", bss.dataField := "days", Icon.minus)
          ),
          <.input (^.tpe := "text", ^.name := "days", bss.formControl, bss.inputNumber, ^.value := "1", ^.min := "0", ^.max := "32767"),
          <.span (bss.inputGroup.button,
            <.button (^.tpe := "button", bss.buttonOpt (default), bss.buttonNumber, bss.dataType := "plus", bss.dataField := "days", Icon.plus)
          )
        )
      )
    }.build

  def apply (model: ModelProxy[TaskList]) =
    component (model)
}
