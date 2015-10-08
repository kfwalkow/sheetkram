organization := "sheetkram"

name := "sheetkram"

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies += "org.jopendocument" % "jOpenDocument" % "1.3"

libraryDependencies += "org.apache.poi" % "poi" % "3.11"

libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.11"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

// src/main/scala only:
unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil

// src/test/scala only:
unmanagedSourceDirectories in Test := (scalaSource in Test).value :: Nil
