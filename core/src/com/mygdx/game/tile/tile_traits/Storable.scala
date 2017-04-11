package com.mygdx.game.tile.tile_traits

import com.mygdx.game.game_map.tile.TileCell
import com.mygdx.game.tile.ITile

/**
  * Created by evan on 4/12/16.
  */
trait Storable {

  def store: Store

  class Store {

    def canBeStored(tc: TileCell): Boolean = {
      tc
        .getAllTiles()
        .flatten
        .exists(this.canBeStored)
    }

    def canBeStored(tile: ITile): Boolean = tile match {
      case toBoard: ITile with Boardable => {
        toBoard.board.canStore
      }
      case _ => false
    }

  }

}
