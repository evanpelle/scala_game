package com.mygdx.game.game_map

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TiledMap}
import com.mygdx.game.animation.GameAnimation.GameAnimation
import com.mygdx.game.animation.MyHexMapRenderer
import com.mygdx.game.animation.factory.AnimationFactory
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.tile.TileFactory
import com.mygdx.game.tile.ITile

/**
  * Created by evan on 4/5/16.
  *
  * creates an empty GameMap
  */
class MapBuilder() {


  private def genMapLayers(tiledMap: TiledMap, mapWidth: Int, mapHeight: Int) = {
    for(i <- 0 to Layer.overlay.id) {
      val newLayer = new TiledMapTileLayer(mapWidth,mapHeight, GameMap.tileWidth, GameMap.tileHeight)
      tiledMap.getLayers.add(newLayer)
    }
  }

  private def genMapCells(tiledMap: TiledMap, mapWidth: Int, mapHeight: Int) = {
    for (y <- 0 until mapHeight) {
      for (x <- 0 until mapWidth) {
        for (layerIndex <- 0 until tiledMap.getLayers.getCount) {
          val layer = tiledMap.getLayers.get(layerIndex).asInstanceOf[TiledMapTileLayer]
          if (layer.getCell(x, y) == null) {
            layer.setCell(x, y, new Cell())
          }
        }
      }
    }
  }

  def addObjectLayer(tiledMap: TiledMap) = {
//    val tr = new ITile {
//      override def layer: Layer = Layer.anyLayer
//
//      override def anim: GameAnimation = new AnimationFactory().buildStatic("fort")
//    }
//    val tmo = new TextureMapObject(tr)
//    tmo.setX(0)
//    tmo.setY(0)
    val mapLayer = new MapLayer()
   // mapLayer.getObjects().add(tr)
    tiledMap.getLayers.add(mapLayer)
  }

  def buildEmptyMap(mapWidth: Int, mapHeight: Int, gem: GameEventManager) = {
    val tiledMap = new TiledMap()
    this.genMapLayers(tiledMap, mapWidth, mapHeight)
    this.genMapCells(tiledMap, mapWidth, mapHeight)
    //this.addObjectLayer(tiledMap)
    val mapRenderer = new MyHexMapRenderer(tiledMap)
    new GameMap(mapWidth, mapHeight, tiledMap, mapRenderer, gem)
  }

  def buildInvisibleMap(mapWidth: Int, mapHeight: Int, gem: GameEventManager) = {
    val tiledMap = new TiledMap()
    this.genMapLayers(tiledMap, mapWidth, mapHeight)
    this.genMapCells(tiledMap, mapWidth, mapHeight)
    //val mapRenderer = new HexagonalTiledMapRenderer(tiledMap)
    new GameMap(mapWidth, mapHeight, tiledMap, null, gem)
  }

  def addGrass(gameMap: IGameMap, tileFactory: TileFactory) = {
    for(y <- 0 until gameMap.getMapHeight) {
      for(x <- 0 until gameMap.getMapWidth) {
        gameMap.setTile(tileFactory.buildGrass, Location(x, y))
      }
    }
    gameMap
  }
//
//  def buildInvisibleWithGrass(mapWidth: Int, mapHeight: Int, tileFactory: TileFactory, gem: GameEventManager) = {
//    val gameMap = this.buildInvisibleMap(mapWidth, mapHeight, gem)
//    for(y <- 0 until gameMap.getMapHeight) {
//      for(x <- 0 until gameMap.getMapWidth) {
//        gameMap.setTile(tileFactory.buildGrass, Location(x, y))
//      }
//    }
//    gameMap
//  }
}
