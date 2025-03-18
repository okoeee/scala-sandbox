scalaVersion := "2.13.12"

name := "scala-sandbox"
organization := "ch.epfl.scala"
version := "1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"

javaOptions ++= Seq(
  "-Xmx4G"
)
