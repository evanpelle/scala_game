package com.mygdx.game.animation.GameAnimation

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.game_map.Location

/**
  * Created by evan on 3/31/16.
  */
class NullAnimation extends GameAnimation {

  override def getTextureRegion(loc: Location): TextureRegion = null
}
