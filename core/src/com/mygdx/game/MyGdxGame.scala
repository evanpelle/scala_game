package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.screens.PlayScreen

class MyGdxGame() extends Game {
  var batch: SpriteBatch = null


  def create {
    batch = new SpriteBatch()
    setScreen(new PlayScreen(this))
  }

  override def render {
    super.render()
  }
}
