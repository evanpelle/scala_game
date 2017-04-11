package com.mygdx.game.tile.tile_traits

import com.mygdx.game.animation.GameAnimation.GameAnimation
import com.mygdx.game.animation.factory.IAnimationFactory
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.event.UpdateEvent
import com.mygdx.game.game_map.{Layer, IGameMap}
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.mechanics.player.IPlayer
import com.mygdx.game.tile.{Tile, ITile}

/**
  * Created by evan on 4/10/16.
  */
trait Ownable {

  this: ITile =>

  def owner: IPlayer

  def marker: Marker

  owner.addProperty(this)

  class Marker(animFactory: IAnimationFactory,
               gameMap: IGameMap,
               gem: GameEventManager) extends ITile with GameListener {


    val layer = Layer.marker
    val name = Tile.overlay

    val anim: GameAnimation = animFactory.buildMarker(Ownable.this.owner.color)


    val origOffsetX = anim.getOffsetX
    val origOffsetY = anim.getOffsetY

    val origFollowOffsetX = getOffsetX
    val origFollowOffsetY = getOffsetY
    gem.addUpdateListener(this)




    def stopFollow = {
      gem.removeAllListener(this)
    }

    this.onUpdate = (ue: UpdateEvent) => {
      if(!Ownable.this.loc.equals(this.loc)) {
        gameMap.setTile(this, Ownable.this.loc)
      }
      this.anim.offsetX = getOffsetX + this.origOffsetX - origFollowOffsetX
      this.anim.offsetY = getOffsetY + this.origOffsetY - origFollowOffsetY
    }
  }

}
