package com.mygdx.game.tile

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.game_map.Layer
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.mechanics.player.IPlayer
import com.mygdx.game.tile.tile_traits._

/**
  * Created by evan on 4/10/16.
  */
abstract class Person extends ITile with Moveable with Ownable with Treadable with Storable with Combatable {

  val layer: Layer = Layer.person

  //def tileAnimation: ITileAnimation = null

}
