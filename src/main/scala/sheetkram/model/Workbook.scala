package sheetkram.model

case class Workbook private (
  private val sheets : Map[ SheetPosition, Sheet ] ) {

  def sheet( idx : Int ) : Sheet = sheets( SheetPosition( idx ) )

  def sheetByName( name : String ) : Option[ Sheet ] = sheets.values.find( _.name == name )

  def sheetForUpdate( idx : Int ) : Sheet.Updater = Sheet.Updater( sheets( SheetPosition( idx ) ), this )

  def sheetForUpdateByName( name : String ) : Option[ Sheet.Updater ] = sheetByName( name ) match {
    case Some( s ) => Some( sheetForUpdate( s.position.idx ) )
    case None      => None
  }

  def updateSheet( sheet : Sheet ) : Workbook = copy( sheets = sheets + ( sheet.position -> sheet ) )

  def removeSheet( idx : Int ) : Workbook = copy( sheets = sheets - SheetPosition( idx ) )

}

object Workbook {

  def apply() : Workbook = Workbook( Map() )

}
