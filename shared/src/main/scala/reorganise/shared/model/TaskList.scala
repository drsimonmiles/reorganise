package reorganise.shared.model

case class TaskList (id: Long, name: String, order: Vector[Long], derivation: Option[Derivation])
