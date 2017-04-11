package com.mygdx.game.game_action.action

import com.mygdx.game.animation.GameAnimation.{GameAnimation, StaticTileAnimation}
import com.mygdx.game.animation.factory.IAnimationFactory
import com.mygdx.game.game_action.event.{GameEventManager, EventName}
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.event.{GameEvent, LocationEvent}
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.game_map.tile.{TileCell}
import com.mygdx.game.game_map.{IGameMap, Layer, Location}
import com.mygdx.game.mechanics.map_traversal.{MapTraversal, WalkPath}
import com.mygdx.game.tile.{Tile, ITile}
import com.mygdx.game.tile.tile_traits.Moveable

/**
  * Created by evan on 3/9/16.
  */
class DisplayWalkAction(tile: ITile with Moveable,
                        gameMap: IGameMap,
                        animFactory: IAnimationFactory,
                        gem: GameEventManager) extends GameAction {


  def execute: Boolean = {
    gem.fireEvent(new GameEvent(EventName.displayWalk))
    new MapTraversal(gameMap).getTilesCanMove(tile)
        .foreach{ walkPath =>
          setSelectTile(gameMap, walkPath)
        }
    true

  }

  private def setSelectTile(gameMap: IGameMap, walkPath: WalkPath) = {
    val onClickAction = new WalkAction(tile, walkPath, gameMap, gem)
    val ot = new ITile with GameListener {
      val name = Tile.overlay
      val layer: Layer = Layer.overlay
      val anim: GameAnimation = animFactory.buildStatic("select")
      onClick = (le: LocationEvent) => {
        if(this.loc.equals(le.loc)){
          onClickAction.execute
        }
        gem.removeAllListener(this)
        gameMap.popTile(this)
      }
      onEvent = (event: GameEvent) => {
        if(event.name == EventName.displayAttack) {
          gem.removeAllListener(this)
          gameMap.popTile(this)
        }
      }
    }
    gem.addClickListener(ot)
    gem.addGameEventListener(ot)
    gameMap.setTile(ot, walkPath.getEndTile.loc)
  }

  override def getDisplayName: String = "move"

  override def isValid: Boolean = {
    new MapTraversal(gameMap).getTilesCanMove(tile).size > 1
  }

}