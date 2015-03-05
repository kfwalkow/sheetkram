package sheetkram.model

case class Sheet private (
  name : String,
  columns : IndexedSeq[ Column ],
  rows : IndexedSeq[ Row ] ) {

  def cell( colIdx : Int, rowIdx : Int ) : Option[ Cell ] =
    if ( colIdx < columns.size && rowIdx < rows.size ) Some( columns( colIdx ).cells( rowIdx ) ) else None

  def column( colIdx : Int ) : Option[ Column ] =
    if ( colIdx >= columns.size ) None else Some( columns( colIdx ) )

  def row( rowIdx : Int ) : Option[ Row ] =
    if ( rowIdx >= rows.size ) None else Some( rows( rowIdx ) )

  def updateCell( colIdx : Int, rowIdx : Int, cell : Cell ) : Sheet = {
    val _columns : IndexedSeq[ Column ] =
      if ( colIdx >= columns.size ) {
        if ( rowIdx >= rows.size ) {
          columns.map { col => col.ensure( rowIdx ) } ++
            IndexedSeq.fill( colIdx - columns.size )( Column( rowIdx + 1 ) ) :+
            Column( rowIdx + 1 ).updateCell( rowIdx, cell )
        } else {
          columns ++
            IndexedSeq.fill( colIdx - columns.size )( Column( rows.size ) ) :+
            Column( rows.size ).updateCell( rowIdx, cell )
        }
      } else {
        if ( rowIdx >= rows.size ) {
          columns.map { col => col.ensure( rowIdx ) }.
            updated( colIdx, columns( colIdx ).updateCell( rowIdx, cell ) )
        } else {
          columns.updated( colIdx, columns( colIdx ).updateCell( rowIdx, cell ) )
        }
      }

    val _rows : IndexedSeq[ Row ] =
      if ( rowIdx >= rows.size ) {
        if ( colIdx >= columns.size ) {
          rows.map { row => row.ensure( colIdx ) } ++
            IndexedSeq.fill( rowIdx - rows.size )( Row( colIdx + 1 ) ) :+
            Row( colIdx + 1 ).updateCell( colIdx, cell )
        } else {
          rows ++
            IndexedSeq.fill( rowIdx - rows.size )( Row( columns.size ) ) :+
            Row( columns.size ).updateCell( colIdx, cell )
        }
      } else {
        if ( colIdx >= columns.size ) {
          rows.map { row => row.ensure( colIdx ) }.
            updated( rowIdx, rows( rowIdx ).updateCell( colIdx, cell ) )
        } else {
          rows.updated( rowIdx, rows( rowIdx ).updateCell( colIdx, cell ) )
        }
      }

    copy( columns = _columns, rows = _rows )

  }

}

object Sheet {

  def apply( name : String ) : Sheet = Sheet( name, IndexedSeq(), IndexedSeq() )

}
