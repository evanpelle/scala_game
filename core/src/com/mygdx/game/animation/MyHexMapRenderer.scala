package com.mygdx.game.animation

import com.mygdx.game.game_map.{GameMap, Location, Layer}
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits.Animatable

import scala.collection.JavaConversions._


import com.badlogic.gdx.maps.{MapLayer, MapObject}
import com.badlogic.gdx.maps.tiled.{TiledMapImageLayer, TiledMapTileLayer, TiledMap}
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer

/**
  * Created by evan on 4/30/16.
  */
class MyHexMapRenderer(tiledMap: TiledMap) extends HexagonalTiledMapRenderer(tiledMap) {


  //TODO: check if tiles are in view before rendering
  override def renderObject(mapObject: MapObject) = mapObject match {
    case anim: ITile => {
      batch.draw(
        anim.getTextureRegion,
        (anim.getTexturePosX +  anim.getOffsetX - GameMap.tileWidth/2).toFloat,
        (anim.getTexturePosY + anim.getOffsetY - GameMap.tileHeight/2).toFloat
      )
    }
    case _ => Unit
  }

  def renderObjects(objects: Set[ITile]) = {
    objects.foreach(renderObject)
  }

  def tiledLayerToObjects(tmtl: TiledMapTileLayer) = {
    var tiles = Set[ITile]()
    for(x <- 0 until tmtl.getHeight) {
      for(y <- 0 until tmtl.getWidth) {
        tiles += tmtl.getCell(x, y).getTile.asInstanceOf[ITile]
      }
    }
    tiles
  }

  override def render() {
    beginRender()
    map.getLayers.filter(_.isVisible).foreach {
      case tmtl: TiledMapTileLayer => {
        if(tiledMap.getLayers.get(Layer.person.id) == tmtl) { //render people layer differently
        val tiles = tiledLayerToObjects(tmtl)
          renderObjects(tiles)
        } else renderTileLayer(tmtl)
      }
      case tmil: TiledMapImageLayer => renderImageLayer(tmil)
      case ml: MapLayer => renderObjects(ml)
    }
    endRender()
  }

}