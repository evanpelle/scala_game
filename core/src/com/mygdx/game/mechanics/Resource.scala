package com.mygdx.game.mechanics

/**
  * Created by evan on 3/26/16.
  */
class Resource(val gold: Double, val iron: Double, val wood: Double, val stone: Double) {

  def +(toAdd: Resource) = {
    Resource(gold + toAdd.gold, iron + toAdd.iron, wood + toAdd.wood, stone + toAdd.stone)
  }

  def -(toAdd: Resource) = {
    Resource(gold - toAdd.gold, iron - toAdd.iron, wood - toAdd.wood, stone - toAdd.stone)
  }

  /**
    * every resource must be greater to be greater
    *
    */
  def >(toComp: Resource) = {
    this.gold > toComp.gold &&
    this.iron > toComp.iron &&
    this.wood > toComp.wood &&
    this.stone > toComp.wood
  }

  def >=(toComp: Resource) = {
    this > toComp || this.equals(toComp)
  }

  def <(toComp: Resource) = {
    this.gold < toComp.gold &&
    this.iron < toComp.iron &&
    this.wood < toComp.wood &&
    this.stone < toComp.wood
  }

  def <=(toComp: Resource) = {
    this < toComp || this.equals(toComp)
  }

  def isPositive = {
    this.gold > 0 &&
    this.iron > 0 &&
    this.wood > 0 &&
    this.stone > 0
  }

  override def equals(any: Any) = any match {
    case res: Resource => {
      this.gold == res.gold && this.iron == res.iron &&
      this.wood == res.wood && this.stone == res.stone
    }
    case _ => false
  }

  override def toString: String = {
    "gold: " + this.gold +
    " iron: " + this.iron +
    " wood: " + this.wood +
    " stone: " + this.stone
  }

}

object Resource {
  def apply(gold: Double = 0, iron: Double = 0, wood: Double = 0, stone: Double = 0) = {
    new Resource(gold, iron, wood, stone)
  }

  def all(all: Double) = {
    new Resource(all, all, all, all)
  }

  def apply() = {
    new Resource(0, 0, 0, 0)
  }
}
