package com.mygdx.game.tile

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.{MapObject, MapProperties}
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.maps.tiled.TiledMapTile.BlendMode
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.Location
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.mechanics._
import com.mygdx.game.mechanics.player.IPlayer
import com.mygdx.game.tile.Tile.Name
import com.mygdx.game.tile.tile_traits.Animatable

/**
  * Created by evan on 3/29/16.
  */
abstract class ITile extends MapObject with TiledMapTile with Animatable {

  //this: Animatable =>

  def layer: Layer

  var loc = Location(-1, -1)



  def name: Name

  def getTextureRegion = this.anim.getTextureRegion(this.loc)


  def getOffsetX: Float = this.anim.getOffsetX

  def getOffsetY: Float = this.anim.getOffsetY

  def getTexturePosX = this.anim.posX

  def getTexturePosY = this.anim.posY

  override def toString = s"($name $loc)"


  /**
    * all methods below are useless,
    * used only for libgdx TiledMapTile api
    */
  def getId: Int = 0
  def setId(id: Int): Unit = {}
  //def getProperties: MapProperties = null
  def getBlendMode: BlendMode = null
  def setBlendMode(blendMode: BlendMode): Unit = {}
  def setTextureRegion(textureRegion: TextureRegion): Unit = {}
  def setOffsetY(offsetY: Float): Unit = {}
  def setOffsetX(offsetX: Float): Unit = {}


}
