package com.mygdx.game.game_map.event

import com.mygdx.game.game_action.event.EventName.EventName
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.{Location, IGameMap}
import com.mygdx.game.mechanics.player.IPlayer

/**
  * Created by evan on 3/13/16.
  */
class GameEvent(val name: EventName) {}

class LocationEvent(val name: String, val loc: Location){
  val locX = loc.x
  val locY = loc.y
}


// called every time screen updated (used for animation mostly)
class UpdateEvent(){}

class TurnEvent(val isStartTurn: Boolean, val player: IPlayer) {

  def isStartingMyTurn(owner: IPlayer) = {
    player == owner && isStartTurn
  }

  def isEndingMyTurn(owner: IPlayer) = {
    player == owner && !isStartTurn
  }

}
