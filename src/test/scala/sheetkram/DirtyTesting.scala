package sheetkram

import java.io.File
import java.util.Date
import sheetkram.io.odf.ReadOdf
import sheetkram.io.odf.WriteOdf
import sheetkram.model.Cell
import sheetkram.model.Workbook
import sheetkram.model.CellPosition
import sheetkram.model.TextCell
import java.nio.file.Files
import java.nio.file.Paths

object DirtyTesting {

  def main( args : Array[ String ] ) : Unit = {

    val list : scala.collection.mutable.IndexedSeq[ Int ] = scala.collection.mutable.IndexedSeq()
    list( 2 ) = 20
    list( 5 ) = 50
    list.splitAt( 3 )
    println( "list: " + list.mkString( ", " ) )

    var workbook : Workbook = ReadOdf.fromFile( new File( "src/test/resources/simple1.ods" ) )

    //    workbook = workbook.sheet2( 0 ).updateCell( Cell( 1, 3, "HUHU" ) )
    //    println( workbook.sheet( 0 ).cell( 1, 4 ) )

    //    for ( x <- 0 to 1 )
    //      for ( y <- 0 to 4 ) {
    //        // by sheet idx:
    //        workbook.sheet( 0 ).foreach { s0 => println( x + ", " + y + ": " + s0.cell( x, y ).map { c : Cell => c.valueAsText } ) }
    //        // by sheet name:
    //        workbook.sheetByName( "Tabelle1" ).foreach { t1 =>
    //          t1.cell( x, y ).foreach {
    //            _.valueAsDate.foreach { d : Date =>
    //              println( "Date: " + d )
    //            }
    //          }
    //        }
    //      }

    //    println( "Zeile 0: " + workbook.sheet( 0 ).get.row( 0 ).get.map { _._2.valueAsText }.mkString( ", " ) )
    //    println( "Zeile 1: " + workbook.sheet( 0 ).get.row( 1 ).get.map { _._2.valueAsText }.mkString( ", " ) )
    //    println( "Spalte 0: " + workbook.sheet( 0 ).get.column( 0 ).get.map { _._2.valueAsText }.mkString( ", " ) )

    //    Files.createFile( Paths.get( "src/test/resources/simple1_out.ods" ) )
    val workbook2 : Workbook = workbook.createSheet( 1, "Moin" ).
      updateSheet( 1, TextCell( CellPosition( 2, 2 ), "HAAAAAAAAAAAAAAAAALLOOO" ) )
    WriteOdf.toFile( workbook2, new File( "src/test/resources/simple1_out.ods" ) )

  }

}
