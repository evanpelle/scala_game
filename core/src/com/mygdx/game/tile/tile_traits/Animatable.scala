package com.mygdx.game.tile.tile_traits

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.animation.GameAnimation.{GameAnimation}
import com.mygdx.game.game_map.Location

/**
  * Created by evan on 4/11/16.
  */
trait Animatable {

  def anim: GameAnimation

}
