package com.mygdx.game.tile.tile_traits

import com.mygdx.game.mechanics.{NullContainer, IResContainer, Resource}
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.Tile.Name

/**
  * Created by evan on 4/10/16.
  */
trait Constructable {

  this: Ownable =>

  def construct: Construct

  class Construct(val canBuild: Set[Name] = Set(),
                  val resToBuild: Resource = Resource()) {}
}