package com.mygdx.game.tile.tile_traits

import com.mygdx.game.game_map.tile.TileCell
import com.mygdx.game.tile.ITile

/**
  * Created by evan on 4/10/16.
  */
trait Combatable {

  this: Ownable =>

  def combat: Combat

  class Combat(val canAttack: Boolean = false,
               val canBeAttacked: Boolean = true,
               val maxHealth: Double = 100.0) {

    var currHealth = maxHealth
    var attack = 100.0
    var defense = 100.0

    def canAttackTile(tc: TileCell): Boolean = {
      tc
        .getAllTiles()
        .flatten
        .exists(canAttackTile)
    }

    // whether or not can attack tile, does not take range
    // into consideration
    def canAttackTile(toAttack: ITile): Boolean = toAttack match {
      case ta: ITile with Combatable with Ownable => {
        ta.owner != owner
      }
      case _ => false
    }
  }


}
