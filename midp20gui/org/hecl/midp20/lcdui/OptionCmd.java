/*
 * Copyright 2006-2007 Wolfgang S. Kechel
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

import org.hecl.ClassCommand;
import org.hecl.ClassCommandInfo;
import org.hecl.HeclException;
import org.hecl.Interp;
import org.hecl.ObjectThing;
import org.hecl.Properties;
import org.hecl.Thing;

public abstract class OptionCmd implements ClassCommand, org.hecl.Command {
    protected OptionCmd() {}
    
    public Object setInstanceProperties(Interp ip,Object target,Properties p)
	throws HeclException {
	if(p != null) {
	    Thing optargs[] = p.getProps();
	    configure(ip,target,optargs,0,optargs.length);
	}
	return target;
    }

    public Thing method(Interp ip, ClassCommandInfo context, Thing[] argv)
	throws HeclException {
	if(argv.length > 1) {
	    String subcmd = argv[1].toString().toLowerCase();
	    Object target = ObjectThing.get(argv[0]);

	    //System.out.println("OptionCmd::method("+target+", subcmd="+subcmd);

	    if(subcmd.equals(WidgetInfo.NCGET)) {
		if(argv.length != 3) {
		    throw HeclException.createWrongNumArgsException(argv, 2, "option");
		}
		return cget(ip,target,argv[2].toString());
	    } else if(subcmd.equals(WidgetInfo.NCONF)
	       || subcmd.equals(WidgetInfo.NCONFIGURE)) {
		configure(ip,target,argv,2,argv.length-2);
		return null;
	    }
	    return handlecmd(ip,target,subcmd,argv,2);
	}
	throw HeclException.createWrongNumArgsException(argv, 2, "Object method [arg...]");
    }
 

    public void configure(Interp ip,Object target,Thing[] argv,int start,int n) 
	throws HeclException {
	if(n < 0 || n % 2 != 0) {
	    throw new HeclException("configure needs name-value pairs");
	}
	// deal with option/value pairs
	for(int i = start ; n > 0; n -= 2, i += 2) {
	    cset(ip,target,argv[i].toString().toLowerCase(),argv[i+1]);
	}
    }
    

    private static HeclException optex(String optname) {
	return new HeclException("Unknown option '"+optname+"'");
    }
    private static HeclException itemoptex(String optname) throws HeclException {
	return new HeclException("Unknown item option '"+optname+"'");
    }
    
    
    protected Thing cget(Interp ip,Object target,String optname)
	throws HeclException {
	throw optex(optname);
    }
    

    protected void cset(Interp ip,Object target,String optname,Thing optval)
	throws HeclException {
	throw optex(optname);
    }
    

    protected Thing itemcget(Interp ip,Object target, int itemno,String optname)
	throws HeclException {
	throw itemoptex("item cget "+optname);
    }
    

    protected void itemcset(Interp ip,Object target,
			    int itemno,String optname,Thing optval)
	throws HeclException {
	throw itemoptex("item cset "+optname);
    }


    protected Thing handlecmd(Interp ip,Object target,
			      String subcmd, Thing[] argv,int startat)
	throws HeclException {
	throw new HeclException("invalid subcommand '"+subcmd+"'");
    }
}

// Variables:
// mode:java
// coding:utf-8
// End:
