package com.mygdx.game.mechanics

/**
  * Created by evan on 3/27/16.
  */
class NullContainer extends IResContainer {

  def maxRes: Resource = Resource()

  def canHoldRes: Boolean = false

  def currRes: Resource = Resource()

  /**
    *
    * @param toAdd amount of resourse to add
    * @return true if successful,
    *         false if toAdd + currRes > maxRes
    */
  def addRes(toAdd: Resource): Boolean = false

  /**
    *
    * @param toRemove amount of res to remove
    * @return true if removal was successful,
    *         if toRemove larger than current resource, false
    */
  def removeRes(toRemove: Resource): Boolean = false


  def setRes(toSet: Resource) = false
}
