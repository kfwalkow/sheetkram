package sheetkram.io.odf

import org.scalatest.FunSuite
import java.io.File
import sheetkram.model.Workbook

class ReadDirtyOdfTest extends FunSuite {

  private def workbook : Workbook = ReadOdf.fromFile( new File( "src/test/resources/documentfoundation-bug-80813.ods" ) )

  //  test( "Check value in Sheet1 B7" ) {
  //    assert( workbook.sheet( 0 ).exists { _.cell( 1, 6 ).exists { _.valueAsText === "ATM" } } )
  //  }

}
