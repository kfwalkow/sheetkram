package sheetkram.model

case class Column private (
  cells : IndexedSeq[ Cell ] ) extends CellVector {

  def updateCell( rowIdx : Int, cell : Cell ) : Column = copy( cells = doUpdateCell( rowIdx, cell ) )

  def ensure( rowIdx : Int ) : Column = copy( cells = doEnsure( rowIdx ) )

}

object Column {

  def apply( rowCount : Int ) : Column = Column( IndexedSeq.fill( rowCount )( EmptyCell() ) )

}
