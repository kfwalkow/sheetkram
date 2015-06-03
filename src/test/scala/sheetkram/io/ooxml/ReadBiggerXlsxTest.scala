package sheetkram.io.ooxml

import java.io.File

import org.scalatest.FunSuite

import sheetkram.model.Workbook

class ReadBiggerXlsxTest extends FunSuite {

  private def workbook : Workbook = ReadXlsx.fromFile( new File( "src/test/resources/sheetkram-test-2.xlsx" ) )

  test( "Read file and check evalues of column A" ) {
    var i : Int = 0
    workbook.access.sheetByIndex( 0 ).sheet.foreach {
      _.columns( 0 ).cells.foreach { c =>
        c.valueAsText === "A" + i
        i = i + 1
      }
    }
  }

}
