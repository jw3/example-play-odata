name := "playodata"

version := "1.0"

scalaVersion := "2.11.8"


// play configuration

lazy val root = (project in file(".")).enablePlugins(PlayScala)

//routesGenerator := InjectedRoutesGenerator

//sourceDirectories in TwirlKeys.compileTemplates := (unmanagedSourceDirectories in Compile).value


libraryDependencies ++= {
  val odataVersion = "2.1.0"
  val akkaVersion = "2.4.9"


  Seq(
    ws, filters,

    "com.rxthings" %% "akka-injects" % "0.5",

    "com.sdl" % "odata_api" % odataVersion,
    "com.sdl" % "odata_edm" % odataVersion,
    "com.sdl" % "odata_common" % odataVersion,

    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaVersion,

    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,


    "com.sdl" % "odata_test" % odataVersion % Test,
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
  )
}




scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-encoding", "UTF-8",

  "-feature",
  "-unchecked",
  "-deprecation",

  "-language:postfixOps",
  "-language:implicitConversions",

  "-Xfatal-warnings",
  "-Xlint:_"
)
