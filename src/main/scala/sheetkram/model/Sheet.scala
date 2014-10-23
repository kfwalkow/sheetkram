package sheetkram.model

import scala.collection.SortedMap

case class SheetPosition( idx : Int )

case class Sheet private (
  position : SheetPosition,
  name : String,
  private val cells : Map[ CellPosition, Cell ],
  private val cols : Map[ ColumnPosition, Column ],
  private val rows : Map[ RowPosition, Row ] ) {

  def cell( colIdx : Int, rowIdx : Int ) : Cell = cells( CellPosition( colIdx, rowIdx ) )

  def column( colIdx : Int ) : Column = {
    val colPos = ColumnPosition( colIdx )
    if ( cols.contains( colPos ) ) cols( colPos ) else Column( colPos, Map() )
  }

  def row( rowIdx : Int ) : Row = {
    val rowPos = RowPosition( rowIdx )
    if ( rows.contains( rowPos ) ) rows( rowPos ) else Row( rowPos, Map() )
  }

  def allRows : Seq[ Row ] = rows.values.toSeq

  private def updateCell( wb : Workbook, cell : Cell ) : Workbook = {
    val colIdx = cell.position.colIdx
    val rowIdx = cell.position.rowIdx
    wb.updateSheet( copy(
      cells = cells + ( cell.position -> cell ),
      cols = cols + ( ColumnPosition( colIdx ) -> Column(
        ColumnPosition( colIdx ), column( colIdx ).cells + ( RowPosition( rowIdx ) -> cell ) ) ),
      rows = rows + ( RowPosition( rowIdx ) -> Row(
        RowPosition( rowIdx ), row( rowIdx ).cells + ( ColumnPosition( colIdx ) -> cell ) ) ) ) )
  }

}

object Sheet {

  def apply( pos : Int, name : String ) : Sheet = Sheet( SheetPosition( pos ), name, Map(), Map(), Map() )

  case class Updater( s : Sheet, w : Workbook ) {
    val updateCell = s.updateCell( w, _ : Cell )
  }

}

