package com.mygdx.game.game_map

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.{TiledMapTile, TiledMapTileLayer, TmxMapLoader, TiledMap}
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.{InputEvent, InputListener, Actor}
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.Direction._
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.event.{UpdateEvent, LocationEvent}
import com.mygdx.game.game_map.tile._
import com.mygdx.game.tile.ITile


/**
  * Created by evan on 2/14/16.
  *
  * a wrapper around libgdx TiledMap class
  *
  */
class GameMap(val mapWidth: Int,
              val mapHeight: Int,
              val tiledMap: TiledMap,
              val mapRenderer: HexagonalTiledMapRenderer,
              val gem: GameEventManager) extends Actor with IGameMap {


  val tileWidth = GameMap.tileWidth
  val tileHeight = GameMap.tileHeight

  println(mapWidth)


  this.setBounds(0, 0, mapWidth * tileWidth, mapHeight * tileHeight)

  override def act(dt: Float): Unit = {
    mapRenderer.setView(getStage.getCamera.asInstanceOf[OrthographicCamera])
  }

  def getMapWidth = mapWidth

  def getMapHeight = mapHeight

  def getAdjacentTiles(loc: Location): Set[TileCell] = {
    loc.getAllNeighbors
      .map(this.getTileCell)
      .filter(_ != None)
      .map(_.get)
      .toSet
  }

  def setTile(tile: ITile, newLoc: Location): Unit = {
    this.popTile(tile)
    tile.loc = Location(newLoc.x, newLoc.y)
    if (tile.loc.isOnBoard) {
      getTile( Location(newLoc.x, newLoc.y), tile.layer) match {
        case Some(tile) => this.popTile(tile)
        case None => Unit
      }
      this.getLayer(tile.layer).getCell(newLoc.x, newLoc.y).setTile(tile)
    }
  }

  def getTile(loc: Location, layer: Layer): Option[ITile] = {
    val cell = this.getLayer(layer).getCell(loc.x, loc.y)
    if(cell == null) return None
    val tile = cell.getTile
    if (tile == null) None
    if (tile.isInstanceOf[ITile]) Some(tile.asInstanceOf[ITile])
    else None
  }

  def getTileCell(loc: Location): Option[TileCell] = {
    if(loc.x >= this.mapWidth || loc.y >= this.mapHeight
      || loc.x < 0 || loc.y < 0) return None

    Some(
      new TileCell(
        this.getTile(Location(loc.x, loc.y), Layer.landscape),
        this.getTile(Location(loc.x, loc.y), Layer.terrain),
        this.getTile(Location(loc.x, loc.y), Layer.structure),
        this.getTile(Location(loc.x, loc.y), Layer.person)
      )
    )
  }


  def popTile(loc: Location, layer: Layer): Option[ITile] = {
    this.getTile(loc, layer) match {
      case Some(tile) => {
        this.getLayer(tile.layer).getCell(loc.x, loc.y).setTile(null)
        tile.asInstanceOf[ITile].loc = Location(-1, -1)
        Some(tile)
      }
      case None => None
    }
  }

  def getLayer(layer: Layer) = {
    tiledMap.getLayers.get(layer.id).asInstanceOf[TiledMapTileLayer]
  }

  this.addListener(new InputListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) = {
      val (locX: Int, locY: Int) = GameMap.pos_to_loc(x.asInstanceOf[Int], y.asInstanceOf[Int])
      gem.fireEvent(new LocationEvent("click", Location(locX, locY)))
      true
    }

    override def mouseMoved(event: InputEvent, x: Float, y: Float) = {
      val (locX: Int, locY: Int) = GameMap.pos_to_loc(x.asInstanceOf[Int], y.asInstanceOf[Int])
      gem.fireEvent(new LocationEvent("hover", Location(locX, locY)))
      true
    }
  })

  @Override
  override def draw(batch: Batch, parentAlpha: Float) {
    mapRenderer.render()
  }

}

object GameMap {

  val lastGameLayer = 3

  val tileWidth = 64
  val tileHeight = 64

 // val magicRatio = 100/75.1
  //bigger denom, stretches it out more
  val magicRatio = 4/3.0



  def pos_to_loc(posX: Int, posY: Int) = {
    val locX = (posX * GameMap.magicRatio - 5).toInt / GameMap.tileWidth
    var locY = 0
    if (locX % 2 == 0) {
      locY = (posY - GameMap.tileHeight / 2.0).toInt / GameMap.tileHeight
    } else {
      locY = posY / GameMap.tileHeight
    }
    (locX, locY)
  }
}





