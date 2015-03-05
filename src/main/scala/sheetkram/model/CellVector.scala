package sheetkram.model

trait CellVector {

  def cells : IndexedSeq[ Cell ]

  protected def doEnsure( idx : Int ) : IndexedSeq[ Cell ] = {
    if ( idx >= cells.size )
      cells ++ IndexedSeq.fill( idx - cells.size + 1 )( EmptyCell() )
    else
      cells
  }

  protected def doUpdateCell( idx : Int, cell : Cell ) : IndexedSeq[ Cell ] = {
    if ( idx >= cells.size )
      cells ++ IndexedSeq.fill( idx - cells.size )( EmptyCell() ) :+ cell
    else
      cells.updated( idx, cell )
  }

}
