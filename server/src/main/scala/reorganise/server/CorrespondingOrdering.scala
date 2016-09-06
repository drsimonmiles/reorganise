package reorganise.server

class CorrespondingOrdering[T, U] (view: T => U, correspondingList: Seq[U]) extends Ordering[T] {
  def compare (x: T, y: T): Int =
    correspondingList.indexOf (view (x)) - correspondingList.indexOf (view (y))
}
