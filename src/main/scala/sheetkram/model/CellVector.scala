package sheetkram.model

trait CellVector {
  def cells : IndexedSeq[ Cell ]
}

case class Column(
  cells : IndexedSeq[ Cell ] ) extends CellVector

case class Row(
  cells : IndexedSeq[ Cell ] ) extends CellVector
