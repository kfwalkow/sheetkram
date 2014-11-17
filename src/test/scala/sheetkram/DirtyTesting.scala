package sheetkram

import java.io.File
import java.util.Date

import sheetkram.load.ReadOdf
import sheetkram.model.Workbook

object DirtyTesting {

  def main( args : Array[ String ] ) : Unit = {

    var workbook : Workbook = ReadOdf.fromFile( new File( "src/test/resources/simple1.ods" ) )

    //    workbook = workbook.sheet2( 0 ).updateCell( Cell( 1, 3, "HUHU" ) )
    //    println( workbook.sheet( 0 ).cell( 1, 4 ) )

    for ( x <- 0 to 1 )
      for ( y <- 0 to 4 ) {
        println( x + ", " + y + ": " + workbook.sheet( 0 ).cell( x, y ).valueAsText )
        workbook.sheetByName( "Tabelle1" ).foreach { t1 =>
          t1.cell( x, y ).valueAsDate.foreach { d : Date =>
            println( "Date: " + d )
          }
        }
      }
  }

}
