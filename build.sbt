import Dependencies._

ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / versionScheme    := Some("early-semver")
ThisBuild / organization     := "io.github.dataunitylab"
ThisBuild / organizationName := "Data Unity Lab"

Global / onChangedBuildSource := ReloadOnSourceChanges

publishTo := sonatypePublishToBundle.value
publishMavenStyle := true
licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
import xerial.sbt.Sonatype._
sonatypeProjectHosting := Some(GitHubHosting("dataunitylab", "fuzzy-sets", "mmior@mail.rit.edu"))
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"

val nonConsoleCompilerOptions = Seq(
  "-feature",
  "-Xfatal-warnings",
  "-Ywarn-unused:imports",
  "-deprecation"
)

lazy val root = (project in file("."))
  .settings(
    name := "Fuzzy Sets",
    libraryDependencies += scalaTest % Test,
    scalacOptions ++= nonConsoleCompilerOptions
  )

wartremoverErrors ++= Seq(
  Wart.ArrayEquals,
  Wart.EitherProjectionPartial,
  Wart.Enumeration,
  Wart.ExplicitImplicitTypes,
  Wart.FinalCaseClass,
  Wart.MutableDataStructures,
  Wart.Null,
  Wart.Option2Iterable,
  Wart.OptionPartial,
  Wart.PublicInference,
  Wart.Recursion,
  Wart.Return,
  Wart.StringPlusAny,
  Wart.TryPartial,
  Wart.Var,
  Wart.While,
)

Compile / compile / wartremoverErrors += Wart.NonUnitStatements

Compile / console / scalacOptions := (console / scalacOptions)
  .value.filterNot(opt =>
    opt.contains("wartremover") ||
    nonConsoleCompilerOptions.contains(opt)
)

enablePlugins(GitHubPagesPlugin)
enablePlugins(GitVersioning)
enablePlugins(SiteScaladocPlugin)

gitHubPagesOrgName := "dataunitylab"
gitHubPagesRepoName := "fuzzy-sets"
gitHubPagesSiteDir := baseDirectory.value / "target/site"

git.remoteRepo := "git@github.com:dataunitylab/fuzzy-sets.git"
git.useGitDescribe := true
