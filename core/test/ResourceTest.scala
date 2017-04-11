package com.mygdx.game;

import com.badlogic.gdx.maps.MapProperties
import com.mygdx.game.game_action.action.ActionNode.build_action.BuildAction
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.tile.TileLoader
import junit.framework.Assert
import org.junit.Assert.assertTrue;
import org.junit.Assert.assertEquals

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map._
import com.mygdx.game.mechanics.{IResContainer, ResContainer, Resource}
import com.mygdx.game.screens.PlayScreen
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.Test;



class ResourceTest {



  @Test
  def resourceAdd: Unit = {
    val first = Resource(1,2,3,4)
    val second = Resource(4,3,2,1)
    val sum = first + second
    assertEquals(sum, Resource(5,5,5,5))
  }

  @Test
  def resourceSubtract: Unit = {
    val first = Resource(10, 9, 8, 7)
    val second = Resource(4, 3, 2, 1)
    val sub = first - second
    assertEquals(sub, Resource(6,6,6,6))

    val start = Resource.all(100)
    val end = start - Resource(gold = 10)
    assertEquals(end, Resource(90, 100, 100, 100))
  }

  @Test
  def resourceCompare: Unit = {
    val greater = Resource(5,5,5,5)
    val lesser = Resource(4,4,4,4)
    assertTrue(greater > lesser)
    assertTrue(greater >= lesser)
    assertTrue(!(greater < lesser))
    assertTrue(!(greater <= lesser))

    val wrong = Resource(10,1,1,1)
    assertTrue(!(greater > wrong))
    assertTrue(!(lesser < wrong))
  }

  @Test
  def resContainer: Unit = {
    val maxRes = Resource(5,5,5,5)
    val currRes = Resource(1,1,1,1)
    val rc: IResContainer = new ResContainer(currRes, maxRes)
    assertTrue(rc.addRes(Resource(2,2,2,2)))
    assertEquals(Resource(3,3,3,3), rc.currRes)

    assertTrue(!rc.removeRes(Resource(4,4,4,4)))
    assertTrue(rc.removeRes(Resource(1,1,1,1)))

    assertEquals(Resource(2,2,2,2), rc.currRes)
  }

  @Test
  def resContainerSetRes: Unit = {
    val resCont: IResContainer = new ResContainer(Resource(0,0,0,0), Resource(50,50,50,50))
    assertTrue(!resCont.setRes(Resource(0,100,100,0)))
    assertTrue(resCont.setRes(Resource(10,10,10,10)))
    assertTrue(Resource(10,10,10,10).equals(resCont.currRes))
  }


}