package com.mygdx.game.mechanics.map_traversal

import com.mygdx.game.game_map.Location

/**
  * Created by evan on 4/4/16.
  */

class WalkTile(val loc: Location, val pathEffort: Double) {
  override def toString = s"($loc + effort: $pathEffort)"
}
