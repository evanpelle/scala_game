package com.mygdx.game.tile.tile_traits

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.event.{TurnEvent, GameEvent}
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.game_map.tile.TileCell
import com.mygdx.game.tile.ITile

/**
  * Created by evan on 4/10/16.
  */
trait Moveable {

  this: Ownable =>

  def move: Move

  abstract class Move(val moveAbility: Double = 3, gem: GameEventManager) extends GameListener {

    var moveLeft = this.moveAbility

    gem.addTurnListener(this)
    this.onTurn = (te: TurnEvent) => {
      if (te.isEndingMyTurn(owner)) {
        this.resetMove()
      }
    }

    def pathEffort(tileCell: TileCell): Option[Double] = {
      if(this.canMoveOn(tileCell)) {
        Some(
          tileCell
            .getAllTiles()
            .flatten
            .collect{ case tread: Treadable => tread.tread.pathEffort}
            .fold(1.0){ (l, r) => l * r }
        )
      } else None
    }

    def resetMove() = {
      this.moveLeft = this.moveAbility
    }

    def canMoveOn(tc: TileCell): Boolean = {
      !tc.getAllTiles().flatten.exists {
        case tread: Treadable => !this.canMoveOn(tread)
        case _ => false
      }
    }

    def canMoveOn(tile: ITile): Boolean
  }

  class Walk(moveAbility: Double = 3, gem: GameEventManager) extends Move(moveAbility, gem) {
     def canMoveOn(tile: ITile): Boolean = tile match {
      case treadable: Treadable => {
        treadable.tread.canWalkOn// && this.moveLeft >= treadable.tread.pathEffort
      }
      case _ => true
    }
  }

  class Sail(moveAbility: Double = 3, gem: GameEventManager) extends Move(moveAbility, gem) {
    def canMoveOn(tile: ITile): Boolean = tile match {
      case treadable: Treadable => {
        treadable.tread.canSailOn// && this.moveLeft >= treadable.tread.pathEffort
      }
      case _ => true
    }
  }
}






