package reorganise.client.model

sealed trait Feature {
  def label: String
}

sealed trait TaskFeature extends Feature

sealed trait ListFeature extends Feature

case object LabelFeature extends TaskFeature {
  val label = "Label"
}

case object StartFeature extends TaskFeature {
  val label = "Start date"
}

case object RecurFeature extends TaskFeature {
  val label = "Recurrence"
}

case object OrderFeature extends TaskFeature with ListFeature {
  val label = "Order"
}

case object DerivationFeature extends ListFeature {
  val label = "Derivation"
}

case object PriorDaysFeature extends ListFeature {
  val label = "Prior Days"
}
