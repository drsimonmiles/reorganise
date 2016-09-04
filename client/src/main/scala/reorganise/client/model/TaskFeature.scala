package reorganise.client.model

sealed trait TaskFeature {
  def label: String
}

case object ListFeature extends TaskFeature {
  val label = "List"
}

case object StartFeature extends TaskFeature {
  val label = "Start date"
}

case object RecurFeature extends TaskFeature {
  val label = "Recurrence"
}


