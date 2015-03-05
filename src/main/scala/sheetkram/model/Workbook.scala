package sheetkram.model

case class Workbook private (
  sheets : IndexedSeq[ Sheet ] ) {

  private def ensure( idx : Int ) : IndexedSeq[ Sheet ] = {
    if ( idx >= sheets.size )
      sheets ++ IndexedSeq.fill( idx - sheets.size + 1 )( Sheet( "Sheet " + idx ) )
    else
      sheets
  }

  def sheet( idx : Int ) : Option[ Sheet ] = if ( idx < sheets.size ) Some( sheets( idx ) ) else None

  def sheetByName( name : String ) : Option[ Sheet ] = sheets.find( _.name == name )

  def appendSheet( name : String ) : Workbook = copy( sheets :+ Sheet( name ) )

  def updateCell( idx : Int, colIdx : Int, rowIdx : Int, cell : Cell ) : Workbook = {
    val ensuredSheets = ensure( idx )
    copy( sheets = ensuredSheets.updated( idx, ensuredSheets( idx ).updateCell( colIdx, rowIdx, cell ) ) )
  }

}

object Workbook {

  def apply() : Workbook = Workbook( IndexedSeq() )

}
