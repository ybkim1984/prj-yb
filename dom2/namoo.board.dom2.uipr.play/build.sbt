name := """namoo.board.dom2.uipr.play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

resolvers += "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "namoo.board" % "namoo.board.dom2.cp.pojo" % "0.0.1-SNAPSHOT",
  "namoo.board" % "namoo.board.dom2.da.mongojava" % "0.0.1-SNAPSHOT",
  javaWs
)
