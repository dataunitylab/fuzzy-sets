package edu.rit.cs.dul.fuzzysets

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FuzzySetSpec extends AnyFlatSpec with Matchers {
  behavior of "FuzzySet"

  it should "start empty" in {
    FuzzySet().size should be (0)
  }

  it should "report degree of membership" in {
    FuzzySet("foo" -> 0.2f).contains("foo") should be (0.2f)
  }

  it should "compute union" in {
    (FuzzySet("foo" -> 0.3f) + FuzzySet("foo" -> 0.2f)) should be (FuzzySet("foo" -> 0.3f))
  }

  it should "compute intersection" in {
    (FuzzySet("foo" -> 0.3f) & FuzzySet("foo" -> 0.2f)) should be (FuzzySet("foo" -> 0.2f))
  }

  it should "compute complement" in {
    (!FuzzySet("foo" -> 0.3f)) should be (FuzzySet("foo" -> 0.7f))
  }

  it should "compute equivalent sets as exactly similar" in {
    val set = FuzzySet("foo" -> 0.3f)
    set.similarity(set) should be (1.0f)
  }

  it should "compute disjoint sets as not at all similar" in {
    FuzzySet("foo" -> 0.3f).similarity(FuzzySet("bar" -> 1.0f)) should be (0.0f)
  }

  it should "compute similarity between sets with an overlapping element" in {
    FuzzySet("foo" -> 1.0f).similarity(FuzzySet(Map("foo" -> 1.0f, "bar" -> 0.5f))) should be (0.5f)
  }
}
