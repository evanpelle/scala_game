package com.mygdx.game.animation.texture_store

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
  * Created by evan on 4/14/16.
  */
object TextureStorage {




  lazy val soldierAttackAnimation = new AttackTexture("soldierAttackSheet")
  lazy val soldierWalkAnimation = new WalkTexture("soldierWalkSheet")
  lazy val workerWalkAnimation = new WalkTexture("workerWalkSheet")


  private lazy val stringToTexture: Map[String, Texture] = Map(
    ("grass", new Texture("assets/tile_map/landscape/Grass.png")),
    ("water", new Texture("assets/tile_map/landscape/Water.png")),
    ("mountain", new Texture("assets/tile_map/terrain/mountain_old.png")),
    ("forest", new Texture("assets/tile_map/terrain/forest.png")),
    ("fort", new Texture("assets/tile_map/structure/wood_base.png")),
    ("mill", new Texture("assets/tile_map/structure/lumber_mill.png")),
    ("city", new Texture("assets/tile_map/structure/city.png")),
    ("worker", new Texture("assets/tile_map/person/worker.png")),
    ("soldier", new Texture("assets/tile_map/person/soldier.png")),
    ("hover", new Texture("assets/tile_map/overlay/hover.png")),
    ("select", new Texture("assets/tile_map/overlay/select.png")),
    ("canAttack", new Texture("assets/tile_map/overlay/can_attack.png")),
    ("canBoard", new Texture("assets/tile_map/overlay/can_board.png")),
    ("ship", new Texture("assets/tile_map/person/ship.png")),
    ("markerBlue", new Texture("assets/tile_map/marker/marker_blue.png")),
    ("markerRed", new Texture("assets/tile_map/marker/marker_red.png")),
    ("invisible", new Texture("assets/tile_map/invisible.png")),
    ("dot", new Texture("assets/tile_map/white_dot.png")),
    ("soldierWalkSheet", new Texture("assets/tile_map/person/soldier_sheet_walk.png")),
    ("soldierAttackSheet", new Texture("assets/tile_map/person/soldier_sheet_attack.png")),
    ("workerWalkSheet", new Texture("assets/tile_map/person/worker_sheet_walk.png"))
  )



  def getTexture(name: String) = {
    if(stringToTexture.contains(name)) {
      stringToTexture(name)
    } else {
      throw new Exception(s"texture with name: $name could not be found")
    }
  }

  def buildSheet(toBuild: String, numColumns: Int) = {
    val walkSheet = TextureStorage.getTexture(toBuild)
    val ts = TextureRegion.split(walkSheet, walkSheet.getWidth/numColumns, walkSheet.getHeight/4)
    ts.foreach{arr =>
      arr.foreach {tr =>
        tr.getTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
      }
    }
    ts
  }

}
