package sheetkram.model

case class SheetPosition( idx : Int )

case class Sheet private (
  position : SheetPosition,
  name : String,
  private val cells : Map[ CellPosition, Cell ] ) {

  def cell( colIdx : Int, rowIdx : Int ) : Cell = cells( CellPosition( colIdx, rowIdx ) )

  //  def column( colIdx : Int ) = cells.filterKeys( _.colIdx == colIdx )

  private def updateCell( wb : Workbook, cell : Cell ) : Workbook = {
    wb.updateSheet( copy( cells = cells + ( cell.position -> cell ) ) )
  }

}

object Sheet {

  def apply( pos : Int, name : String ) : Sheet = Sheet( SheetPosition( pos ), name, Map() )

  case class Updater( s : Sheet, w : Workbook ) {
    val updateCell = s.updateCell( w, _ : Cell )
  }

}

