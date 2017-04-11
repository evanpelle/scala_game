package com.mygdx.game.game_action.action

import com.mygdx.game.game_action.event.{EventName, GameEventManager}
import com.mygdx.game.game_map.{IGameMap, GameMap}
import com.mygdx.game.game_map.event.GameEvent
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits.{Ownable, Combatable}

/**
  * Created by evan on 5/2/16.
  */
class DeathAction(toDie: ITile with Combatable, gameMap: IGameMap, gem: GameEventManager) extends GameAction {

  override def execute: Boolean = {
    println(s"gonna kill $toDie")
    gameMap.popTile(toDie)
    toDie match {
      case gl: GameListener => gem.removeAllListener(gl)
      case _ => Unit
    }
    toDie match {
      case own: Ownable => own.owner.removeProperty(own)
      case _ => Unit
    }
    gem.fireEvent(new GameEvent(EventName.death))
    true
  }

  override def getDisplayName: String = "die"

  override def isValid: Boolean = true
}
