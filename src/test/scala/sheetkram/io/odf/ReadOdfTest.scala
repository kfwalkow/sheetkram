package sheetkram.io.odf

import org.scalatest.FunSuite
import java.io.File

class ReadOdfTest extends FunSuite {

  test( "Read basic file" ) {
    val workbook = ReadOdf.fromFile( new File( "src/test/resources/sheetkram-test-1.ods" ) )
    assert( workbook.sheet( 0 ).exists { _.cell( 0, 0 ).exists { _.valueAsText === "A1" } } )
    assertResult( "It's" )( workbook.sheet( 0 ).flatMap { _.cell( 1, 6 ) }.map { _.valueAsText }.getOrElse( "" ) )
  }

}
