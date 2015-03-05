package sheetkram

import java.io.File
import java.util.Date
import sheetkram.io.odf.WriteOdf
import java.nio.file.Files
import java.nio.file.Paths
import sheetkram.model.Workbook
import sheetkram.io.odf.ReadOdf
import sheetkram.model.TextCell
import sheetkram.io.ooxml.WriteXlsx
import sheetkram.io.ooxml.ReadXlsx

object DirtyTesting {

  def main( args : Array[ String ] ) : Unit = {

    var workbook : Workbook = ReadOdf.fromFile( new File( "src/test/resources/simple1.ods" ) )
    println( "Zeile 0: " + workbook.sheet( 0 ).get.row( 0 ).get.cells.map { _.valueAsText }.mkString( ", " ) )
    println( "Zeile 1: " + workbook.sheet( 0 ).get.row( 1 ).get.cells.map { _.valueAsText }.mkString( ", " ) )
    println( "Spalte 1: " + workbook.sheet( 0 ).get.column( 1 ).get.cells.map { _.valueAsText }.mkString( ", " ) )

    workbook = workbook.updateCell( 0, 10, 2, TextCell( "HUHU" ) )
    println( "Zelle X: " + workbook.sheet( 0 ).get.cell( 10, 2 ).get.valueAsText )

    workbook = workbook.appendSheet( "Moin" )
    workbook = workbook.updateCell( 1, 2, 2, TextCell( "Tachchen!" ) )

    Files.deleteIfExists( Paths.get( "../simple1_out.ods" ) )
    Files.createFile( Paths.get( "../simple1_out.ods" ) )
    WriteOdf.toFile( workbook, new File( "../simple1_out.ods" ) )

    Files.deleteIfExists( Paths.get( "../simple1_out.xlsx" ) )
    Files.createFile( Paths.get( "../simple1_out.xlsx" ) )
    WriteXlsx.toFile( workbook, new File( "../simple1_out.xlsx" ) )

    val workbook2 : Workbook = ReadXlsx.fromFile( new File( "../simple1_out.xlsx" ) )
    println( "Zelle X: " + workbook2.sheet( 0 ).get.cell( 10, 2 ).get.valueAsText )

  }

}
