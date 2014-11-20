package sheetkram.model

import scala.collection.SortedMap

case class SheetPosition( idx : Int )

case class Sheet private (
  position : SheetPosition,
  name : String,
  private val cols : Map[ Int, Map[ Int, Cell ] ],
  private val rows : Map[ Int, Map[ Int, Cell ] ] ) {

  def cell( colIdx : Int, rowIdx : Int ) : Option[ Cell ] =
    if ( colIdx < cols.size && rowIdx < rows.size ) Some( cols( colIdx )( rowIdx ) ) else None

  def column( colIdx : Int ) : Option[ Map[ Int, Cell ] ] =
    cols.get( colIdx )

  def row( rowIdx : Int ) : Option[ Map[ Int, Cell ] ] =
    rows.get( rowIdx )

  def allRows : Map[ Int, Map[ Int, Cell ] ] = rows

  def createOrUpdateCell( cell : Cell ) : Sheet = {
    val colIdx = cell.position.colIdx
    val rowIdx = cell.position.rowIdx
    copy(
      cols = cols + ( colIdx -> ( cols.getOrElse( colIdx, Map() ) + ( rowIdx -> cell ) ) ),
      rows = rows + ( rowIdx -> ( rows.getOrElse( rowIdx, Map() ) + ( colIdx -> cell ) ) )
    )
  }

}

object Sheet {

  def apply( sheetPos : SheetPosition, name : String ) : Sheet = Sheet( sheetPos, name, Map(), Map() )

}

