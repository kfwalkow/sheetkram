package sheetkram.model

import org.scalatest.FunSuite

class WorkbookTest extends FunSuite {

  test( "Create empty Workbook" ) {
    val workbook : Workbook = Workbook()
    assert( workbook.sheets.size === 0 )
  }

  test( "Create Workbook and append three Sheets" ) {
    val workbook : Workbook = Workbook().appendSheet( "Sheet 0" ).appendSheet( "Sheet 1" ).appendSheet( "Sheet 2" )
    assert( workbook.sheets.size === 3 )
  }

  test( "Create Workbook, append one Sheet and set a text value" ) {
    val sheetName = "Sheet Zero"
    val cellValue = "Klonk!"
    val workbook : Workbook = Workbook().appendSheet( sheetName ).updateCell( 0, 3, 3, TextCell( cellValue ) )
    assert( workbook.sheets.size === 1 )
    assert( workbook.sheetByName( sheetName ).exists { _.name === sheetName } )
    assert( workbook.sheet( 0 ).exists { _.cell( 3, 3 ).exists { _.valueAsText === cellValue } } )
  }

  test( "Create Workbook and set a text value" ) {
    val cellValue = "Klonk!"
    val workbook : Workbook = Workbook().updateCell( 0, 3, 3, TextCell( cellValue ) )
    assert( workbook.sheets.size === 1 )
    assert( workbook.sheet( 0 ).exists { _.cell( 3, 3 ).exists { _.valueAsText === cellValue } } )
  }

  test( "Create Workbook and set a few text values" ) {
    val cellValueA = "A"
    val cellValueB = "B"
    val cellValueC = "C"
    val cellValueD = "D"
    val cellValueE = "E"
    val workbook : Workbook = Workbook()
      .updateCell( 0, 3, 3, TextCell( cellValueA ) )
      .updateCell( 0, 3, 1, TextCell( cellValueB ) )
      .updateCell( 0, 1, 3, TextCell( cellValueC ) )
      .updateCell( 0, 5, 3, TextCell( cellValueD ) )
      .updateCell( 0, 3, 5, TextCell( cellValueE ) )
    assert( workbook.sheets.size === 1 )
    assert( workbook.sheet( 0 ).exists { _.columns.size === 6 } )
    assert( workbook.sheet( 0 ).exists { _.rows.size === 6 } )
    assert( workbook.sheet( 0 ).exists { _.cell( 3, 3 ).exists { _.valueAsText === cellValueA } } )
    assert( workbook.sheet( 0 ).exists { _.cell( 3, 1 ).exists { _.valueAsText === cellValueB } } )
    assert( workbook.sheet( 0 ).exists { _.cell( 1, 3 ).exists { _.valueAsText === cellValueC } } )
    assert( workbook.sheet( 0 ).exists { _.cell( 5, 3 ).exists { _.valueAsText === cellValueD } } )
    assert( workbook.sheet( 0 ).exists { _.cell( 3, 5 ).exists { _.valueAsText === cellValueE } } )
  }

}
