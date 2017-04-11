package com.mygdx.game.tile.tile_traits


/**
  * Created by evan on 4/10/16.
  */
trait Treadable {

  def tread: Tread

  class Tread(val pathEffort: Double = 1.0,
              val canWalkOn: Boolean = true,
              val canSailOn: Boolean = false) {}

}
