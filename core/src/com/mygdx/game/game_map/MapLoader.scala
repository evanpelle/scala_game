package com.mygdx.game.game_map

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.{MapLayer, MapObject}
import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer, TiledMapTile, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer
import com.mygdx.game.animation.factory.AnimationFactory
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.tile.{ITile, TileLoader}

import scala.collection.JavaConversions._

import org.w3c.dom._;
import javax.xml.parsers._;
import java.io._;
/**
  * Created by evan on 2/18/16.
  *
  * loads contents of filename into gameMap
  */
class MapLoader(val filename: String, gameMap: IGameMap, tileLoader: TileLoader, gem: GameEventManager) {

  def generateMap: IGameMap = {
    val tiledMap = this.buildMapTiledMap
    this.processMap(gameMap, tiledMap)
    gameMap
  }

  private def buildMapTiledMap = {
    val map_loader = new TmxMapLoader()
    map_loader.load(filename)
  }

//  private def buildGameMap(tiledMap: TiledMap) = {
//    val mapWidth = tiledMap.getProperties().get("width", Int.getClass).asInstanceOf[Int]
//    val mapHeight = tiledMap.getProperties().get("height", Int.getClass).asInstanceOf[Int]
//    mapBuilder.buildEmptyMap(mapWidth, mapHeight, gem)
//  }

  private def processMap(gameMap: IGameMap, tiledMap: TiledMap) = {
    for (y <- 0 until gameMap.getMapHeight) {
      for (x <- 0 until gameMap.getMapWidth) {
        for (layerIndex <- 0 until tiledMap.getLayers.getCount) {
          val layer = tiledMap.getLayers.get(layerIndex).asInstanceOf[TiledMapTileLayer]
          if(layer.getCell(x, y) != null) {
            val oldTile: TiledMapTile = layer.getCell(x, y).getTile
            if (oldTile != null) {
              val newTile = tileLoader.build(oldTile.getProperties)
              gameMap.setTile(newTile, Location(x, y))
            }
          }
        }
      }
    }

    //tiledMap.getLayers.get(3).asInstanceOf[TiledMapTileLayer].getObjects.add(tmo)
  }

  //def generateTile(layerIndex: Int, locX: Int locY: Int, tiledMap: TiledMap)

}
