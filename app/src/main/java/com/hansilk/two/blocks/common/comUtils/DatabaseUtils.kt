package com.hansilk.two.blocks.common.comUtils

import com.hansilk.two.support.utils.listUtils.ListUtils
import org.json.JSONObject
import java.util.ArrayList

class DatabaseUtils {
    companion object {

        val remoteReoKeys = "aa,ab,ac,ba,bb,bc,bd,bf,bi,bj,bk,bl,bm,bn,bo,bp,ca,cb,cc,cd,ce,cf,cg,ch,ci,cj,ck,cl,cm,cn,co,cp,cq,cr,cs,ct,cu,cv,de,df,cx,fa,fb,fc,fd,fe,ff,fg,fh,ia,ib,iy,rb,tw,ty".split(",")

        //function to print keys list from remote JSON
        //to print keyList for a table, change remoteString and call function
        fun printKeysFromJSON() {
            val remoteString = "{\"aa\":\"14141\",\"ab\":\"1607964139930\",\"ac\":\"1\",\"ba\":null,\"bb\":\"461\",\"bc\":\"1\",\"bd\":null,\"bf\":null,\"bi\":null,\"bj\":null,\"bk\":null,\"bl\":null,\"bm\":null,\"bn\":null,\"bo\":\"89\",\"bp\":null,\"ca\":\"272\",\"cb\":\"280\",\"cc\":\"284\",\"cd\":\"288\",\"ce\":\"304\",\"cf\":\"358\",\"cg\":null,\"ch\":null,\"ci\":\"326\",\"cj\":null,\"ck\":\"336\",\"cl\":\"339\",\"cm\":\"345\",\"cn\":null,\"co\":null,\"cp\":\"630\",\"cq\":\"500\",\"cr\":\"756\",\"cs\":\"128\",\"ct\":null,\"cu\":null,\"cv\":null,\"de\":null,\"df\":null,\"cx\":null,\"fa\":\"1614324914652\",\"fb\":null,\"fc\":null,\"fd\":null,\"fe\":null,\"ff\":null,\"fg\":null,\"fh\":null,\"ia\":\"1611753201960124\",\"ib\":\"1\",\"iy\":\"14\",\"rb\":\"593\",\"tw\":null,\"ty\":\"2021-08-22 10:53:47\"}\n"
            val remoteReoModel = JSONObject(remoteString)
            val list = ArrayList<String>()
            val iterator = remoteReoModel.keys()
            while (iterator.hasNext()){
                val ky = iterator.next()
                list.add(ky)
            }
            println("listaa : ${ListUtils.ArrayListToStringWithNoSpaces(list)}")
        }

        fun filterOutExtrasReo (localModel:JSONObject): JSONObject{
            return filterOutExtrasFromLocalModel(remoteReoKeys, localModel)
        }

        fun filterOutExtrasFromLocalModel (remoteKeys:List<String>, localModel:JSONObject): JSONObject{
            val finalModel = JSONObject()

            //iterate through local model, check and add key-value to final model if the key is present in remote model
            val iterator = localModel.keys()
            while (iterator.hasNext()){
                val ky = iterator.next()
                if (remoteKeys.contains(ky)) finalModel.put(ky, localModel.get(ky))
            }

            return finalModel
        }

    }
}