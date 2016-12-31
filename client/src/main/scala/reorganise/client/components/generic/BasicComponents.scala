package reorganise.client.components.generic

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactEventI, ReactKeyboardEventI, ReactNode}
import reorganise.client.components.generic.ComponentPatterns._
import reorganise.client.components.generic.FAIcon.{chevronCircleDown, chevronCircleUp, longArrowDown, longArrowUp, minus, plus}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

//noinspection TypeAnnotation
object BasicComponents {
  private def bss = GlobalStyles.bootstrapStyles

  val checkbox = editor[Boolean] ("Checkbox") { v =>
    <.input.checkbox (^.checked := v.value, ^.onChange --> v.set (!v.value))
  }

  def dropdown[Item] (fieldID: String, label: Item => String, style: AlertStyle, items: Seq[Item]) =
    editor [Item]("Dropdown") { v =>
      <.div (bss.dropdown, ^.id := fieldID,
        <.button (bss.buttonOpt (style), bss.dropdownButton, ^.tpe := "button", bss.dataToggle := "dropdown",
          label (v.value) + " ", <.span (bss.caret)),
        <.ul (bss.dropdownMenu,
          items.map (item => <.li (bss.listGroup.item, label (item), ^.onClick --> v.set (item)))
        )
      )
    }

  def focusedTextField (placeholder: String) = atomicEditor[String] ("FocusedTextField") { v =>
    <.input.text (bss.formControl, ^.id := "description", ^.value := v.state,
      ^.placeholder := placeholder,
      ^.onChange ==> { event: ReactEventI => v.set (event.target.value) },
      ^.onKeyPress ==> { event: ReactKeyboardEventI =>
        Callback (if (event.charCode == 13) event.currentTarget.blur ())
      },
      ^.onBlur ==> { _: ReactEventI => v.commit }
    )
  }

  def panel (heading: String, children: ReactNode*) =
    <.div (bss.panel, <.div (bss.panelHeading, heading), <.div (bss.panelBody, children))

  val quantityControl = editor[Int] ("QuantityControl") { p =>
    def button (icon: ReactNode, increment: Int) =
      <.span (bss.inputGroup.button,
        <.button (^.tpe := "button", bss.buttonOpt (default), icon,
          ^.onClick --> p.set ((p.model.value + increment).max (0))))
    <.div (bss.inputGroup.inputGroup,
      button (minus (), -1),
      <.input (bss.formControl, ^.tpe := "text", ^.name := "quantity", ^.value := p.value, ^.disabled := "yes"),
      button (plus (), 1)
    )
  }

  def reorderControl[Item] (item: Item) = editor[Vector[(Item, Boolean)]] ("ReorderControl") { p =>
    val order = p.value
    val index = order.indexWhere (_._1 == item)

    def moveUpAll =
      if (index > 0) (order (index) +: order.take (index)) ++ order.drop (index + 1)
      else order
    def moveUpOne =
      if (index > 0) {
        val prior = order.take (index).reverse.dropWhile (!_._2).size - 1
        (order.take (prior) :+ order (index)) ++ order.slice (prior, index) ++ order.drop (index + 1)
      } else order
    def moveDownOne =
      if (index >= 0 && index < order.size - 1) {
        val next = order.drop (index + 1).takeWhile (!_._2).size + index + 1
        (order.take (index) ++ order.slice (index + 1, next + 1) :+ order (index)) ++ order.drop (next + 1)
      } else order
    def moveDownAll =
      if (index >= 0 && index < order.size - 1) order.take (index) ++ order.drop (index + 1) :+ order (index)
      else order

    <.div (
      <.span (Button.small (p.set (moveUpAll),   longArrowUp ())),
      <.span (Button.small (p.set (moveUpOne),   chevronCircleUp ())),
      <.span (Button.small (p.set (moveDownOne), chevronCircleDown ())),
      <.span (Button.small (p.set (moveDownAll), longArrowDown ()))
    )
  }
}
