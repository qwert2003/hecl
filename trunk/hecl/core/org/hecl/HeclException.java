/* Copyright 2004-2006 David N. Welton

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package org.hecl;

import java.util.Stack;
import java.util.Vector;

/**
 * The <code>HeclException</code> class implements exceptions for Hecl.
 *
 * @author <a href="mailto:davidw@dedasys.com">David N. Welton </a>
 * @version 1.0
 */

public class HeclException extends Exception {
    public String code = null;

    Stack stack;

    String txt;

    static final String BREAK = "BREAK";

    static final String CONTINUE = "CONT";

    static final String RETURN = "RETURN";

    static final String ERROR = "ERROR";

    /**
     * Creates a new <code>HeclException</code> instance.
     *
     * @param s a <code>String</code> value
     */

    public HeclException(String s) {
        super(s);
        txt = s;
        code = ERROR;
        pushException();
    }

    /**
     * Creates a new <code>HeclException</code> instance.
     *
     * @param s
     *            a <code>String</code> value describing the error.
     * @param exception_code
     *            an <code>int</code> value
     */

    public HeclException(String s, String exception_code) {
        code = exception_code;
        txt = s;
        pushException();
    }

    /**
     * <code>pushException</code> adds to the exception stack.
     *
     */
    private void pushException() {
        stack = new Stack();
        Vector lst = new Vector();
        lst.addElement(new Thing(code));
        lst.addElement(new Thing(txt));

        stack.push(new Thing(new ListThing(lst)));
    }

    /**
     * The <code>where</code> method tells the exception what command it
     * occurred in.
     *
     * @param cmd
     *            a <code>String</code> containing the command name.
     */
    public void where(String cmd) {
        stack.push(new Thing(cmd));
    }

    /**
     * The <code>toString</code> method turns the exception stack into a
     * string.
     *
     * @return a <code>String</code> value
     */

    public String toString() {
        return getStack().toString();
    }

    /**
     * The <code>getStack</code> method returns the exception as a Thing.
     *
     * @return a <code>Thing</code> value
     */

    public Thing getStack() {
        return ListThing.create((Vector) stack);
    }

    public static HeclException createWrongNumArgsException(Thing argv[],
            int count, String message) throws HeclException {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < count && i < argv.length; i++) {
            str.append(argv[i].getStringRep());
            str.append(" ");
        }
        return new HeclException("wrong # args: should be \"" + str + message + "\"");
    }
    /**
     *
     * @param param
     *            <code>Thing</code> specifying the actual parameter.
     * @param type
     *            String saying the type - ie <i>option </i>, <i>command </i>.
     * @param options
     *            A comma-separated list of options that can be supplied.
     * @return a new HeclException
     * @throws HeclException
     */
    public static HeclException createInvalidParameter(Thing param,
            String type, String options) throws HeclException {
        return new HeclException("invalid " + type + " specified \""
                + param.getStringRep() + "\"; should be: " + options + ".");
    }
}
