package sheetkram.io.odf

import org.scalatest.FunSuite
import sheetkram.model.Workbook
import java.io.File

class ReadBiggerOdfTest extends FunSuite {

  private def workbook : Workbook = ReadOdf.fromFile( new File( "src/test/resources/sheetkram-test-2.ods" ) )

  test( "Read file and check evalues of column A" ) {
    var i : Int = 0
    workbook.sheet( 0 ).foreach {
      _.columns( 0 ).cells.foreach { c =>
        c.valueAsText === "A" + i
        i = i + 1
      }
    }
  }

}
