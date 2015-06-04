package sheetkram.model

case class Workbook private (
    sheets : IndexedSeq[ Sheet ] ) {

  private def ensure( idx : Int ) : IndexedSeq[ Sheet ] = {
    if ( idx >= sheets.size )
      sheets ++ IndexedSeq.fill( idx - sheets.size + 1 )( Sheet( "Sheet " + idx ) )
    else
      sheets
  }

  def appendSheet( name : String ) : Workbook = copy( sheets :+ Sheet( name ) )

  def updateCell( idx : Int, colIdx : Int, rowIdx : Int, cell : Cell ) : Workbook = {
    val ensuredSheets = ensure( idx )
    copy( sheets = ensuredSheets.updated( idx, ensuredSheets( idx ).updateCell( colIdx, rowIdx, cell ) ) )
  }

  def get : WorkbookAccessor = new WorkbookAccessor( this )

}

object Workbook {

  def apply() : Workbook = Workbook( IndexedSeq() )

}
