package com.mygdx.game.game_action.action

import com.mygdx.game.game_action.event.{GameEventManager, EventName}
import com.mygdx.game.game_map.event.{GameEvent, LocationEvent}
import com.mygdx.game.game_map.tile.{TileCell, TileFactory}
import com.mygdx.game.game_map.{Location, IGameMap, Layer}
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits.Combatable

/**
  * Created by evan on 3/8/16.
  */
class DisplayAttackAction(tile: ITile with Combatable, gameMap: IGameMap, gem: GameEventManager, tileFactory: TileFactory) extends GameAction {

  def execute: Boolean = {
    gem.fireEvent(new GameEvent(EventName.displayAttack))
    val canAttack = gameMap.getAdjacentTiles(tile.loc)
    canAttack.foreach {tc => createCanAttackOverlayTile(gameMap, tc)}
    true
  }

  private def createCanAttackOverlayTile(gameMap: IGameMap, tileCell: TileCell) = {
    val canAttackTile = tileFactory.buildDisplayAttack
    gem.addClickListener(canAttackTile)
    gem.addGameEventListener(canAttackTile)
    canAttackTile.onClick = (le: LocationEvent) => {
      if (canAttackTile.loc.equals(le.loc)) {
        gameMap.getTile(Location(le.locX, le.locY), Layer.person) match {
          case Some(toAttack: ITile with Combatable) => {
            new AttackAction(tile, toAttack, gameMap, gem).execute
          }
          case _ => Unit
        }
      }
      gameMap.popTile(canAttackTile)
      gem.removeAllListener(canAttackTile)
    }
    canAttackTile.onEvent = (event: GameEvent) => {
      if(event.name == EventName.displayWalk) {
        gameMap.popTile(canAttackTile)
        gem.removeAllListener(canAttackTile)
      }
    }
    gameMap.setTile(canAttackTile, tileCell.loc)
  }

  def onClickTile() = false

  override def getDisplayName: String = "attack"

  override def isValid: Boolean = tile match {
    case toAttack: ITile with Combatable => {
      gameMap
        .getAdjacentTiles(tile.loc)
        .exists(toAttack.combat.canAttackTile) &&
      toAttack.combat.canAttack
    }
    case _ => false
  }
}
