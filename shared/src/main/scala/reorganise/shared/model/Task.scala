package reorganise.shared.model

case class Task (id: Long, text: String, startDate: String, list: Long, recur: Option[Int], completed: Boolean)

