package com.mygdx.game.game_map

/**
  * Created by evan on 3/15/16.
  */
object Direction extends Enumeration {
  type Direction = Value
  val North = Value(0)
  val South = Value(1)
  val NorthEast = Value(2)
  val NorthWest = Value(3)
  val SouthEast = Value(4)
  val SouthWest = Value(5)
}
