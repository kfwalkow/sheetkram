package sheetkram.model

import scala.collection.SortedMap

case class SheetPosition( idx : Int )

case class Sheet private (
  position : SheetPosition,
  name : String,
  private val cols : Map[ Int, Sheet.Column ],
  private val rows : Map[ Int, Sheet.Row ] ) {

  //  private val cols2 : IndexedSeq[ IndexedSeq[ Cell ] ] = IndexedSeq()

  def cell( colIdx : Int, rowIdx : Int ) : Option[ Cell ] =
    if ( colIdx < cols.size && rowIdx < rows.size ) Some( cols( colIdx )( rowIdx ) ) else None

  def column( colIdx : Int ) : Option[ Sheet.Column ] =
    cols.get( colIdx )

  def row( rowIdx : Int ) : Option[ Sheet.Row ] =
    rows.get( rowIdx )

  def allColumns : Map[ Int, Sheet.Column ] = cols

  def allRows : Map[ Int, Sheet.Row ] = rows

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

  type Column = Map[ Int, Cell ]
  type Row = Map[ Int, Cell ]

}

