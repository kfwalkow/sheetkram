package sheetkram.model

class CellRangeAccessor( sheet : Option[ Sheet ], fromColIdx : Int, fromRowIdx : Int ) {

  def to( colIdx : Int, rowIdx : Int ) : Seq[ Cell ] = {
    sheet match {
      case Some( s ) => {
        s.rows.slice( fromRowIdx, Math.min( rowIdx + 1, s.rows.size ) ).map { r : Row =>
          r.cells.slice( fromColIdx, Math.min( colIdx + 1, r.cells.size ) )
        }.flatten
      }
      case None => Seq()
    }
  }

}
