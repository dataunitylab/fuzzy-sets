import Dependencies._

ThisBuild / scalaVersion     := "2.11.12"
ThisBuild / versionScheme     := Some("early-semver")
ThisBuild / organization     := "edu.rit.cs.dul"
ThisBuild / organizationName := "Data Unity Lab"

Global / onChangedBuildSource := ReloadOnSourceChanges

val nonConsoleCompilerOptions = Seq(
  "-feature",
  "-Xfatal-warnings",
  "-Ywarn-unused-import",
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
  Wart.TraversableOps,
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
