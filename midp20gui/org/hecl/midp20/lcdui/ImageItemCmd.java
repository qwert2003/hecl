/*
 * Copyright 2005-2007
 * Wolfgang S. Kechel, data2c GmbH (www.data2c.com)
 * 
 * Author: Wolfgang S. Kechel - wolfgang.kechel@data2c.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hecl.midp20.lcdui;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;

import org.hecl.HeclException;
import org.hecl.Interp;
import org.hecl.Properties;
import org.hecl.ObjectThing;
import org.hecl.StringThing;
import org.hecl.Thing;

import org.hecl.misc.HeclUtils;

public class ImageItemCmd extends ItemCmd {
    public static void load(Interp ip) {
	ip.addCommand(CMDNAME,cmd);
	ip.addClassCmd(ImageItem.class,cmd);
    }
    public static void unload(Interp ip) {
	ip.removeCommand(CMDNAME);
	ip.removeClassCmd(ImageItem.class);
    }

    protected ImageItemCmd() {}


    public Thing cmdCode(Interp interp,Thing[] argv) throws HeclException {
	Properties p = WidgetInfo.defaultProps(ImageItem.class);
	p.setProps(argv,1);
	ImageItem im = new ImageItem(p.getProp(WidgetInfo.NLABEL).toString(),
				     null,
				     Item.LAYOUT_DEFAULT,
				     p.getProp(WidgetInfo.NTEXT).toString(),
				     WidgetInfo.toItemAppearance(
					 p.getProp(WidgetInfo.NAPPEARANCE)));
	p.delProp(WidgetInfo.NLABEL);
	p.delProp(WidgetInfo.NTEXT);
	p.delProp(WidgetInfo.NAPPEARANCE);
	return ObjectThing.create(setInstanceProperties(interp,im,p));
    }

    
    public Thing cget(Interp ip,Object target,String optname)
	throws HeclException {
	ImageItem item = (ImageItem)target;
	
	if(optname.equals(WidgetInfo.NTEXT))
	    return StringThing.create(item.getAltText());
	if(optname.equals(WidgetInfo.NIMAGE))
	    return ObjectThing.create(item.getImage());
	if(optname.equals(WidgetInfo.NAPPEARANCE))
	    return WidgetInfo.fromItemAppearance(item.getAppearanceMode());
	return super.cget(ip,target,optname);
    }
    
    public void cset(Interp ip,Object target,String optname,Thing optval)
	throws HeclException {
	ImageItem item = (ImageItem)target;

	if(optname.equals(WidgetInfo.NTEXT)) {
	    item.setAltText(optval.toString());
	    return;
	}
	if(optname.equals(WidgetInfo.NIMAGE)) {
	    item.setImage(WidgetInfo.asImage(optval,true,true));
	    return;
	}
	super.cset(ip,target,optname,optval);
    }

    private static ImageItemCmd cmd = new ImageItemCmd();
    private static final String CMDNAME = "lcdui.imageitem";
}

// Variables:
// mode:java
// coding:utf-8
// End:
