package sheetkram.model

case class Workbook private (
  private val sheets : IndexedSeq[ Sheet ] ) {

  def sheet( idx : Int ) : Option[ Sheet ] = if ( idx < sheets.size ) Some( sheets( idx ) ) else None

  def sheetByName( name : String ) : Option[ Sheet ] = sheets.find( _.name == name )

  def allSheets : IndexedSeq[ Sheet ] = sheets

  private def insertSheet( sheet : Sheet ) : Workbook =
    copy( sheets = ( sheets.take( sheet.position.idx ) :+ sheet ) ++ sheets.drop( sheet.position.idx + 1 ) )

  def createSheet( idx : Int, name : String ) : Workbook = {
    assert( idx >= 0 && idx <= sheets.size, "Invalid index for new sheet!" )
    insertSheet( Sheet( SheetPosition( idx ), name ) )
  }

  def updateSheet( idx : Int, cell : Cell ) : Workbook = {
    assert( idx >= 0 && idx < sheets.size, "Sheet with index " + idx + " does not exist!" )
    insertSheet( sheets( idx ).createOrUpdateCell( cell ) )
  }

  def updateSheet( sheet : Sheet, cell : Cell ) : Workbook =
    updateSheet( sheet.position.idx, cell )

  def removeSheet( idx : Int ) : Workbook = {
    assert( idx >= 0 && idx < sheets.size, "Sheet with index " + idx + " does not exist!" )
    copy( sheets = sheets.take( idx ) ++ sheets.drop( idx + 1 ) )
  }

}

object Workbook {

  def apply() : Workbook = Workbook( IndexedSeq() )

}
