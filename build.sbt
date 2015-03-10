organization := "sheetkram"

name := "sheetkram"

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies += "org.jopendocument" % "jOpenDocument" % "1.3"

libraryDependencies += "org.apache.poi" % "poi" % "3.11"

libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.11"

// src/main/scala only:
unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil

// src/test/scala only:
unmanagedSourceDirectories in Test := (scalaSource in Test).value :: Nil
