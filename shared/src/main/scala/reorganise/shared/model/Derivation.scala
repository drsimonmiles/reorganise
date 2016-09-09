package reorganise.shared.model

sealed trait Derivation {
  def name: String
}

case object NoTasks extends Derivation {
  val name = "No tasks"
}

case object NoRestriction extends Derivation {
  val name = "All tasks"
}

case class PriorToToday (days: Int) extends Derivation {
  val name = "Prior to today"
}
