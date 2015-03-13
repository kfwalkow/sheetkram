package sheetkram.io.odf

import org.scalatest.FunSuite
import java.io.File
import sheetkram.model.Workbook

class ReadBasicOdfTest extends FunSuite {

  private def workbook : Workbook = ReadOdf.fromFile( new File( "src/test/resources/sheetkram-test-1.ods" ) )

  test( "Read file and check existence of value" ) {
    assert( workbook.sheet( 0 ).exists { _.cell( 1, 6 ).exists { _.valueAsText === "It's" } } )
  }

  test( "Read file and extract value" ) {
    assertResult( "just" ) {
      workbook.sheet( 0 ).flatMap { _.cell( 2, 6 ) }.map { _.valueAsText }.getOrElse( "" )
    }
  }

  test( "Read file, check existence of cell and iterate to the value" ) {
    workbook.sheet( 0 ).exists { s => s.columns.size > 3 && s.rows.size > 6 }
    workbook.sheet( 0 ).foreach { s =>
      s.cell( 3, 6 ).foreach { c =>
        assertResult( "a test!" ) { c.valueAsText }
      }
    }
  }

}
