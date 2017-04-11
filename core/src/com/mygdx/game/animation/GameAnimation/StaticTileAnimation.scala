package com.mygdx.game.animation.GameAnimation

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.game_map.Location

/**
  * Created by evan on 3/11/16.
  */
class StaticTileAnimation(val textureRegion: TextureRegion) extends GameAnimation {

  var name = "none"

  def getTextureRegion(loc: Location): TextureRegion = {
    textureRegion
  }

}

