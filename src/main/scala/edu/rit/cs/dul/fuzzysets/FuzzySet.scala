package com.github.dataunitylab.fuzzysets

import scala.collection.immutable.HashMap

object FuzzySet {
  def apply[T](element: (T, Float)): FuzzySet[T] = {
    FuzzySet(List(element).toMap)
  }
}

final case class FuzzySet[T](
    val elements: Map[T, Float] = HashMap.empty[T, Float]
) {
  def +(other: FuzzySet[T]): FuzzySet[T] = {
    FuzzySet((elements.keySet ++ other.elements.keySet).map { key =>
      key -> elements
        .getOrElse(key, 0.0f)
        .max(other.elements.getOrElse(key, 0.0f))
    }.toMap)
  }

  def &(other: FuzzySet[T]): FuzzySet[T] = {
    FuzzySet(
      (elements.keySet ++ other.elements.keySet)
        .map { key =>
          key -> elements
            .getOrElse(key, 0.0f)
            .min(other.elements.getOrElse(key, 0.0f))
        }
        .filter(_._2 != 0.0f)
        .toMap
    )
  }

  def unary_! : FuzzySet[T] = {
    FuzzySet(
      elements
        .map { case (key, value) => key -> (1 - value) }
        .filter(_._2 != 0.0f)
    )
  }

  def contains(element: T): Float = elements.getOrElse(element, 0.0f)

  def size: Int = elements.size

  def add(element: T, degree: Float): FuzzySet[T] = {
    FuzzySet(
      elements + (element -> degree.max(elements.getOrElse(element, 0.0f)))
    )
  }

  def remove(element: T, degree: Float): FuzzySet[T] = {
    FuzzySet(
      (elements + (element -> degree.min(elements.getOrElse(element, 0.0f))))
        .filter(_._2 != 0.0f)
    )
  }

  def similarity(other: FuzzySet[T]): Float = {
    val allKeys = elements.keySet ++ other.elements.keySet
    allKeys.map { key =>
      val u = elements.getOrElse(key, 0.0f)
      val v = other.elements.getOrElse(key, 0.0f)
      if (u == 0.0f && v == 0.0f) {
        1
      } else {
        (u * v) / (u * u + v * v - u * v)
      }
    }.sum / allKeys.size
  }
}
