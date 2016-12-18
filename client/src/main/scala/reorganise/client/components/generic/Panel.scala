package reorganise.client.components.generic

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactComponentB, ReactNode}
import reorganise.client.model.ModelPoint
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

object Panel {
  @inline private def bss = GlobalStyles.bootstrapStyles

  def component = ReactComponentB[ModelPoint[_, String]]("Panel")
    .renderPC ((_, p, c) =>
      <.div (bss.panel,
        <.div (bss.panelHeading, p.value),
        <.div (bss.panelBody, c)
      )
    ).build

  def apply[Model <: AnyRef] (heading: ModelPoint[Model, String], children: ReactNode*) =
    component (heading, children)
}
