package sheetkram.model

class SheetAccessor( _sheet : Option[ Sheet ] ) {

  def sheet : Option[ Sheet ] = _sheet

  def cellAt( colIdx : Int, rowIdx : Int ) : Option[ Cell ] =
    sheet match {
      case Some( s ) => if ( colIdx < s.columns.size && rowIdx < s.rows.size ) Some( s.columns( colIdx ).cells( rowIdx ) ) else None
      case None      => None
    }

  def cellsFrom( colIdx : Int, rowIdx : Int ) : CellRangeAccessor = new CellRangeAccessor( sheet, colIdx, rowIdx )

  def columnAt( colIdx : Int ) : Option[ Column ] =
    sheet match {
      case Some( s ) => if ( colIdx >= s.columns.size ) None else Some( s.columns( colIdx ) )
      case None      => None
    }

  def rowAt( rowIdx : Int ) : Option[ Row ] =
    sheet match {
      case Some( s ) => if ( rowIdx >= s.rows.size ) None else Some( s.rows( rowIdx ) )
      case None      => None
    }
}
