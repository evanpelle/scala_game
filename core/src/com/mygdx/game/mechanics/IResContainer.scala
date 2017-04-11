package com.mygdx.game.mechanics

/**
  * Created by evan on 3/27/16.
  */
trait IResContainer {

  def maxRes: Resource

  def canHoldRes: Boolean

  def currRes: Resource


  /**
    *
    * @param toRemove amount of res to remove
    * @return true if removal was successful,
    *         if toRemove larger than current resource, false
    */
  def removeRes(toRemove: Resource): Boolean


  /**
    *
    * @param toAdd amount of resourse to add
    * @return true if successful,
    *         false if toAdd + currRes > maxRes
    */
  def addRes(toAdd: Resource): Boolean


  def setRes(toSet: Resource): Boolean



}