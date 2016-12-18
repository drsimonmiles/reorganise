package reorganise.client.components.generic

import diode.react.ModelProxy
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactComponentB, ReactNode}
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

object Panel {
  @inline private def bss = GlobalStyles.bootstrapStyles

  def component = ReactComponentB[ModelProxy[String]]("Panel")
    .renderPC ((_, p, c) =>
      <.div (bss.panel,
        <.div (bss.panelHeading, p.value),
        <.div (bss.panelBody, c)
      )
    ).build

  def apply (heading: ModelProxy[String], children: ReactNode*) =
    component (heading, children)
}
