package com.mygdx.game.game_map

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, InputListener}
import com.mygdx.game.game_map.Layer._
import com.mygdx.game.game_map.event.LocationEvent
import com.mygdx.game.game_map.tile.TileCell
import com.mygdx.game.tile.ITile

/**
  * Created by evan on 3/27/16.
  */
trait IGameMap {

  def getMapWidth: Int

  def getMapHeight: Int

  def getAdjacentTiles(loc: Location): Set[TileCell]

  def setTile(tile: ITile, newLoc: Location): Unit

  def getTile(loc: Location, layer: Layer): Option[ITile]

  def getTileCell(loc: Location): Option[TileCell]

  def popTile(loc: Location, layer: Layer): Option[ITile]

  def popTile(tile: ITile): Option[ITile] = this.popTile(tile.loc, tile.layer)

}
