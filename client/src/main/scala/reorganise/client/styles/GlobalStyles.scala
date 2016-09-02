package reorganise.client.styles

import scalacss.Defaults._

object GlobalStyles extends StyleSheet.Inline {
  import dsl._

  style (unsafeRoot ("body")(
    paddingTop (70.px))
  )

  val compact = style (paddingTop (0.px), paddingBottom (0.px), marginTop (0.px), marginBottom (0.px))

  val bootstrapStyles = new BootstrapStyles
}
