package sheetkram.model

case class Row private (
  cells : IndexedSeq[ Cell ] ) extends CellVector {

  def updateCell( colIdx : Int, cell : Cell ) : Row = copy( cells = doUpdateCell( colIdx, cell ) )

  def ensure( colIdx : Int ) : Row = copy( cells = doEnsure( colIdx ) )

}

object Row {

  def apply( colCount : Int ) : Row = Row( IndexedSeq.fill( colCount )( EmptyCell() ) )

}
