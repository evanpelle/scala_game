package com.mygdx.game;

import com.mygdx.game.animation.factory.NullAnimationFactory
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.event.TurnEvent
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.tile.{Tile, ITile}
import org.junit.Assert.assertTrue;
import org.junit.Assert.assertEquals

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{ResContainer, Resource}
import com.mygdx.game.screens.PlayScreen
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.Test;



class GEMTest {

  val gem = new GameEventManager
  val tile = new ITile with GameListener{
    override val layer: Layer = Layer.person
    val name = Tile.none
    override val anim = null
  }

  @Test
  def testOnTurnEnd = {
    var wasFired = false
    tile.onTurn = (te: TurnEvent) => {wasFired = true}
    gem.addTurnListener(tile)
    gem.fireEvent(new TurnEvent(false, null))
    assertTrue(wasFired)

    wasFired = false
    gem.removeTurnListener(tile)
    gem.fireEvent(new TurnEvent(false, null))
    assertTrue(!wasFired)
  }

}