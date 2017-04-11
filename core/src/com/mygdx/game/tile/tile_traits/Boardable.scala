package com.mygdx.game.tile.tile_traits

import com.mygdx.game.tile.{Person, ITile}

/**
  * Created by evan on 4/11/16.
  * if an object can store other objects
  */
trait Boardable {

  this: ITile with Ownable =>

  def board: Board

  class Board(val maxCargoSize: Int = 4) {

    private var myCargo: Set[Person] = Set()
    def canStore = myCargo.size < maxCargoSize

    def getCargo = this.myCargo

    def cargoSize = this.myCargo.size

    def hasCargo = this.myCargo.nonEmpty

    def removeCargo(toRemove: Person) = {
      this.myCargo -= toRemove
    }

    def addCargo(toAdd: Person) = {
      if(this.canStore) {
        myCargo += toAdd
      } else {
        throw new Exception("can't store tile: " + toAdd)
      }
    }
  }


}
