package com.mygdx.game

import com.mygdx.game.animation.factory.NullAnimationFactory
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.{MapBuilder, IGameMap}
import com.mygdx.game.game_map.tile.TileFactory

/**
  * Created by evan on 5/21/16.
  */
class GameBuilder {

  def buildInvisibleGame: (IGameMap, TileFactory, GameEventManager) = {
    val gem = new GameEventManager
    val mapBuilder = new MapBuilder
    val gameMap = mapBuilder.buildInvisibleMap(100, 100, gem)
    val tileFactory = new TileFactory(new NullAnimationFactory, gameMap, gem)
    mapBuilder.addGrass(gameMap, tileFactory)
    (gameMap, tileFactory, gem)
  }

}
