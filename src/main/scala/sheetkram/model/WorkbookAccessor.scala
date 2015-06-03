package sheetkram.model

class WorkbookAccessor( workbook : Workbook ) {

  def sheetByIndex( sheetIdx : Int ) : SheetAccessor = {
    val sheet = if ( sheetIdx < workbook.sheets.size ) Some( workbook.sheets( sheetIdx ) ) else None
    new SheetAccessor( sheet )
  }

  def sheetByName( sheetName : String ) : SheetAccessor = {
    val sheet = workbook.sheets.find( _.name == sheetName )
    new SheetAccessor( sheet )
  }

}
