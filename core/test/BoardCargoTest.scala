package com.mygdx.game;

import com.badlogic.gdx.maps.MapProperties
import com.mygdx.game.animation.factory.{NullAnimationFactory, AnimationFactory}
import com.mygdx.game.game_action.action.ActionNode.build_action.BuildAction
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.mechanics.player.{Human, IPlayer}
import com.mygdx.game.tile.{Tile, ITile, TileLoader}
import com.mygdx.game.tile.tile_traits.{Ownable, Boardable}
import junit.framework.Assert
import org.junit.Assert.assertTrue;
import org.junit.Assert.assertEquals

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{ResContainer, Resource}
import com.mygdx.game.screens.PlayScreen
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.Test;



class BoardCargoTest {

  val player: IPlayer = new Human("player", "blue")

  val (gameMap, tileFactory, gem) = new GameBuilder().buildInvisibleGame


  @Test
  def testCargo = {
    val toBoard = new ITile with Boardable with Ownable {
      def owner: IPlayer = player
      val layer: Layer = Layer.person
      val name = Tile.none
      val board: Board = new Board()
      val anim = null
      val marker: Marker = null
    }

    val person = tileFactory.buildSoldier(player)
    assertTrue(toBoard.board.canStore)
    toBoard.board.addCargo(person)
    assertEquals(1, toBoard.board.cargoSize)

    toBoard.board.removeCargo(person)
    assertEquals(0, toBoard.board.cargoSize)

    assertTrue(!toBoard.board.hasCargo)


  }

}