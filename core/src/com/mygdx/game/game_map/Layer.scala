package com.mygdx.game.game_map


/**
  * Created by evan on 3/12/16.
  */
object Layer extends Enumeration {
  type Layer = Value
  val landscape = Value(0)
  val terrain = Value(1)
  val structure = Value(2)
  val person = Value(3)
  val marker = Value(4)
  val overlay = Value(5)
  val anyLayer = Value(6)
}
