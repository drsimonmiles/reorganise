package reorganise.shared.model

sealed trait Derivation
case object NoRestriction extends Derivation
case class PriorToToday (days: Int) extends Derivation
