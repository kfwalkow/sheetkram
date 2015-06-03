package sheetkram.io.odf

import org.scalatest.FunSuite
import java.io.File
import sheetkram.model.Workbook

class ReadBasicOdfTest extends FunSuite {

  private def workbook : Workbook = ReadOdf.fromFile( new File( "src/test/resources/sheetkram-test-1.ods" ) )

  test( "Read file and check existence of value" ) {
    assert( workbook.access.sheetByIndex( 0 ).cellAt( 1, 6 ).exists { _.valueAsText === "It's" } )
  }

  test( "Read file and extract single value" ) {
    assertResult( "just" ) {
      workbook.access.sheetByIndex( 0 ).cellAt( 2, 6 ).map { _.valueAsText }.getOrElse( "" )
    }
  }

  test( "Read file, access sheet by name and extract single value" ) {
    assertResult( "just" ) {
      workbook.access.sheetByName( "Tab1" ).cellAt( 2, 6 ).map { _.valueAsText }.getOrElse( "" )
    }
  }

  test( "Read file and extract range of values I" ) {
    assertResult( Seq( "42", "56", "17", "23", "32", "1234" ).mkString ) {
      workbook.access.sheetByIndex( 0 ).cellsFrom( 5, 3 ).to( 7, 4 ).map { _.valueAsText }.mkString
    }
  }

  test( "Read file and extract range of values II" ) {
    assertResult( Seq( "A1", "It's", "just", "a test!" ).mkString ) {
      workbook.access.sheetByIndex( 0 ).cellsFrom( -5, -5 ).to( 3, 10000 ).map { _.valueAsText }.mkString
    }
  }

  test( "Read file and extract range of values III" ) {
    assertResult( Seq( "42", "56", "17", "23", "32", "1234", "M7", "a test!" ).mkString ) {
      workbook.access.sheetByIndex( 0 ).cellsFrom( 3, 3 ).to( 12, 6 ).map { _.valueAsText }.mkString
    }
  }

}
