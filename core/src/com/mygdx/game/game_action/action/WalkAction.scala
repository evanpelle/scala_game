package com.mygdx.game.game_action.action

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.IGameMap
import com.mygdx.game.game_map.event.UpdateEvent
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.mechanics.map_traversal.{WalkTile, WalkPath}
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits.Moveable

/**
  * Created by evan on 3/6/16.
  */
class WalkAction(toWalk: ITile with Moveable , walkPath: WalkPath, gameMap: IGameMap, gem: GameEventManager)
  extends GameAction {

  val origLoc = toWalk.loc

  var counter = 0
  var path = walkPath.path
  val walkTime = 10

//  this.onUpdate = (event: UpdateEvent) => {
//    if(path.isEmpty){ // end action
//      gem.removeUpdateListener(this)
//    }
//    counter += 1
//    if(counter > walkTime) {
//      counter = 0
//      this.moveOneTile(path.head)
//      path = path.tail
//      if(path.nonEmpty) this.notifyTile()
//    }
//  }

  def execute: Boolean = {
    //path is just the location tile is already at
    if(walkPath.path.length == 1) return true
    toWalk.move.moveLeft  -= walkPath.totalPathEffort
    gameMap.setTile(toWalk, walkPath.getEndTile.loc)
    this.notifyTile()
    true
  }

//  def moveOneTile(toMove: WalkTile) = {
//    gameMap.setTile(toWalk, toMove.loc)
//  }

  def notifyTile() = {
    toWalk.anim.justMoved(walkPath)
  }

  override def isValid: Boolean = true

  override def getDisplayName: String = "walk"
}
