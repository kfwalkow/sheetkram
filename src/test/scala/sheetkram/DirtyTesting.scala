package sheetkram

import java.io.File
import java.util.Date
//import sheetkram.io.odf.WriteOdf
import java.nio.file.Files
import java.nio.file.Paths
import sheetkram.model.Workbook
import sheetkram.io.odf.ReadOdf
import sheetkram.model.TextCell

object DirtyTesting {

  def main( args : Array[ String ] ) : Unit = {

    //    var workbook : Workbook = ReadOdf.fromFile( new File( "src/test/resources/simple1.ods" ) )

    //    workbook = workbook.Sheet( 0 ).updateCell( Cell( 1, 3, "HUHU" ) )
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
    //    println( "Spalte 1: " + workbook.sheet( 0 ).get.column( 1 ).get.map { _._2.valueAsText }.mkString( ", " ) )

    var Workbook : Workbook = ReadOdf.fromFile( new File( "src/test/resources/simple1.ods" ) )
    println( "Zeile 0: " + Workbook.sheet( 0 ).get.row( 0 ).get.cells.map { _.valueAsText }.mkString( ", " ) )
    println( "Zeile 1: " + Workbook.sheet( 0 ).get.row( 1 ).get.cells.map { _.valueAsText }.mkString( ", " ) )
    println( "Spalte 1: " + Workbook.sheet( 0 ).get.column( 1 ).get.cells.map { _.valueAsText }.mkString( ", " ) )

    Workbook = Workbook.updateCell( 0, 10, 2, TextCell( "HUHU" ) )
    println( "Zelle X: " + Workbook.sheet( 0 ).get.cell( 10, 2 ).get.valueAsText )
    //    Files.createFile( Paths.get( "src/test/resources/simple1_out.ods" ) )
    //    val Workbook : Workbook = workbook.createSheet( 1, "Moin" ).
    //      updateSheet( 1, TextCell( CellPosition( 2, 2 ), "HAAAAAAAAAAAAAAAAALLOOO" ) )
    //    WriteOdf.toFile( Workbook, new File( "src/test/resources/simple1_out.ods" ) )

  }

}
